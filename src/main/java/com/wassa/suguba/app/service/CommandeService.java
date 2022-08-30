package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.*;
import com.wassa.suguba.app.payload.*;
import com.wassa.suguba.app.repository.*;
import com.wassa.suguba.authentification.entity.ApplicationUser;
import com.wassa.suguba.authentification.entity.Response;
import com.wassa.suguba.authentification.repo.ApplicationUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommandeService {
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final LigneCommendeRepository ligneCommendeRepository;
    private final ProduitRepository produitRepository;
    private final NotificationService notificationService;
    private final SendEmailService sendEmailService;
    private final PaiementRepository paiementRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final SouscritionRepository souscritionRepository;
    private final PaiementService paiementService;
    private final SendSmsService sendSmsService;

    public CommandeService(CommandeRepository commandeRepository, ClientRepository clientRepository, LigneCommendeRepository ligneCommendeRepository, ProduitRepository produitRepository, NotificationService notificationService, SendEmailService sendEmailService, PaiementRepository paiementRepository, ApplicationUserRepository applicationUserRepository, SouscritionRepository souscritionRepository, PaiementService paiementService, SendSmsService sendSmsService) {
        this.commandeRepository = commandeRepository;
        this.clientRepository = clientRepository;
        this.ligneCommendeRepository = ligneCommendeRepository;
        this.produitRepository = produitRepository;
        this.notificationService = notificationService;
        this.sendEmailService = sendEmailService;
        this.paiementRepository = paiementRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.souscritionRepository = souscritionRepository;
        this.paiementService = paiementService;
        this.sendSmsService = sendSmsService;
    }


   double calculSum(ArrayList<Double> montantArr) {
       double sum = 0;
       for(int i = 0; i < montantArr.size(); i++)
       {
           sum = sum + montantArr.get(i);
       }
       return sum;
   }

    public ResponseEntity<Map<String, Object>> saveCommande(CommandeAndPayementPayload commandeAndPayementPayload) {
      try {
          CommandePayload commandePayload = commandeAndPayementPayload.getCommandePayload();
          Optional<ApplicationUser> user = applicationUserRepository.findById(commandePayload.getUserId());

//          NotificationPayload notificationPayload = new NotificationPayload();
            List<LigneCommande> ligneArray = new ArrayList();
            List<String> included_segments = new ArrayList<>();
            included_segments.add(commandePayload.getOneSignalNotificationId());
            Commande commande = new Commande();
            Client client = new Client();
            client.setEmail(commandePayload.getEmail());
            client.setPhone(commandePayload.getPhnoneClient());
            client.setNom(commandePayload.getNomClient());
            Client clientSaved = clientRepository.save(client);

            commande.setUser(user.get());
            commande.setClient(clientSaved);
            commande.setOneSignalNotificationId(commandePayload.getOneSignalNotificationId());
            commande.setAdresse(commandePayload.getAdresse());
            commande.setAdressePath(commandePayload.getAdressePath());
            commande.setNumero(commandePayload.getNumero());
            commande.setStatut(commandePayload.getStatut());
            commande.setReceptionMe(commandePayload.getReceptionMe());

            if (commandePayload.getTypePaiement() != null) {
                // choisir le service (orange ou moov) depuis le client
                Paiement paiement = new Paiement();
                ArrayList<Double> montantArr = new ArrayList();
                commandePayload.getLigneQuantites().forEach(ligneQuantite -> {
                    Produit produit = produitRepository.getById(ligneQuantite.getIdProduit());
                    Double prixProd = produit.getPrix();
                    Double prodMontant = ligneQuantite.getQuantite() * prixProd;
                    montantArr.add(prodMontant);
                });

                paiement.setTypePaiement(commandePayload.getTypePaiement());
                paiement.setMontant(calculSum(montantArr));
                paiement.setStatus("INITIATED");
                Paiement paiementSaved = paiementRepository.save(paiement);
                commande.setPaiement(paiementSaved);
                Commande commandeSaved = commandeRepository.save(commande);

                commandePayload.getLigneQuantites().forEach(ligneQuantite -> {
                    Optional<Produit> produit = produitRepository.findById(ligneQuantite.idProduit);
                    LigneCommande ligneCommande = new LigneCommande();
                    ligneCommande.setCommande(commandeSaved);
                    ligneCommande.setProduit(produit.get());
                    ligneCommande.setQuantite(ligneQuantite.getQuantite());
                    ligneArray.add(ligneCommande);
                });
                ligneCommendeRepository.saveAll(ligneArray);

                // enregistrement du paiement a travers l'api
                commandeAndPayementPayload.getPaymentMarchandPayload().setAmount(calculSum(montantArr));
                commandeAndPayementPayload.getPaymentMarchandPayload().setIdFromClient(new Date().getTime() + "");
                commandeAndPayementPayload.getPaymentMarchandPayload().setRecipientNumber(user.get().getUsername()); // à modifier: doit etre choisi depuis le client
                commandeAndPayementPayload.getPaymentMarchandPayload().setCallback("https://vmi920689.contaboserver.net:8443/suguba/paiements/payement_callback");

                paiementService.makePayementMarchand(commandeAndPayementPayload.getPaymentMarchandPayload(), commandeSaved);
                // je dois implementer la verification de cet entregistrement



//                notificationPayload.setCommandeId(commandeSaved.getId());
//                notificationPayload.setType("COMMANDE");
//                notificationPayload.setTitre("COMMANDE");
//                notificationPayload.setDescription("Votre commande est en traitement, nous vous contacterons dans peu de temps.");

//                notificationService.saveNotification(notificationPayload, included_segments);
//                System.out.println("commandeSaved: " + commandeSaved.toString());
                return new ResponseEntity<>(Response.success(commandeSaved, "Votre commande est en cours de traitement. Vous recevrez un message sur l'état de votre commande."), HttpStatus.OK);

            } else {
                Souscrition souscrition = souscritionRepository.findByUserIdAndActive(commandePayload.getUserId(), true);
                if (souscrition != null) {
                    ArrayList<Double> montantArr = new ArrayList();
                    commandePayload.getLigneQuantites().forEach(ligneQuantite -> {
                        Produit produit = produitRepository.getById(ligneQuantite.getIdProduit());
                        Double prixProd = produit.getPrix();
                        Double prodMontant = ligneQuantite.getQuantite() * prixProd;
                        montantArr.add(prodMontant);
                    });

                    if (souscrition.getMontant() >= calculSum(montantArr)) {
                        souscrition.setMontant(souscrition.getMontant() - calculSum(montantArr));
                        souscritionRepository.save(souscrition);

                        Commande commandeSaved = commandeRepository.save(commande);

//                        notificationPayload.setCommandeId(commandeSaved.getId());
//                        notificationPayload.setType("COMMANDE");
//                        notificationPayload.setTitre("COMMANDE");
//                        notificationPayload.setDescription("Votre commande est en traitement, nous vous contacterons dans peu de temps.");

                        commandePayload.getLigneQuantites().forEach(ligneQuantite -> {
                            Optional<Produit> produit = produitRepository.findById(ligneQuantite.idProduit);
                            LigneCommande ligneCommande = new LigneCommande();
                            ligneCommande.setCommande(commandeSaved);
                            ligneCommande.setProduit(produit.get());
                            ligneCommande.setQuantite(ligneQuantite.getQuantite());
                            ligneArray.add(ligneCommande);
                        });
                        ligneCommendeRepository.saveAll(ligneArray);
//                        notificationService.saveNotification(notificationPayload, included_segments);
                        sendSmsService.sendSmsSingle(user.get().getUsername(), "Commande est cours de traitement, vous recevrez un message de validation.");
                        return new ResponseEntity<>(Response.success(commandeSaved, "Commande est cours de traitement, vous recevrez un message de validation."), HttpStatus.OK);
                    } else {
//                        System.err.println("MontantTotal: " + calculSum(montantArr));
//                        System.err.println("MontantSouscription: " + souscrition.getMontant());
                        return new ResponseEntity<>(Response.error(souscrition, "Le solde de votre souscription est insuffisant pour cette commande."), HttpStatus.OK);
                    }
                }
            }
          sendSmsService.sendSmsSingle(user.get().getUsername(), "Commande est cours de traitement, vous recevrez un message de validation.");
          return new ResponseEntity<>(Response.success(commande, "Commande est cours de traitement, vous recevrez un message de validation."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.error(e, "Erreur d'enregistrement de la commande."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public Map<String, Object> updateCommandeWithoutFile(Commande commande) {
        try {
            Optional<Commande> commandeOptional = commandeRepository.findById(commande.getId());
            if (commandeOptional.isPresent()) {
                Commande commandeSaced = commandeRepository.save(commande);
                return Response.success(commandeSaced, "Commande modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Commande n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la commande.");
        }
    }

    public Map<String, Object> updateStatut(UpdateStatut updateStatut) {
        try {
            Optional<Commande> commandeOptional = commandeRepository.findById(updateStatut.getCommandeId());
            if (commandeOptional.isPresent()) {
                Commande commande = commandeOptional.get();
                commande.setLastModifiedAt(LocalDateTime.now());
                commande.setStatut(updateStatut.getStatut());
                Commande commandeSaced = commandeRepository.save(commande);
                return Response.success(commandeSaced, "Commande modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Commande n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la commande.");
        }
    }

    public Map<String, Object> getCommandesByPage(int page, int size) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<Commande> commandes = commandeRepository.findAll(paging);
            return Response.success(commandes, "Liste des commandes.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getCommandes() {
        try {
            List<Commande> commandes = commandeRepository.findAll();
            return Response.success(commandes, "Liste des commandes.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getCommandeById(Long id) {
        try {
            List<Commande> commandes = commandeRepository.getAllByUserId(id);
            if (!commandes.isEmpty()) {
                return Response.success(commandes.size(), "commande trouvée.");
            } else {
                return Response.error(new ArrayList<>(), "Cette commande n'existe pas.");
            }
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de commande.");
        }
    }

    public Map<String, Object> getCommandeByUser(Long userId, int page, int size) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<Commande> commandes = commandeRepository.findAllByUserId(userId, paging);
            return Response.success(commandes, "Liste des commandes");
        } catch (Exception e) {
            return Response.error(e, "Erreur de récupération");
        }
    }

    public Map<String, Object> getByIntervalleDate(IntervalleDate intervalleDate) {
        try {
            Map<String, Object> commandeWithTotalMontant = new HashMap<>();
            ArrayList<Double> montantTotalArr = new ArrayList();
            List<Commande> commandeList = commandeRepository.getByRangeDateWithoutPage(intervalleDate.getDateDebut(), intervalleDate.getDateFin(), intervalleDate.getPartenaireId());
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable paging = PageRequest.of(intervalleDate.getPage(), intervalleDate.getSize(), defaultSort);
            Page<Commande> commandes = commandeRepository.getByRangeDate(intervalleDate.getDateDebut(), intervalleDate.getDateFin(), intervalleDate.getPartenaireId(), paging);

            commandeList.forEach(commande -> {
                ArrayList<Double> montantArr = new ArrayList();
                commande.getLigneCommandes().forEach(ligneQuantite -> {
                    Produit produit = produitRepository.getById(ligneQuantite.getProduit().getId());
                    Double prixProd = produit.getPrix();
                    Double prodMontant = ligneQuantite.getQuantite() * prixProd;
                    montantArr.add(prodMontant);
                });
//                System.err.println("totalMonantCommandes: " + calculSum(montantArr));
                montantTotalArr.add(calculSum(montantArr));
            });
            commandeWithTotalMontant.put("commandes", commandes);
            commandeWithTotalMontant.put("montantTotal", calculSum(montantTotalArr));
//            System.err.println("somme totale des commandes: " + calculSum(montantTotalArr));

            return Response.success(commandeWithTotalMontant, "Liste des commande");
        } catch (Exception e) {
            System.err.println(e);
           return Response.error(e, "Erreur de récupération");
        }
    }

    public Map<String, Object> getByIntervalleDateAdmin(IntervalleDate intervalleDate) {
        try {
            Map<String, Object> commandeWithTotalMontant = new HashMap<>();
            List<Commande> commandeList = commandeRepository.getByRangeDateAdminWithoutPage(intervalleDate.getDateDebut(), intervalleDate.getDateFin());
            ArrayList<Double> montantTotalArr = new ArrayList();
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable paging = PageRequest.of(intervalleDate.getPage(), intervalleDate.getSize(), defaultSort);
            Page<Commande> commandes = commandeRepository.getByRangeDateAdmin(intervalleDate.getDateDebut(), intervalleDate.getDateFin(), paging);

            commandeList.forEach(commande -> {
                ArrayList<Double> montantArr = new ArrayList();
                commande.getLigneCommandes().forEach(ligneQuantite -> {
                    Produit produit = produitRepository.getById(ligneQuantite.getProduit().getId());
                    Double prixProd = produit.getPrix();
                    Double prodMontant = ligneQuantite.getQuantite() * prixProd;
                    montantArr.add(prodMontant);
                });

//                System.err.println("totalMonantCommandes: " + calculSum(montantArr));
                montantTotalArr.add(calculSum(montantArr));
            });

//            System.err.println("somme totale des commandes: " + calculSum(montantTotalArr));
            commandeWithTotalMontant.put("commandes", commandes);
            commandeWithTotalMontant.put("montantTotal", calculSum(montantTotalArr));

            return Response.success(commandeWithTotalMontant, "Liste des commande");
        } catch (Exception e) {
            System.err.println(e);
           return Response.error(e, "Erreur de récupération");
        }
    }

    public List<Commande> getAllCommande() {
        return commandeRepository.findAll();
    }

    public Map<String, Object> exportToPDF(IntervalleDate intervalleDate,  HttpServletResponse response) {
//        HttpServletResponse response = new HttpServletResponse();
        try {
            List<Commande> commandeList;
            if (intervalleDate.getPartenaireId() == null) {
                 commandeList = commandeRepository.getByRangeDateAdminWithoutPage(intervalleDate.getDateDebut(), intervalleDate.getDateFin());

            } else {
                commandeList = commandeRepository.getByRangeDateWithoutPage(intervalleDate.getDateDebut(), intervalleDate.getDateFin(), intervalleDate.getPartenaireId());
            }
            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=commandes_" + currentDateTime + ".pdf";
            response.setHeader(headerKey, headerValue);

            CommandePDFExporter exporter = new CommandePDFExporter(commandeList);
            exporter.export(response);

            return Response.success(null, "PDF exporté avec succès.");
        } catch (Exception e) {
            System.err.println(e);
            return Response.error(e, "Exportation échouée");
        }
    }
}




//            if (client.getEmail() != null) {
//                final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.FRENCH);
//                final String today = LocalDate.now().format(formatter);
//                String message = "Cher(e) " + commandeSaved.getClient().getNom() + ".\n" + "Merci de faire vos achats sur SUGUBA.\n" + "Votre commande du " + today + ", référence " + commandeSaved.getId() + "a été reçu";
//                sendEmailService.sendEmailWithAttachment(client.getEmail(), "SUGUBA RECEPTION DE COMMANDE", commandeSaved.getId(), commandeSaved.getClient().getPhone(), message);
//                return Response.success(commandeSaved, "Commande enregistrée.");
//            }
