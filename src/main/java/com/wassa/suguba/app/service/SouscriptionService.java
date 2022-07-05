package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Client;
import com.wassa.suguba.app.entity.Partenaire;
import com.wassa.suguba.app.entity.Souscrition;
import com.wassa.suguba.app.payload.SouscriptionClient;
import com.wassa.suguba.app.repository.ClientRepository;
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

    public SouscriptionService(SouscritionRepository souscritionRepository, PartenaireRepository partenaireRepository, ClientRepository clientRepository, ApplicationUserRepository applicationUserRepository) {
        this.souscritionRepository = souscritionRepository;
        this.partenaireRepository = partenaireRepository;
        this.clientRepository = clientRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

   public Map<String, Object> getSouscritonByStatut(int page, int size, Boolean active) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<Souscrition> souscritions = souscritionRepository.findAllByActive(active, paging);
            return Response.success(souscritions, "Liste des comptes");
        } catch (Exception e) {
            return Response.error(e, "Erreur de récupération");
        }
    }

    public Map<String, Object> compleAcount(SouscriptionClient souscriptionClient) {
        try {
            Optional<Souscrition> souscritionOptional = souscritionRepository.findById(souscriptionClient.getId());
            if (souscritionOptional.isPresent()) {
                Souscrition souscrition = souscritionOptional.get();
                ApplicationUser user = souscrition.getUser();
                Client client = user.getClient();
                client.setPrenom(souscriptionClient.getPrenom());
                client.setNom(souscriptionClient.getNom());
                client.setEmail(souscriptionClient.getEmail());
                clientRepository.save(client);

                if (souscriptionClient.getPartenaire() != null) {
                  Optional<Partenaire> partenaire = partenaireRepository.findById(souscriptionClient.partenaire);
                    souscrition.setPartenaire(partenaire.get());
                }
                Souscrition souscritionSaved = souscritionRepository.save(souscrition);
                return Response.success(souscritionSaved, "Souscrition enregistrée.");
            } else {
                return Response.error(new Object(), "Cette souscription n'existe pas.");
            }

        } catch (Exception e) {
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
