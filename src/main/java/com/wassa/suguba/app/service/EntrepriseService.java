package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Entreprise;
import com.wassa.suguba.app.repository.EntrepriseRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class EntrepriseService {
    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseService(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    public Map<String, Object> saveEntreprise(Entreprise entreprise) {
        try {
            if (entreprise.getId() == null) {
                Entreprise entrepriseSaved = entrepriseRepository.save(entreprise);
                return Response.success(entrepriseSaved, "Structure enregsitrée");
            } else {
                Optional<Entreprise> entrepriseOptional = entrepriseRepository.findById(entreprise.getId());
                if (entrepriseOptional.isPresent()) {
                    entreprise.setLastModifiedAt(LocalDateTime.now());
                    Entreprise entrepriseUpdated = entrepriseRepository.save(entreprise);
                    return Response.success(entrepriseUpdated, "Structure modifiée");
                } else {
                    return Response.error(new Object(), "Structure inexistante");
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return Response.success(e, "Enregistrement de la structure échouée");
        }
    }

    public Map<String, Object> getStructureByPage(int page, int size) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "lastModifiedAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<Entreprise> entreprises = entrepriseRepository.findAll(paging);
            return Response.success(entreprises, "Liste des structures.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
