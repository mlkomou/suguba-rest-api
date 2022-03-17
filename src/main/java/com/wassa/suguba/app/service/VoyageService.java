package com.wassa.suguba.app.service;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Voyage;
import com.wassa.suguba.app.repository.VoyageRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VoyageService {
    private final VoyageRepository voyageRepository;
    private final UploadFileService uploadFileService;
    private final SendEmailService sendEmailService;

    public VoyageService(VoyageRepository voyageRepository, UploadFileService uploadFileService, SendEmailService sendEmailService) {
        this.voyageRepository = voyageRepository;
        this.uploadFileService = uploadFileService;
        this.sendEmailService = sendEmailService;
    }

    public Map<String, Object> saveVoyage(Voyage voyage, MultipartFile photo) {
        try {
            voyage.setPath(uploadFileService.uploadFile(photo, UploadPath.VOYAGE_DOWNLOAD_LINK));
            Voyage voyageSaced = voyageRepository.save(voyage);
            if (voyage.getMail() != null) {
                String message = "Votre demande de voyage est en cours de traitement, nous vous contacterons pour la suite. Merci d'avoir choisi SUGUBA.";
//                sendEmailService.sendEmailWithAttachment(voyage.getMail(), voyage.getPrenom() + " " + voyage.getNom(), message, "DEMANDE VOYAGE");
            }
            return Response.success(voyageSaced, "Voyage enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la voyage.");
        }
    }

    public Map<String, Object> updateVoyageWithFile(Voyage voyage, MultipartFile photo) {
        try {
            Optional<Voyage> voyageOptional = voyageRepository.findById(voyage.getId());
            if (voyageOptional.isPresent()) {
                voyage.setPath(uploadFileService.uploadFile(photo, UploadPath.VOYAGE_DOWNLOAD_LINK));
                Voyage voyageSaced = voyageRepository.save(voyage);
                return Response.success(voyageSaced, "Voyage modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Voyage n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la voyage.");
        }
    }

    public Map<String, Object> updateVoyageWithoutFile(Voyage voyage) {
        try {
            Optional<Voyage> voyageOptional = voyageRepository.findById(voyage.getId());
            if (voyageOptional.isPresent()) {
                Voyage voyageSaced = voyageRepository.save(voyage);
                return Response.success(voyageSaced, "Voyage modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Voyage n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la voyage.");
        }
    }

    public Map<String, Object> getVoyagesByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Voyage> voyages = voyageRepository.findAll(paging);
            return Response.error(voyages, "Liste des voyages.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getVoyages() {
        try {
            List<Voyage> voyages = voyageRepository.findAll();
            return Response.success(voyages, "Liste des voyages.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
