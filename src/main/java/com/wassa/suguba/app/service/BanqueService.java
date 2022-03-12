package com.wassa.suguba.app.service;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Banque;
import com.wassa.suguba.app.repository.BanqueRepository;
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
public class BanqueService {
    private final BanqueRepository banqueRepository;
    private final UploadFileService uploadFileService;

    public BanqueService(BanqueRepository banqueRepository, UploadFileService uploadFileService) {
        this.banqueRepository = banqueRepository;
        this.uploadFileService = uploadFileService;
    }

    public Map<String, Object> saveBanque(Banque banque, MultipartFile photo) {
        try {
            banque.setPath(uploadFileService.uploadFile(photo, UploadPath.CATEGORIE_DOWNLOAD_LINK));
            Banque banqueSaced = banqueRepository.save(banque);
            return Response.success(banqueSaced, "Banque enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la banque.");
        }
    }

    public Map<String, Object> updateBanqueWithFile(Banque banque, MultipartFile photo) {
        try {
            Optional<Banque> banqueOptional = banqueRepository.findById(banque.getId());
            if (banqueOptional.isPresent()) {
                banque.setPath(uploadFileService.uploadFile(photo, UploadPath.CATEGORIE_DOWNLOAD_LINK));
                Banque banqueSaced = banqueRepository.save(banque);
                return Response.success(banqueSaced, "Banque modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Banque n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la banque.");
        }
    }

    public Map<String, Object> updateBanqueWithoutFile(Banque banque) {
        try {
            Optional<Banque> banqueOptional = banqueRepository.findById(banque.getId());
            if (banqueOptional.isPresent()) {
                Banque banqueSaced = banqueRepository.save(banque);
                return Response.success(banqueSaced, "Banque modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Banque n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la banque.");
        }
    }

    public Map<String, Object> getBanquesByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Banque> banques = banqueRepository.findAll(paging);
            return Response.error(banques, "Liste des banques.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getBanques() {
        try {
            List<Banque> banques = banqueRepository.findAll();
            return Response.success(banques, "Liste des banques.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}