package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.entity.Fournisseur;
import com.wassa.suguba.app.service.FournisseurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fournisseurs")
public class FournisseurController {
    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @GetMapping("/bycode/{codeFournisseur}")
    public ResponseEntity<Map<String, Object>> getFournisseurByCode(@PathVariable String codeFournisseur) {
        return fournisseurService.getFournisseurByCode(codeFournisseur);
    }

    @PostMapping()
    public Map<String, Object> saveFournisseur(@RequestBody Fournisseur entreprise) {
        return fournisseurService.saveFournisseur(entreprise);
    }

    @PostMapping("/page")
    public Map<String, Object> getCategoriesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return fournisseurService.getFournisseurByPage(page, size);
    }

}
