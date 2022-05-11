package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.entity.Entreprise;
import com.wassa.suguba.app.service.EntrepriseService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/entreprises")
public class EntrepriseController {
    private final EntrepriseService entrepriseService;

    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @PostMapping()
    public Map<String, Object> saveEntreprise(@RequestBody Entreprise entreprise) {
        return entrepriseService.saveEntreprise(entreprise);
    }

    @PostMapping("/page")
    public Map<String, Object> getCategoriesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return entrepriseService.getStructureByPage(page, size);
    }

}
