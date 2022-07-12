package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Fournisseur;
import com.wassa.suguba.app.entity.Partenaire;
import com.wassa.suguba.app.payload.PartenaireUsers;
import com.wassa.suguba.app.repository.PartenaireRepository;
import com.wassa.suguba.authentification.entity.ApplicationUser;
import com.wassa.suguba.authentification.entity.Response;
import com.wassa.suguba.authentification.repo.ApplicationUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PartenaireService {
    private final PartenaireRepository partenaireRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CompteurService compteurService;

    public PartenaireService(PartenaireRepository partenaireRepository, ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, CompteurService compteurService) {
        this.partenaireRepository = partenaireRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.compteurService = compteurService;
    }

    public Map<String, Object> savePartenaire(PartenaireUsers partenaireUsers) {
        try {

            if (partenaireUsers.getPartenaire().getId() == null) {
                partenaireUsers.getPartenaire().setCodePartenaire(makeCodePartenaire());
                Partenaire partenaireSaved = partenaireRepository.save(partenaireUsers.getPartenaire());

                if (!partenaireUsers.getUsers().isEmpty()) {
                    List<ApplicationUser> users = partenaireUsers.getUsers();
                    users.forEach(user -> {
                        user.setPartenaire(partenaireSaved);
                        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                        applicationUserRepository.save(user);
                    });
                }
                return Response.success(partenaireSaved, "Structure enregsitrée");
            } else if (partenaireUsers.getPartenaire().getId() != null){
                Optional<Partenaire> partenaireOptional = partenaireRepository.findById(partenaireUsers.getPartenaire().getId());
                if (partenaireOptional.isPresent()) {
                    if (partenaireUsers.getPartenaire().getCodePartenaire() != null) {
                        partenaireUsers.getPartenaire().setCodePartenaire(partenaireOptional.get().getCodePartenaire());
                    } else {
                        partenaireUsers.getPartenaire().setCodePartenaire(makeCodePartenaire());
                    }
                    partenaireUsers.getPartenaire().setLastModifiedAt(LocalDateTime.now());
                    Partenaire partenaireUpdated = partenaireRepository.save(partenaireUsers.getPartenaire());
                    return Response.success(partenaireUpdated, "Partenaire modifié");
                } else {
                    return Response.error(new Object(), "Partenaire inexistant");
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(e);
            return Response.error(e, "Enregistrement du partenaire échoué");
        }
    }

    public Map<String, Object> getStructureByPage(int page, int size) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "lastModifiedAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<Partenaire> partenaires = partenaireRepository.findAll(paging);
            return Response.success(partenaires, "Liste des structures.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getAllPartenaire() {
        try {
            List<Partenaire> partenaires = partenaireRepository.findAll();
            return Response.success(partenaires, "Tous les partenaires");
        } catch (Exception e) {
            return Response.error(e, "Erreur de récupération");
        }
    }

    public ResponseEntity<Map<String, Object>> getPartenaireByCode(String codeFournisseur) {
        try {
            Optional<Partenaire> partenaire = partenaireRepository.findByCodePartenaire(codeFournisseur);
            if (partenaire.isPresent()) {
                return new ResponseEntity<>(Response.success(partenaire, "Partenaire trouvé"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Response.success(null, "Aucun partenaire trouvé"), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Response.error(e, "Impossible de trouver le partenaire, veuillez réessayer plus tard."), HttpStatus.OK);
        }
    }

    private String makeCodePartenaire() {
        System.out.println("start get max");
        Integer maxNumber = compteurService.getNumberMax();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (maxNumber.toString().length() == 1) {
            return "SUGUBA_" + year + "_PART_" + "0000000000" + maxNumber;
        }
        if (maxNumber.toString().length() == 2) {
            return "SUGUBA_" + year + "_PART_" + "000000000" + maxNumber;
        }
        if (maxNumber.toString().length() == 3) {
            return "SUGUBA_" + year + "_PART_" + "00000000" + maxNumber;
        }
        if (maxNumber.toString().length() == 4) {
            return "SUGUBA_" + year + "_PART_" + "0000000" + maxNumber;
        }
        if (maxNumber.toString().length() == 5) {
            return "SUGUBA_" + year + "_PART_" + "000000" + maxNumber;
        }
        if (maxNumber.toString().length() == 6) {
            return "SUGUBA_" + year + "_PART_" + "00000" + maxNumber;
        }
        if (maxNumber.toString().length() == 7) {
            return "SUGUBA_" + year + "_PART_" + "0000" + maxNumber;
        }
        if (maxNumber.toString().length() == 8) {
            return "SUGUBA_" + year + "_PART_" + "000" + maxNumber;
        }
        if (maxNumber.toString().length() == 9) {
            return "SUGUBA_" + year + "_PART_" + "00" + maxNumber;
        }
        if (maxNumber.toString().length() == 10) {
            return "SUGUBA_" + year + "_PART_" + "0" + maxNumber;
        } else {
            return "SUGUBA_" + year + "_PART_" + maxNumber;
        }

    }
}
