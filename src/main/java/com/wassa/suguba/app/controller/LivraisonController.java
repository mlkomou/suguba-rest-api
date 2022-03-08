package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.entity.Livraison;
import com.wassa.suguba.app.service.LivraisonService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/livraisons")
public class LivraisonController {
    private final LivraisonService livraisonService;

    public LivraisonController(LivraisonService livraisonService) {
        this.livraisonService = livraisonService;
    }


    @PostMapping()
    public Map<String, Object> saveLivraison(@RequestBody Livraison livraison) {
        return livraisonService.saveLivraison(livraison);
    }

    @PutMapping()
    public Map<String, Object> updateLivraisonWithoutFile(@RequestBody Livraison livraison) {
        return livraisonService.updateLivraisonWithoutFile(livraison);
    }


    @GetMapping
    public Map<String, Object> getLivraisons() {
        return livraisonService.getLivraisons();
    }

    @GetMapping("/page")
    public Map<String, Object> getLivraisonsByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return livraisonService.getLivraisonsByPage(page, size);
    }

}
