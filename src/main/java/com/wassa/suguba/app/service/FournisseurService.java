package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Fournisseur;
import com.wassa.suguba.app.repository.FournisseurRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

@Service
public class FournisseurService {
    private final FournisseurRepository fournisseurRepository;
    private final CompteurService compteurService;

    public FournisseurService(FournisseurRepository fournisseurRepository, CompteurService compteurService) {
        this.fournisseurRepository = fournisseurRepository;
        this.compteurService = compteurService;
    }

    public Map<String, Object> saveFournisseur(Fournisseur fournisseur) {
        try {

            if (fournisseur.getId() == null) {
                fournisseur.setCodeFournisseur(makeCodeFournisseur());
                Fournisseur fournisseurSaved = fournisseurRepository.save(fournisseur);
                return Response.success(fournisseurSaved, "Fournisseur enregsitré");
            } else {
                Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(fournisseur.getId());
                if (fournisseurOptional.isPresent()) {
                    Fournisseur fournisseurOld = fournisseurOptional.get();
                    if (fournisseurOld.getCodeFournisseur() == null) {
                        fournisseur.setCodeFournisseur(makeCodeFournisseur());
                    }
                    fournisseur.setLastModifiedAt(LocalDateTime.now());
                    Fournisseur fournisseurUpdated = fournisseurRepository.save(fournisseur);
                    return Response.success(fournisseurUpdated, "Fournisseur modifié");
                } else {
                    return Response.error(new Object(), "Fournisseur inexistant");
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return Response.success(e, "Enregistrement du fournisseur échoué");
        }
    }

    public Map<String, Object> getFournisseurByPage(int page, int size) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "lastModifiedAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<Fournisseur> fournisseurs = fournisseurRepository.findAll(paging);
            return Response.success(fournisseurs, "Liste des structures.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public ResponseEntity<Map<String, Object>> getFournisseurByCode(String codeFournisseur) {
        try {
            Optional<Fournisseur> fournisseur = fournisseurRepository.findByCodeFournisseur(codeFournisseur);
            if (fournisseur.isPresent()) {
                return new ResponseEntity<>(Response.success(fournisseur, "Fournisseur trouvé"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Response.success(null, "Aucun fournisseur trouvé"), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Response.error(e, "Impossible de trouver le fournisseur, veuillez réessayer plus tard."), HttpStatus.OK);
        }
    }

    private String makeCodeFournisseur() {
        System.out.println("start get max");
        Integer maxNumber = compteurService.getNumberMax();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (maxNumber.toString().length() == 1) {
            return "SUGUBA_" + year + "_FOUR_" + "0000000000" + maxNumber;
        }
        if (maxNumber.toString().length() == 2) {
            return "SUGUBA_" + year + "_FOUR_" + "000000000" + maxNumber;
        }
        if (maxNumber.toString().length() == 3) {
            return "SUGUBA_" + year + "_FOUR_" + "00000000" + maxNumber;
        }
        if (maxNumber.toString().length() == 4) {
            return "SUGUBA_" + year + "_FOUR_" + "0000000" + maxNumber;
        }
        if (maxNumber.toString().length() == 5) {
            return "SUGUBA_" + year + "_FOUR_" + "000000" + maxNumber;
        }
        if (maxNumber.toString().length() == 6) {
            return "SUGUBA_" + year + "_FOUR_" + "00000" + maxNumber;
        }
        if (maxNumber.toString().length() == 7) {
            return "SUGUBA_" + year + "_FOUR_" + "0000" + maxNumber;
        }
        if (maxNumber.toString().length() == 8) {
            return "SUGUBA_" + year + "_FOUR_" + "000" + maxNumber;
        }
        if (maxNumber.toString().length() == 9) {
            return "SUGUBA_" + year + "_FOUR_" + "00" + maxNumber;
        }
        if (maxNumber.toString().length() == 10) {
            return "SUGUBA_" + year + "_FOUR_" + "0" + maxNumber;
        } else {
            return "SUGUBA_" + year + "_FOUR_" + maxNumber;
        }

    }
}
