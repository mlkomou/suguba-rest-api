package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Client;
import com.wassa.suguba.app.entity.DemandeSouscription;
import com.wassa.suguba.app.entity.Partenaire;
import com.wassa.suguba.app.entity.Souscrition;
import com.wassa.suguba.app.payload.SouscriptionClient;
import com.wassa.suguba.app.repository.ClientRepository;
import com.wassa.suguba.app.repository.DemandeSouscriptionRepository;
import com.wassa.suguba.app.repository.PartenaireRepository;
import com.wassa.suguba.app.repository.SouscritionRepository;
import com.wassa.suguba.authentification.entity.ApplicationUser;
import com.wassa.suguba.authentification.entity.Response;
import com.wassa.suguba.authentification.repo.ApplicationUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class SouscriptionService {
    private final SouscritionRepository souscritionRepository;
    private final PartenaireRepository partenaireRepository;
    private final ClientRepository clientRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final DemandeSouscriptionRepository demandeSouscriptionRepository;
    private final SendSmsService sendSmsService;

    public SouscriptionService(SouscritionRepository souscritionRepository, PartenaireRepository partenaireRepository, ClientRepository clientRepository, ApplicationUserRepository applicationUserRepository, DemandeSouscriptionRepository demandeSouscriptionRepository, SendSmsService sendSmsService) {
        this.souscritionRepository = souscritionRepository;
        this.partenaireRepository = partenaireRepository;
        this.clientRepository = clientRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.demandeSouscriptionRepository = demandeSouscriptionRepository;
        this.sendSmsService = sendSmsService;
    }

    public Map<String, Object> getSouscritonByStatut(int page, int size, Boolean active, String statut, String statutBanque) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<DemandeSouscription> souscritions = demandeSouscriptionRepository.findAllByStatutAndStatutBanqueAndActive(statut, statutBanque, active, paging);
            return Response.success(souscritions, "Liste des comptes");
        } catch (Exception e) {
            return Response.error(e, "Erreur de récupération");
        }
    }


    //suguba validation
    public Map<String, Object> compleAcount(SouscriptionClient souscriptionClient) {
        try {
            Optional<DemandeSouscription> demandeSouscriptionOptional = demandeSouscriptionRepository.findByIdAndStatut(souscriptionClient.getId(), "TRAITEMENT");

            if (demandeSouscriptionOptional.isPresent()) {
                // demande de souscription existe
                DemandeSouscription demandeSouscription = demandeSouscriptionOptional.get();
                demandeSouscription.setStatut("VALIDE");
                demandeSouscriptionRepository.save(demandeSouscription);
                return Response.success(null, "Demande de Souscription validée au niveau de SUGUBA, elle sera soumise à la banque pour une dexième validation.");

//                Souscrition souscrition = souscritionOptional.get();
//                ApplicationUser user = demandeSouscription.getUser();
//                Optional<Souscrition> souscritionOptional = souscritionRepository.findByUserId(user.getId());
//                if (souscritionOptional.isPresent()) {
//                    // souscription existe, faire le cumul des montant
//
//                    Souscrition souscrition = souscritionOptional.get();
//                    System.err.println("existe: " + souscrition.getMontant());
//                    souscrition.setUser(user);
//                    Double newMontant = souscriptionClient.getMontant() + souscrition.getMontant();
//                    souscrition.setMontant(newMontant);
//
//                    Client client = user.getClient();
//
//                    if (souscriptionClient.getPartenaire() != null) {
//                        Optional<Partenaire> partenaire = partenaireRepository.findById(souscriptionClient.partenaire);
//                        souscrition.setPartenaire(partenaire.get());
//                        client.setPartenaire(partenaire.get());
//                        user.setServicePaiement(partenaire.get());
//                    }
//                    souscritionRepository.save(souscrition);
//                    demandeSouscription.setStatut("VALIDE");
//                    demandeSouscriptionRepository.save(demandeSouscription);
//
//                    clientRepository.save(client);
//                    applicationUserRepository.save(user);
//                    sendSmsService.sendSmsSingle(client.getPhone(), "Salut " + client.getPrenom() + ", Votre souscription a été validée avec succès, vous pouvez désormais passer des commandes à crédit. "+"\n"+"  SUGUBA vous remercie.");
//
//                    return Response.success(null, "Souscrition enregistrée.");
//
//                } else {
//                    Souscrition souscrition = new Souscrition();
//                    // souscription existe, faire le cumul des montant
//
//                    souscrition.setUser(user);
//                    souscrition.setMontant(souscriptionClient.getMontant());
//
//                    Client client = user.getClient();
//
//                    if (souscriptionClient.getPartenaire() != null) {
//                        Optional<Partenaire> partenaire = partenaireRepository.findById(souscriptionClient.partenaire);
//                        souscrition.setPartenaire(partenaire.get());
//                        client.setPartenaire(partenaire.get());
//                        user.setServicePaiement(partenaire.get());
//                    }
//                    souscritionRepository.save(souscrition);
//                    demandeSouscription.setStatut("VALIDE");
//                    demandeSouscriptionRepository.save(demandeSouscription);
//
//                    clientRepository.save(client);
//                    applicationUserRepository.save(user);
//
//                    sendSmsService.sendSmsSingle(client.getPhone(), "Salut " + client.getPrenom() + ", Votre souscription a été validée avec succès, vous pouvez désormais passer des commandes à crédit. "+"\n"+"  SUGUBA vous remercie.");
//
//
//                    return Response.success(null, "Souscrition enregistrée.");
//                }


            } else {
                return Response.error(new Object(), "Cette demande de souscription n'existe pas.");
            }

        } catch (Exception e) {
            System.err.println(e);
            return Response.error(e, "Erreur d'enregistrement. Veuillez réessayer plus tard.");
        }
    }

    //banque validation
    public Map<String, Object> compleAcountForBanque(SouscriptionClient souscriptionClient) {
        try {
            Optional<DemandeSouscription> demandeSouscriptionOptional = demandeSouscriptionRepository.findByIdAndStatut(souscriptionClient.getId(), "TRAITEMENT");

            if (demandeSouscriptionOptional.isPresent()) {
                // demande de souscription existe
                DemandeSouscription demandeSouscription = demandeSouscriptionOptional.get();
//                Souscrition souscrition = souscritionOptional.get();
                ApplicationUser user = demandeSouscription.getUser();
                Optional<Souscrition> souscritionOptional = souscritionRepository.findByUserId(user.getId());
                if (souscritionOptional.isPresent()) {
                    // souscription existe, faire le cumul des montant

                    Souscrition souscrition = souscritionOptional.get();
                    System.err.println("existe: " + souscrition.getMontant());
                    souscrition.setUser(user);
                    Double newMontant = souscriptionClient.getMontant() + souscrition.getMontant();
                    souscrition.setMontant(newMontant);

                    return getClient(souscriptionClient, demandeSouscription, user, souscrition);

                } else {
                    Souscrition souscrition = new Souscrition();
                    // souscription existe, faire le cumul des montant

                    souscrition.setUser(user);
                    souscrition.setMontant(souscriptionClient.getMontant());

                    return getClient(souscriptionClient, demandeSouscription, user, souscrition);
                }


            } else {
                return Response.error(new Object(), "Cette demande de souscription n'existe pas.");
            }

        } catch (Exception e) {
            System.err.println(e);
            return Response.error(e, "Erreur d'enregistrement. Veuillez réessayer plus tard.");
        }
    }

    private Map<String, Object> getClient(SouscriptionClient souscriptionClient, DemandeSouscription demandeSouscription, ApplicationUser user, Souscrition souscrition) {
        Client client = user.getClient();

        if (souscriptionClient.getPartenaire() != null) {
            Optional<Partenaire> partenaire = partenaireRepository.findById(souscriptionClient.partenaire);
            souscrition.setPartenaire(partenaire.get());
            client.setPartenaire(partenaire.get());
            user.setServicePaiement(partenaire.get());
        }
        souscritionRepository.save(souscrition);
        demandeSouscription.setStatutBanque("VALIDE");
        demandeSouscriptionRepository.save(demandeSouscription);

        clientRepository.save(client);
        applicationUserRepository.save(user);

        sendSmsService.sendSmsSingle(client.getPhone(), "Salut " + client.getPrenom() + ", Votre souscription a été validée avec succès, vous pouvez désormais passer des commandes à crédit. "+"\n"+"  SUGUBA vous remercie.");

        return Response.success(null, "Souscrition enregistrée.");
    }

    public Map<String, Object> activeAndDesactiveSouscr(Long id, boolean statut) {
        try {
            Optional<Souscrition>  souscritionOptional = souscritionRepository.findById(id);
            if (souscritionOptional.isPresent()) {
                Souscrition souscrition = souscritionOptional.get();
                souscrition.setActive(statut);
                Souscrition souscritionUpdated = souscritionRepository.save(souscrition);
                return Response.success(souscritionUpdated, "Souscription modifiée.");
            } else {
                return Response.error(new Object(), "Cette souscription n'existe pas.");
            }
        } catch (Exception e) {
            return Response.error(e, "Erreur de modification de la souscription.");
        }
    }

    public Map<String, Object> getSouscriptionByUser(Long id) {
        try {
            Optional<ApplicationUser> userOptional = applicationUserRepository.findById(id);
            if (userOptional.isPresent()) {
                Souscrition souscrition = souscritionRepository.findByUserIdAndActive(id, true);
                return Response.success(souscrition, "Souscription");
            } else {
                return Response.error(new Object(), "Cette souscription n'existe pas.");
            }
        } catch (Exception e) {
            return Response.error(e, "Erreur de récupération de la souscription.");
        }
    }
}
