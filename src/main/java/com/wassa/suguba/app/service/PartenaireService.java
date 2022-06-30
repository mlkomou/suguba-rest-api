package com.wassa.suguba.app.service;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PartenaireService {
    private final PartenaireRepository partenaireRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PartenaireService(PartenaireRepository partenaireRepository, ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.partenaireRepository = partenaireRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Map<String, Object> savePartenaire(PartenaireUsers partenaireUsers) {
        try {
            if (partenaireUsers.getPartenaire().getId() == null) {
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
                    partenaireUsers.getPartenaire().setLastModifiedAt(LocalDateTime.now());
                    Partenaire partenaireUpdated = partenaireRepository.save(partenaireUsers.getPartenaire());
                    return Response.success(partenaireUpdated, "Structure modifiée");
                } else {
                    return Response.error(new Object(), "Structure inexistante");
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(e);
            return Response.error(e, "Enregistrement de la structure échouée");
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
}
