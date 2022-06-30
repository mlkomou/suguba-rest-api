package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.payload.SouscriptionClient;
import com.wassa.suguba.app.service.SouscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/souscriptions")
public class SouscriptionController {
    private final SouscriptionService souscriptionService;

    public SouscriptionController(SouscriptionService souscriptionService) {
        this.souscriptionService = souscriptionService;
    }

    @PostMapping("/complete")
    Map<String, Object> compleSousciption(@RequestBody SouscriptionClient souscriptionClient) {
        return souscriptionService.compleAcount(souscriptionClient);
    }

    @PostMapping("/get_by_active")
    Map<String , Object> getSouscritionByActive(@RequestParam("page") int page,
                                                @RequestParam("size") int size,
                                                @RequestParam("active") boolean active) {
        return souscriptionService.getSouscritonByStatut(page, size, active);
    }

    @GetMapping("/active/{id}/{statut}")
    Map<String, Object> activeDesactive(@PathVariable Long id, @PathVariable boolean statut) {
        return souscriptionService.activeAndDesactiveSouscr(id, statut);
    }

    @GetMapping("/{id}")
    Map<String, Object> getByUser(@PathVariable Long id) {
        return souscriptionService.getSouscriptionByUser(id);
    }
}
