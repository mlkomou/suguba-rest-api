package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Client;
import com.wassa.suguba.app.entity.Commande;
import com.wassa.suguba.app.entity.LigneCommande;
import com.wassa.suguba.app.entity.Produit;
import com.wassa.suguba.app.payload.CommandePayload;
import com.wassa.suguba.app.payload.NotificationPayload;
import com.wassa.suguba.app.payload.UpdateStatut;
import com.wassa.suguba.app.repository.ClientRepository;
import com.wassa.suguba.app.repository.CommandeRepository;
import com.wassa.suguba.app.repository.LigneCommendeRepository;
import com.wassa.suguba.app.repository.ProduitRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CommandeService {
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final LigneCommendeRepository ligneCommendeRepository;
    private final ProduitRepository produitRepository;
    private final NotificationService notificationService;
    private final SendEmailService sendEmailService;

    public CommandeService(CommandeRepository commandeRepository, ClientRepository clientRepository, LigneCommendeRepository ligneCommendeRepository, ProduitRepository produitRepository, NotificationService notificationService, SendEmailService sendEmailService) {
        this.commandeRepository = commandeRepository;
        this.clientRepository = clientRepository;
        this.ligneCommendeRepository = ligneCommendeRepository;
        this.produitRepository = produitRepository;
        this.notificationService = notificationService;
        this.sendEmailService = sendEmailService;
    }


    public Map<String, Object> saveCommande(CommandePayload commandePayload) {
      try {
          NotificationPayload notificationPayload = new NotificationPayload();
            List<LigneCommande> ligneArray = new ArrayList();
            List<String> included_segments = new ArrayList<>();
            included_segments.add(commandePayload.getOneSignalNotificationId());
            Commande commande = new Commande();
            Client client = new Client();
            client.setEmail(commandePayload.getEmail());
            client.setPhone(commandePayload.getPhnoneClient());
            client.setNom(commandePayload.getNomClient());
            Client clientSaved = clientRepository.save(client);

            commande.setClient(clientSaved);
            commande.setOneSignalNotificationId(commandePayload.getOneSignalNotificationId());
            commande.setAdresse(commandePayload.getAdresse());
            commande.setAdressePath(commandePayload.getAdressePath());
            commande.setNumero(commandePayload.getNumero());
            commande.setStatut(commandePayload.getStatut());
            Commande commandeSaved = commandeRepository.save(commande);

            notificationPayload.setCommandeId(commandeSaved.getId());
            notificationPayload.setType("COMMANDE");
            notificationPayload.setTitre("COMMANDE");
            notificationPayload.setDescription("Votre commande est en traitement, nous vous contacterons dans peu de temps.");

            commandePayload.getLigneQuantites().forEach(ligneQuantite -> {
                Optional<Produit> produit = produitRepository.findById(ligneQuantite.idProduit);
                LigneCommande ligneCommande = new LigneCommande();
                ligneCommande.setCommande(commandeSaved);
                ligneCommande.setProduit(produit.get());
                ligneCommande.setQuantite(ligneQuantite.getQuantite());
                ligneArray.add(ligneCommande);
            });
            ligneCommendeRepository.saveAll(ligneArray);
            notificationService.saveNotification(notificationPayload, included_segments);

            if (client.getEmail() != null) {
                final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.FRENCH);
                final String today = LocalDate.now().format(formatter);
                String message = "Cher(e) " + commandeSaved.getClient().getNom() + ".\n" + "Merci de faire vos achats sur SUGUBA.\n" + "Votre commande du " + today + ", référence " + commandeSaved.getId() + "a été reçu";
                sendEmailService.sendEmailWithAttachment(client.getEmail(), "SUGUBA RECEPTION DE COMMANDE", commandeSaved.getId(), commandeSaved.getClient().getPhone(), message);
                return Response.success(commandeSaved, "Commande enregistrée.");
            }
          return Response.success(commandeSaved, "Commande enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la commande.");
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
            Pageable paging = PageRequest.of(page, size);
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
}
