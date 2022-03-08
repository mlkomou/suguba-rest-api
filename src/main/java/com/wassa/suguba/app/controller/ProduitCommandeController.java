package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.entity.ProduitCommande;
import com.wassa.suguba.app.service.ProduitCommandeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/produitCommande")
public class ProduitCommandeController {
    private final ProduitCommandeService produitCommandeService;

    public ProduitCommandeController(ProduitCommandeService produitCommandeService) {
        this.produitCommandeService = produitCommandeService;
    }


    @PostMapping()
    public Map<String, Object> saveProduitCommande(@RequestBody ProduitCommande produitCommande) {
        return produitCommandeService.saveProduitCommande(produitCommande);
    }

    @PutMapping()
    public Map<String, Object> updateProduitCommandeWithoutFile(@RequestBody ProduitCommande produitCommande) {
        return produitCommandeService.updateProduitCommandeWithoutFile(produitCommande);
    }


    @GetMapping
    public Map<String, Object> getProduitCommandes() {
        return produitCommandeService.getProduitCommandes();
    }

    @GetMapping("/page")
    public Map<String, Object> getProduitCommandesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return produitCommandeService.getProduitCommandesByPage(page, size);
    }

}
