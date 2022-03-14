package com.wassa.suguba.app.service;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.PaiementFacture;
import com.wassa.suguba.app.repository.PaiementFactureRepository;
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
public class PaiementFactureService {
    private final PaiementFactureRepository paiementFactureRepository;
    private final UploadFileService uploadFileService;
    private final SendEmailService sendEmailService;

    public PaiementFactureService(PaiementFactureRepository paiementFactureRepository, UploadFileService uploadFileService, SendEmailService sendEmailService) {
        this.paiementFactureRepository = paiementFactureRepository;
        this.uploadFileService = uploadFileService;
        this.sendEmailService = sendEmailService;
    }

    public Map<String, Object> savePaiementFactureWithoutFile(PaiementFacture paiementFacture) {
        try {
            PaiementFacture paiementFactureSaced = paiementFactureRepository.save(paiementFacture);
            if (paiementFacture.getMail() != null) {
                String message = "La demande de paiement de votre facture est en cours de traitement, nous vous contacterons pour la suite. Merci d'avoir choisi SUGUBA.";
                sendEmailService.sendEmailWithAttachment(paiementFacture.getMail(), paiementFacture.getPrenom() + " " + paiementFacture.getNom(), message, "DEMANDE PAIEMENT FACTURE");
                return Response.success(paiementFactureSaced, "PaiementFacture enregistrée.");
            }
            return Response.success(paiementFactureSaced, "PaiementFacture enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la paiementFacture.");
        }
    }

    public Map<String, Object> savePaiementFacture(PaiementFacture paiementFacture, MultipartFile photo) {
        try {
            paiementFacture.setPath(uploadFileService.uploadFile(photo, UploadPath.FACTURE_DOWNLOAD_LINK));
            PaiementFacture paiementFactureSaced = paiementFactureRepository.save(paiementFacture);
            if (paiementFacture.getMail() != null) {
                String message = "La demande de paiement de votre facture est en cours de traitement, nous vous contacterons pour la suite. Merci d'avoir choisi SUGUBA.";
                sendEmailService.sendEmailWithAttachment(paiementFacture.getMail(), paiementFacture.getPrenom() + " " + paiementFacture.getNom(), message, "DEMANDE PAIEMENT FACTURE");
                return Response.success(paiementFactureSaced, "PaiementFacture enregistrée.");
            }
            return Response.success(paiementFactureSaced, "PaiementFacture enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la paiementFacture.");
        }
    }

    public Map<String, Object> updatePaiementFactureWithFile(PaiementFacture paiementFacture, MultipartFile photo) {
        try {
            Optional<PaiementFacture> paiementFactureOptional = paiementFactureRepository.findById(paiementFacture.getId());
            if (paiementFactureOptional.isPresent()) {
                paiementFacture.setPath(uploadFileService.uploadFile(photo, UploadPath.CATEGORIE_DOWNLOAD_LINK));
                PaiementFacture paiementFactureSaced = paiementFactureRepository.save(paiementFacture);
                return Response.success(paiementFactureSaced, "PaiementFacture modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette PaiementFacture n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la paiementFacture.");
        }
    }

    public Map<String, Object> updatePaiementFactureWithoutFile(PaiementFacture paiementFacture) {
        try {
            Optional<PaiementFacture> paiementFactureOptional = paiementFactureRepository.findById(paiementFacture.getId());
            if (paiementFactureOptional.isPresent()) {
                PaiementFacture paiementFactureSaced = paiementFactureRepository.save(paiementFacture);
                return Response.success(paiementFactureSaced, "PaiementFacture modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette PaiementFacture n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la paiementFacture.");
        }
    }

    public Map<String, Object> getPaiementFacturesByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<PaiementFacture> paiementFactures = paiementFactureRepository.findAll(paging);
            return Response.error(paiementFactures, "Liste des paiementFactures.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getPaiementFactures() {
        try {
            List<PaiementFacture> paiementFactures = paiementFactureRepository.findAll();
            return Response.success(paiementFactures, "Liste des paiementFactures.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
