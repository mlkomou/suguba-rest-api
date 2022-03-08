package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Livraison;
import com.wassa.suguba.app.repository.LivraisonRepository;
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
public class LivraisonService {
    private final LivraisonRepository livraisonRepository;

    public LivraisonService(LivraisonRepository livraisonRepository) {
        this.livraisonRepository = livraisonRepository;
    }


    public Map<String, Object> saveLivraison(Livraison livraison) {
        try {
            Livraison livraisonSaced = livraisonRepository.save(livraison);
            return Response.success(livraisonSaced, "Livraison enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la livraison.");
        }
    }

    public Map<String, Object> updateLivraisonWithoutFile(Livraison livraison) {
        try {
            Optional<Livraison> livraisonOptional = livraisonRepository.findById(livraison.getId());
            if (livraisonOptional.isPresent()) {
                Livraison livraisonSaced = livraisonRepository.save(livraison);
                return Response.success(livraisonSaced, "Livraison modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Livraison n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la livraison.");
        }
    }

    public Map<String, Object> getLivraisonsByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Livraison> livraisons = livraisonRepository.findAll(paging);
            return Response.error(livraisons, "Liste des livraisons.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getLivraisons() {
        try {
            List<Livraison> livraisons = livraisonRepository.findAll();
            return Response.error(livraisons, "Liste des livraisons.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
