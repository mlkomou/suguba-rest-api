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

    public SouscriptionService(SouscritionRepository souscritionRepository, PartenaireRepository partenaireRepository, ClientRepository clientRepository, ApplicationUserRepository applicationUserRepository, DemandeSouscriptionRepository demandeSouscriptionRepository) {
        this.souscritionRepository = souscritionRepository;
        this.partenaireRepository = partenaireRepository;
        this.clientRepository = clientRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.demandeSouscriptionRepository = demandeSouscriptionRepository;
    }

   public Map<String, Object> getSouscritonByStatut(int page, int size, Boolean active, String statut) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<DemandeSouscription> souscritions = demandeSouscriptionRepository.findAllByStatutAndActive(statut, active, paging);
            return Response.success(souscritions, "Liste des comptes");
        } catch (Exception e) {
            return Response.error(e, "Erreur de récupération");
        }
    }

    public Map<String, Object> compleAcount(SouscriptionClient souscriptionClient) {
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

                    Client client = user.getClient();
                    client.setPrenom(souscriptionClient.getPrenom());
                    client.setNom(souscriptionClient.getNom());
                    client.setEmail(souscriptionClient.getEmail());

                    if (souscriptionClient.getPartenaire() != null) {
                        Optional<Partenaire> partenaire = partenaireRepository.findById(souscriptionClient.partenaire);
                        souscrition.setPartenaire(partenaire.get());
                        client.setPartenaire(partenaire.get());
                        user.setServicePaiement(partenaire.get());
                    }
                    souscritionRepository.save(souscrition);
                    demandeSouscription.setStatut("VALIDE");
                    demandeSouscriptionRepository.save(demandeSouscription);

                    clientRepository.save(client);
                    applicationUserRepository.save(user);

                    return Response.success(null, "Souscrition enregistrée.");

                } else {
                    Souscrition souscrition = new Souscrition();
                    // souscription existe, faire le cumul des montant

                    souscrition.setUser(user);
                    souscrition.setMontant(souscriptionClient.getMontant());

                    Client client = user.getClient();
                    client.setPrenom(souscriptionClient.getPrenom());
                    client.setNom(souscriptionClient.getNom());
                    client.setEmail(souscriptionClient.getEmail());

                    if (souscriptionClient.getPartenaire() != null) {
                        Optional<Partenaire> partenaire = partenaireRepository.findById(souscriptionClient.partenaire);
                        souscrition.setPartenaire(partenaire.get());
                        client.setPartenaire(partenaire.get());
                        user.setServicePaiement(partenaire.get());
                    }
                    souscritionRepository.save(souscrition);
                    demandeSouscription.setStatut("VALIDE");
                    demandeSouscriptionRepository.save(demandeSouscription);

                    clientRepository.save(client);
                    applicationUserRepository.save(user);

                    return Response.success(null, "Souscrition enregistrée.");
                }


            } else {
                return Response.error(new Object(), "Cette demande de souscription n'existe pas.");
            }

        } catch (Exception e) {
            System.err.println(e);
            return Response.error(e, "Erreur d'enregistrement. Veuillez réessayer plus tard.");
        }
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
