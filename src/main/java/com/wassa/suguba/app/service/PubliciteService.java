package com.wassa.suguba.app.service;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Publicite;
import com.wassa.suguba.app.repository.PubliciteRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PubliciteService {
    private final PubliciteRepository publiciteRepository;
    private final UploadFileService uploadFileService;

    public PubliciteService(PubliciteRepository publiciteRepository, UploadFileService uploadFileService) {
        this.publiciteRepository = publiciteRepository;
        this.uploadFileService = uploadFileService;
    }
    public Map<String, Object> savePublicite(Publicite publicite, MultipartFile photo) {
        try {
            publicite.setPath(uploadFileService.uploadFile(photo, UploadPath.PUBLICITE_DOWNLOAD_LINK));
            Publicite publiciteSaced = publiciteRepository.save(publicite);
            return Response.success(publiciteSaced, "Publicite enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la publicite.");
        }
    }

    public Map<String, Object> updatePubliciteWithFile(Publicite publicite, MultipartFile photo) {
        try {
            Optional<Publicite> publiciteOptional = publiciteRepository.findById(publicite.getId());
            if (publiciteOptional.isPresent()) {
                publicite.setPath(uploadFileService.uploadFile(photo, UploadPath.PUBLICITE_DOWNLOAD_LINK));
                Publicite publiciteSaced = publiciteRepository.save(publicite);
                return Response.success(publiciteSaced, "Publicite modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Publicite n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la publicite.");
        }
    }

    public Map<String, Object> updatePubliciteWithoutFile(Publicite publicite) {
        try {
            Optional<Publicite> publiciteOptional = publiciteRepository.findById(publicite.getId());
            if (publiciteOptional.isPresent()) {
                Publicite publiciteSaced = publiciteRepository.save(publicite);
                return Response.success(publiciteSaced, "Publicite modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Publicite n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la publicite.");
        }
    }

    public Map<String, Object> getPublicitesByPage(int page, int size) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<Publicite> publicites = publiciteRepository.findAllByActive(true, paging);
            return Response.success(publicites, "Liste des publicites.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getPublicites() {
        try {
            List<Publicite> publicites = publiciteRepository.findAll();
            return Response.success(publicites, "Liste des publicites.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
