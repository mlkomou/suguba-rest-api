package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.entity.Livreur;
import com.wassa.suguba.app.service.LivreurService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/livreurs")
public class LivreurController {
    private final LivreurService livreurService;

    public LivreurController(LivreurService livreurService) {
        this.livreurService = livreurService;
    }


    @PostMapping()
    public Map<String, Object> saveLivreur(@RequestBody Livreur livreur) {
        return livreurService.saveLivreur(livreur);
    }

    @PutMapping()
    public Map<String, Object> updateLivreurWithoutFile(@RequestBody Livreur livreur) {
        return livreurService.updateLivreurWithoutFile(livreur);
    }


    @GetMapping
    public Map<String, Object> getLivreurs() {
        return livreurService.getLivreurs();
    }

    @GetMapping("/page")
    public Map<String, Object> getLivreursByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return livreurService.getLivreursByPage(page, size);
    }

}
