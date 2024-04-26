package com.wassa.suguba.app.service;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.SousCategorie;
import com.wassa.suguba.app.repository.SousCategorieRepository;
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
public class SousCategorieService {
    private final SousCategorieRepository sousSousCategorieRepository;
    private final UploadFileService uploadFileService;

    public SousCategorieService(SousCategorieRepository sousSousCategorieRepository, UploadFileService uploadFileService) {
        this.sousSousCategorieRepository = sousSousCategorieRepository;
        this.uploadFileService = uploadFileService;
    }


    public Map<String, Object> saveSousCategorie(SousCategorie sousCategorie, MultipartFile photo) {
        try {
            sousCategorie.setPath(uploadFileService.uploadFile(photo, UploadPath.DOWNLOAD_LINK + "/categorie"));
            SousCategorie sousCategorieSaced = sousSousCategorieRepository.save(sousCategorie);
            return Response.success(sousCategorieSaced, "SousCategorie enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la sousCategorie.");
        }
    }

    public Map<String, Object> updateSousCategorieWithFile(SousCategorie sousCategorie, MultipartFile photo) {
        try {
            Optional<SousCategorie> sousCategorieOptional = sousSousCategorieRepository.findById(sousCategorie.getId());
            if (sousCategorieOptional.isPresent()) {
                sousCategorie.setPath(uploadFileService.uploadFile(photo, UploadPath.DOWNLOAD_LINK + "/categorie"));
                SousCategorie sousCategorieSaced = sousSousCategorieRepository.save(sousCategorie);
                return Response.success(sousCategorieSaced, "SousCategorie modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette SousCategorie n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la sousCategorie.");
        }
    }

    public Map<String, Object> updateSousCategorieWithoutFile(SousCategorie sousCategorie) {
        try {
            Optional<SousCategorie> sousCategorieOptional = sousSousCategorieRepository.findById(sousCategorie.getId());
            if (sousCategorieOptional.isPresent()) {
                SousCategorie sousCategorieSaced = sousSousCategorieRepository.save(sousCategorie);
                return Response.success(sousCategorieSaced, "SousCategorie modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette SousCategorie n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la sousCategorie.");
        }
    }

    public Map<String, Object> getSousCategoriesByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<SousCategorie> sousCategories = sousSousCategorieRepository.findAll(paging);
            return Response.error(sousCategories, "Liste des sousCategories.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getSousCategories() {
        try {
            List<SousCategorie> sousCategories = sousSousCategorieRepository.findAll();
            return Response.success(sousCategories, "Liste des sousCategories.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
