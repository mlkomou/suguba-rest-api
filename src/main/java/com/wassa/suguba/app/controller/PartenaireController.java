package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.entity.Partenaire;
import com.wassa.suguba.app.payload.PartenaireUsers;
import com.wassa.suguba.app.service.PartenaireService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/partenaires")
public class PartenaireController {
    private final PartenaireService partenaireService;

    public PartenaireController(PartenaireService partenaireService) {
        this.partenaireService = partenaireService;
    }

    @PostMapping()
    public Map<String, Object> savePartenaire(@RequestBody PartenaireUsers partenaire) {
        return partenaireService.savePartenaire(partenaire);
    }

    @PostMapping("/page")
    public Map<String, Object> getCategoriesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return partenaireService.getStructureByPage(page, size);
    }

    @GetMapping("/getAll")
    public Map<String, Object> getAll() {
        return partenaireService.getAllPartenaire();
    }

}
