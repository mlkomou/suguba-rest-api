package com.wassa.suguba.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Pharmacie;
import com.wassa.suguba.app.service.PharmacieService;
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
@RequestMapping("/pharmacies")
public class PharmacieController {
    private final PharmacieService pharmacieService;

    public PharmacieController(PharmacieService pharmacieService) {
        this.pharmacieService = pharmacieService;
    }

    @PostMapping()
    public Map<String, Object> savePharmacie(@RequestParam("pharmacie") String pharmacieString,
                                             @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Pharmacie pharmacie = new ObjectMapper().readValue(pharmacieString, Pharmacie.class);
        return pharmacieService.savePharmacie(pharmacie, photo);
    }

    @PutMapping()
    public Map<String, Object> updatePharmacieWithoutFile(@RequestBody Pharmacie pharmacie) {
        return pharmacieService.updatePharmacieWithoutFile(pharmacie);
    }

    @PutMapping("/file")
    public Map<String, Object> updatePharmacieWithFile(@RequestParam("pharmacie") String pharmacieString,
                                                       @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Pharmacie pharmacie = new ObjectMapper().readValue(pharmacieString, Pharmacie.class);
        return pharmacieService.updatePharmacieWithFile(pharmacie, photo);
    }

    @GetMapping
    public Map<String, Object> getPharmacies() {
        return pharmacieService.getPharmacies();
    }

    @GetMapping("/page")
    public Map<String, Object> getPharmaciesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return pharmacieService.getPharmaciesByPage(page, size);
    }

    @ResponseBody
    @GetMapping("/download/{photo}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        String path = UploadPath.PHARMACIE_DOWNLOAD_LINK;
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
