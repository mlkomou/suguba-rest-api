package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.ProduitCommande;
import com.wassa.suguba.app.repository.ProduitCommandeRepository;
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
public class ProduitCommandeService {
    private final ProduitCommandeRepository produitCommandeRepository;

    public ProduitCommandeService(ProduitCommandeRepository produitCommandeRepository) {
        this.produitCommandeRepository = produitCommandeRepository;
    }

    public Map<String, Object> saveProduitCommande(ProduitCommande produit) {
        try {
            ProduitCommande produitSaced = produitCommandeRepository.save(produit);
            return Response.success(produitSaced, "ProduitCommande enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la produit.");
        }
    }

    public Map<String, Object> updateProduitCommandeWithoutFile(ProduitCommande produit) {
        try {
            Optional<ProduitCommande> produitOptional = produitCommandeRepository.findById(produit.getId());
            if (produitOptional.isPresent()) {
                ProduitCommande produitSaced = produitCommandeRepository.save(produit);
                return Response.success(produitSaced, "ProduitCommande modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette ProduitCommande n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la produit.");
        }
    }

    public Map<String, Object> getProduitCommandesByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<ProduitCommande> produits = produitCommandeRepository.findAll(paging);
            return Response.error(produits, "Liste des produits.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getProduitCommandes() {
        try {
            List<ProduitCommande> produits = produitCommandeRepository.findAll();
            return Response.error(produits, "Liste des produits.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
