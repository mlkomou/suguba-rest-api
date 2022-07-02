package com.wassa.suguba.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Categorie;
import com.wassa.suguba.app.entity.Produit;
import com.wassa.suguba.app.service.ProduitService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produits")
public class ProduitController {
    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }


    @PostMapping()
    public Map<String, Object> saveProduit(@RequestParam("produit") String produitString, @RequestParam("files") List<MultipartFile> files) throws JsonProcessingException {
        Produit produit = new ObjectMapper().readValue(produitString, Produit.class);
        return produitService.saveProduit(produit, files);
    }

    @PutMapping()
    public Map<String, Object> updateProduitWithoutFile(@RequestBody Produit produit) {
        return produitService.updateProduitWithoutFile(produit);
    }


    @GetMapping
    public Map<String, Object> getProduits() {
        return produitService.getProduits();
    }

    @GetMapping("/categorie/{catId}/{page}/{size}")
    public Map<String, Object> getProduitsByCategorie(@PathVariable Long catId,
                                                      @PathVariable int page,
                                                      @PathVariable int size) {
        return produitService.getProduitsByCategorie(catId, page, size);
    }

    @PostMapping("/page")
    public Map<String, Object> getProduitsByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return produitService.getProduitsByPage(page, size);
    }

    @PostMapping("/deepSearch")
    public Map<String, Object> deepSearch(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("searchTerm") String searchTerm) {
        return produitService.deepSearch(searchTerm, page, size);
    }

    @ResponseBody
    @GetMapping("/download/{photo}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        String path = UploadPath.PRODUIT_DOWNLOAD_LINK;
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
