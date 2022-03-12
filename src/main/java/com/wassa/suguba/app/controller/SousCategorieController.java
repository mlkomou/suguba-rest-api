package com.wassa.suguba.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.SousCategorie;
import com.wassa.suguba.app.service.SousCategorieService;
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
@RequestMapping("/sous-categories")
public class SousCategorieController {
    private final SousCategorieService sousSousCategorieService;

    public SousCategorieController(SousCategorieService sousSousCategorieService) {
        this.sousSousCategorieService = sousSousCategorieService;
    }


    @PostMapping()
    public Map<String, Object> saveSousCategorie(@RequestParam("categorie") String categorieString,
                                             @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        SousCategorie categorie = new ObjectMapper().readValue(categorieString, SousCategorie.class);
        return sousSousCategorieService.saveSousCategorie(categorie, photo);
    }

    @PutMapping()
    public Map<String, Object> updateSousCategorieWithoutFile(@RequestBody SousCategorie categorie) {
        return sousSousCategorieService.updateSousCategorieWithoutFile(categorie);
    }

    @PutMapping("/file")
    public Map<String, Object> updateSousCategorieWithFile(@RequestParam("categorie") String categorieString,
                                                       @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        SousCategorie categorie = new ObjectMapper().readValue(categorieString, SousCategorie.class);
        return sousSousCategorieService.updateSousCategorieWithFile(categorie, photo);
    }

    @GetMapping
    public Map<String, Object> getSousCategories() {
        return sousSousCategorieService.getSousCategories();
    }

    @GetMapping("/page")
    public Map<String, Object> getSousCategoriesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return sousSousCategorieService.getSousCategoriesByPage(page, size);
    }

    @ResponseBody
    @GetMapping("/download/{photo}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        String path = UploadPath.CATEGORIE_DOWNLOAD_LINK;
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
