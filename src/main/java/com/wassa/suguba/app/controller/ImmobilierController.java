package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.entity.Immobilier;
import com.wassa.suguba.app.service.ImmobilierService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/immobiliers")
public class ImmobilierController {
    private final ImmobilierService immobilierService;

    public ImmobilierController(ImmobilierService immobilierService) {
        this.immobilierService = immobilierService;
    }


    @PostMapping()
    public Map<String, Object> saveImmobilier(@RequestBody Immobilier immobilier) {
        return immobilierService.saveImmobilier(immobilier);
    }

    @PutMapping()
    public Map<String, Object> updateImmobilierWithoutFile(@RequestBody Immobilier immobilier) {
        return immobilierService.updateImmobilierWithoutFile(immobilier);
    }


    @GetMapping
    public Map<String, Object> getImmobiliers() {
        return immobilierService.getImmobiliers();
    }

    @GetMapping("/page")
    public Map<String, Object> getImmobiliersByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return immobilierService.getImmobiliersByPage(page, size);
    }

}
