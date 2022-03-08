package com.wassa.suguba.app.service;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Categorie;
import com.wassa.suguba.app.repository.CategorieRepository;
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
public class CategorieService {
    private final CategorieRepository categorieRepository;
    private final UploadFileService uploadFileService;

    public CategorieService(CategorieRepository categorieRepository, UploadFileService uploadFileService) {
        this.categorieRepository = categorieRepository;
        this.uploadFileService = uploadFileService;
    }
    public Map<String, Object> saveCategorie(Categorie categorie, MultipartFile photo) {
        try {
            categorie.setPath(uploadFileService.uploadFile(photo, UploadPath.CATEGORIE_DOWNLOAD_LINK));
            Categorie categorieSaced = categorieRepository.save(categorie);
            return Response.success(categorieSaced, "Categorie enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la categorie.");
        }
    }

    public Map<String, Object> updateCategorieWithFile(Categorie categorie, MultipartFile photo) {
        try {
            Optional<Categorie> categorieOptional = categorieRepository.findById(categorie.getId());
            if (categorieOptional.isPresent()) {
                categorie.setPath(uploadFileService.uploadFile(photo, UploadPath.CATEGORIE_DOWNLOAD_LINK));
                Categorie categorieSaced = categorieRepository.save(categorie);
                return Response.success(categorieSaced, "Categorie modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Categorie n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la categorie.");
        }
    }

    public Map<String, Object> updateCategorieWithoutFile(Categorie categorie) {
        try {
            Optional<Categorie> categorieOptional = categorieRepository.findById(categorie.getId());
            if (categorieOptional.isPresent()) {
                Categorie categorieSaced = categorieRepository.save(categorie);
                return Response.success(categorieSaced, "Categorie modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Categorie n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la categorie.");
        }
    }

    public Map<String, Object> getCategoriesByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Categorie> categories = categorieRepository.findAll(paging);
            return Response.error(categories, "Liste des categories.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getCategories() {
        try {
            List<Categorie> categories = categorieRepository.findAll();
            return Response.success(categories, "Liste des categories.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
