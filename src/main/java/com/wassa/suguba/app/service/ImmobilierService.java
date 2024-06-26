package com.wassa.suguba.app.service;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Immobilier;
import com.wassa.suguba.app.payload.UpdateStatut;
import com.wassa.suguba.app.repository.ImmobilierRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ImmobilierService {
    private final ImmobilierRepository immobilierRepository;
    private final UploadFileService uploadFileService;
    private final SendEmailService sendEmailService;

    public ImmobilierService(ImmobilierRepository immobilierRepository, UploadFileService uploadFileService, SendEmailService sendEmailService) {
        this.immobilierRepository = immobilierRepository;
        this.uploadFileService = uploadFileService;
        this.sendEmailService = sendEmailService;
    }

    public Map<String, Object> saveImmobilierWithFile(Immobilier immobilier, MultipartFile photo) {
        try {
            immobilier.setPath(uploadFileService.uploadFile(photo, UploadPath.DOWNLOAD_LINK + "/immobilier"));
            Immobilier immobilierSaved = immobilierRepository.save(immobilier);
            if (immobilier.getMail() != null) {
                String message = "Votre demande est en cours de traitement, nous vous contacterons pour la suite. Merci d'avoir choisi SUGUBA.";
                sendEmailService.sendEmailImmobilier(immobilier.getMail(), "SUGUBA IMMOBILIER", immobilierSaved.getId(), immobilierSaved.getPhone(), message);
                return Response.success(immobilierSaved, "Demande envoyée.");
            }
            return Response.success(immobilierSaved, "Demande envoyée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la demande.");
        }
    }

    public Map<String, Object> saveImmobilier(Immobilier immobilier) {
        try {
            Immobilier immobilierSaced = immobilierRepository.save(immobilier);
            return Response.success(immobilierSaced, "Immobilier enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la immobilier.");
        }
    }

    public Map<String, Object> updateImmobilierWithoutFile(Immobilier immobilier) {
        try {
            Optional<Immobilier> immobilierOptional = immobilierRepository.findById(immobilier.getId());
            if (immobilierOptional.isPresent()) {
                Immobilier immobilierSaced = immobilierRepository.save(immobilier);
                return Response.success(immobilierSaced, "Immobilier modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Immobilier n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la immobilier.");
        }
    }

    public Map<String, Object> getImmobiliersByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Immobilier> immobiliers = immobilierRepository.findAll(paging);
            return Response.success(immobiliers, "Liste des immobiliers.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> updateStatut(UpdateStatut updateStatut) {
        try {
            Optional<Immobilier> commandeOptional = immobilierRepository.findById(updateStatut.getCommandeId());
            if (commandeOptional.isPresent()) {
                Immobilier commande = commandeOptional.get();
                commande.setLastModifiedAt(LocalDateTime.now());
                commande.setStatut(updateStatut.getStatut());
                Immobilier commandeSaced = immobilierRepository.save(commande);
                return Response.success(commandeSaced, "Commande modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Commande n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la commande.");
        }
    }

    public Map<String, Object> getImmobiliers() {
        try {
            List<Immobilier> immobiliers = immobilierRepository.findAll();
            return Response.error(immobiliers, "Liste des immobiliers.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
