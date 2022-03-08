package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.entity.Commande;
import com.wassa.suguba.app.payload.CommandePayload;
import com.wassa.suguba.app.service.CommandeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/commandes")
public class CommandeController {
    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping()
    public Map<String, Object> saveCommande(@RequestBody CommandePayload commande) {
        return commandeService.saveCommande(commande);
    }

    @PutMapping()
    public Map<String, Object> updateCommandeWithoutFile(@RequestBody Commande commande) {
        return commandeService.updateCommandeWithoutFile(commande);
    }


    @GetMapping
    public Map<String, Object> getCommandes() {
        return commandeService.getCommandes();
    }

    @GetMapping("/page")
    public Map<String, Object> getCommandesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return commandeService.getCommandesByPage(page, size);
    }

}
