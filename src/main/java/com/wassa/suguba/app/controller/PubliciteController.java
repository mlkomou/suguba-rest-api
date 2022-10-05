package com.wassa.suguba.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Publicite;
import com.wassa.suguba.app.service.PubliciteService;
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
@RequestMapping("/publicites")
public class PubliciteController {
    private final PubliciteService publiciteService;

    public PubliciteController(PubliciteService publiciteService) {
        this.publiciteService = publiciteService;
    }
    @PostMapping()
    public Map<String, Object> savePublicite(@RequestParam("publicite") String publiciteString,
                                             @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Publicite publicite = new ObjectMapper().readValue(publiciteString, Publicite.class);
        return publiciteService.savePublicite(publicite, photo);
    }

    @PutMapping()
    public Map<String, Object> updatePubliciteWithoutFile(@RequestBody Publicite publicite) {
        return publiciteService.updatePubliciteWithoutFile(publicite);
    }

    @PutMapping("/file")
    public Map<String, Object> updatePubliciteWithFile(@RequestParam("publicite") String publiciteString,
                                                       @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Publicite publicite = new ObjectMapper().readValue(publiciteString, Publicite.class);
        return publiciteService.updatePubliciteWithFile(publicite, photo);
    }

    @GetMapping
    public Map<String, Object> getPublicites() {
        return publiciteService.getPublicites();
    }

    @PostMapping("/page")
    public Map<String, Object> getPublicitesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return publiciteService.getPublicitesByPage(page, size);
    }

    @GetMapping("/delete/{id}")
    public Map<String, Object> deletePub(@PathVariable Long id) {
        return publiciteService.deletePub(id);
    }

    @ResponseBody
    @GetMapping("/download/{photo}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        String path = UploadPath.PUBLICITE_DOWNLOAD_LINK;
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
