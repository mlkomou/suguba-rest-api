package com.wassa.suguba.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Categorie;
import com.wassa.suguba.app.service.CategorieService;
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
@RequestMapping("/categories")
public class CategorieController {
    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }
    @PostMapping()
    public Map<String, Object> saveCategorie(@RequestParam("categorie") String categorieString,
                                             @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Categorie categorie = new ObjectMapper().readValue(categorieString, Categorie.class);
        return categorieService.saveCategorie(categorie, photo);
    }

    @PutMapping()
    public Map<String, Object> updateCategorieWithoutFile(@RequestBody Categorie categorie) {
        return categorieService.updateCategorieWithoutFile(categorie);
    }

    @PutMapping("/file")
    public Map<String, Object> updateCategorieWithFile(@RequestParam("categorie") String categorieString,
                                             @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Categorie categorie = new ObjectMapper().readValue(categorieString, Categorie.class);
        return categorieService.updateCategorieWithFile(categorie, photo);
    }

    @GetMapping
    public Map<String, Object> getCategories() {
        return categorieService.getCategories();
    }

    @PostMapping("/page")
    public Map<String, Object> getCategoriesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return categorieService.getCategoriesByPage(page, size);
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
            System.err.println(e);
            // TODO: handle exception
        }
        return ResponseEntity.badRequest().build();
    }

}
