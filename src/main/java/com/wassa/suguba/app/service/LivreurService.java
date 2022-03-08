package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Livreur;
import com.wassa.suguba.app.repository.LivreurRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LivreurService {
    private final LivreurRepository livreurRepository;

    public LivreurService(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }


    public Map<String, Object> saveLivreur(Livreur livreur) {
        try {
            Livreur livreurSaced = livreurRepository.save(livreur);
            return Response.success(livreurSaced, "Livreur enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la livreur.");
        }
    }

    public Map<String, Object> updateLivreurWithoutFile(Livreur livreur) {
        try {
            Optional<Livreur> livreurOptional = livreurRepository.findById(livreur.getId());
            if (livreurOptional.isPresent()) {
                Livreur livreurSaced = livreurRepository.save(livreur);
                return Response.success(livreurSaced, "Livreur modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Livreur n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la livreur.");
        }
    }

    public Map<String, Object> getLivreursByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Livreur> livreurs = livreurRepository.findAll(paging);
            return Response.error(livreurs, "Liste des livreurs.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getLivreurs() {
        try {
            List<Livreur> livreurs = livreurRepository.findAll();
            return Response.error(livreurs, "Liste des livreurs.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
