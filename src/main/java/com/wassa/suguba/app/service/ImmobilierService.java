package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Immobilier;
import com.wassa.suguba.app.repository.ImmobilierRepository;
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
public class ImmobilierService {
    private final ImmobilierRepository immobilierRepository;

    public ImmobilierService(ImmobilierRepository immobilierRepository) {
        this.immobilierRepository = immobilierRepository;
    }

    public Map<String, Object> saveImmobilier(Immobilier immobilier) {
        try {
            Immobilier immobilierSaced = immobilierRepository.save(immobilier);
            return Response.success(immobilierSaced, "Immobilier enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la immobilier.");
        }
    }

    public Map<String, Object> updateImmobilierWithoutFile(Immobilier immobilier) {
        try {
            Optional<Immobilier> immobilierOptional = immobilierRepository.findById(immobilier.getId());
            if (immobilierOptional.isPresent()) {
                Immobilier immobilierSaced = immobilierRepository.save(immobilier);
                return Response.success(immobilierSaced, "Immobilier modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Immobilier n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la immobilier.");
        }
    }

    public Map<String, Object> getImmobiliersByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Immobilier> immobiliers = immobilierRepository.findAll(paging);
            return Response.error(immobiliers, "Liste des immobiliers.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getImmobiliers() {
        try {
            List<Immobilier> immobiliers = immobilierRepository.findAll();
            return Response.error(immobiliers, "Liste des immobiliers.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
