package com.wassa.suguba.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Immobilier;
import com.wassa.suguba.app.payload.UpdateStatut;
import com.wassa.suguba.app.service.ImmobilierService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
@RestController
@RequestMapping("/immobiliers")
public class ImmobilierController {
    private final ImmobilierService immobilierService;

    public ImmobilierController(ImmobilierService immobilierService) {
        this.immobilierService = immobilierService;
    }
    @PostMapping("/file")
    public Map<String, Object> savePaiementFacture(@RequestParam("immobilier") String immobilierString,
                                                   @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Immobilier immobilier = new ObjectMapper().readValue(immobilierString, Immobilier.class);
        return immobilierService.saveImmobilierWithFile(immobilier, photo);
    }


    @PostMapping()
    public Map<String, Object> saveImmobilier(@RequestBody Immobilier immobilier) {
        return immobilierService.saveImmobilier(immobilier);
    }

    @PutMapping()
    public Map<String, Object> updateImmobilierWithoutFile(@RequestBody Immobilier immobilier) {
        return immobilierService.updateImmobilierWithoutFile(immobilier);
    }


    @GetMapping
    public Map<String, Object> getImmobiliers() {
        return immobilierService.getImmobiliers();
    }

    @PostMapping("/page")
    public Map<String, Object> getImmobiliersByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return immobilierService.getImmobiliersByPage(page, size);
    }
    @PostMapping("/statut")
    public Map<String, Object> updateStatut(@RequestBody UpdateStatut updateStatut) {
        return immobilierService.updateStatut(updateStatut);
    }

    @ResponseBody
    @GetMapping("/download/{photo}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        String path = UploadPath.DOWNLOAD_LINK + "/immobilier";
        try {
            Path fileName = Paths.get(path, photo);
            byte[] buffer = Files.readAllBytes(fileName);
            ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
            return ResponseEntity.ok()
                    .contentLength(buffer.length)
                    .contentType(MediaType.parseMediaType("image/png"))
                    .body(byteArrayResource);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return ResponseEntity.badRequest().build();
    }

}
