package com.wassa.suguba.app.service;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Pharmacie;
import com.wassa.suguba.app.repository.PharmacieRepository;
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
public class PharmacieService {
    private final PharmacieRepository pharmacieRepository;
    private final UploadFileService uploadFileService;

    public PharmacieService(PharmacieRepository pharmacieRepository, UploadFileService uploadFileService) {
        this.pharmacieRepository = pharmacieRepository;
        this.uploadFileService = uploadFileService;
    }

    public Map<String, Object> savePharmacie(Pharmacie pharmacie, MultipartFile photo) {
        try {
            pharmacie.setPath(uploadFileService.uploadFile(photo, UploadPath.PHARMACIE_DOWNLOAD_LINK));
            Pharmacie pharmacieSaced = pharmacieRepository.save(pharmacie);
            return Response.success(pharmacieSaced, "Ordonance envoyée avec succès.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de l'ordonance.");
        }
    }

    public Map<String, Object> updatePharmacieWithFile(Pharmacie pharmacie, MultipartFile photo) {
        try {
            Optional<Pharmacie> pharmacieOptional = pharmacieRepository.findById(pharmacie.getId());
            if (pharmacieOptional.isPresent()) {
                pharmacie.setPath(uploadFileService.uploadFile(photo, UploadPath.PHARMACIE_DOWNLOAD_LINK));
                Pharmacie pharmacieSaced = pharmacieRepository.save(pharmacie);
                return Response.success(pharmacieSaced, "ordonance modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette ordonance n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de l'ordonance.");
        }
    }

    public Map<String, Object> updatePharmacieWithoutFile(Pharmacie pharmacie) {
        try {
            Optional<Pharmacie> pharmacieOptional = pharmacieRepository.findById(pharmacie.getId());
            if (pharmacieOptional.isPresent()) {
                Pharmacie pharmacieSaced = pharmacieRepository.save(pharmacie);
                return Response.success(pharmacieSaced, "ordonance modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette ordonance n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de l'ordonance.");
        }
    }

    public Map<String, Object> getPharmaciesByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Pharmacie> pharmacies = pharmacieRepository.findAll(paging);
            return Response.error(pharmacies, "Liste des ordonances.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getPharmacies() {
        try {
            List<Pharmacie> pharmacies = pharmacieRepository.findAll();
            return Response.success(pharmacies, "Liste des ordonances.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
