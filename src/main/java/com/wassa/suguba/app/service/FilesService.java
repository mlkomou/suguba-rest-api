package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Files;
import com.wassa.suguba.app.repository.FilesRepository;
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
public class FilesService {
    private final FilesRepository filesRepository;

    public FilesService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }


    public Map<String, Object> saveFiles(Files files) {
        try {
            Files filesSaced = filesRepository.save(files);
            return Response.success(filesSaced, "Files enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la files.");
        }
    }

    public Map<String, Object> updateFilesWithoutFile(Files files) {
        try {
            Optional<Files> filesOptional = filesRepository.findById(files.getId());
            if (filesOptional.isPresent()) {
                Files filesSaced = filesRepository.save(files);
                return Response.success(filesSaced, "Files modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Files n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la files.");
        }
    }

    public Map<String, Object> getFilessByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Files> filess = filesRepository.findAll(paging);
            return Response.error(filess, "Liste des filess.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getFiless() {
        try {
            List<Files> filess = filesRepository.findAll();
            return Response.error(filess, "Liste des filess.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
