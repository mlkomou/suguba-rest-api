package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Commande;
import com.wassa.suguba.app.payload.CommandePayload;
import com.wassa.suguba.app.service.CommandeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @ResponseBody
    @GetMapping("/logo/{photo}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        String path = UploadPath.LOGO_DOWNLOAD_LINK;
        try {
            Path fileName = Paths.get(path, photo);
            byte[] buffer = Files.readAllBytes(fileName);
            ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
            return ResponseEntity.ok()
                    .contentLength(buffer.length)
                    .contentType(MediaType.parseMediaType("image/png"))
                    .body(byteArrayResource);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return ResponseEntity.badRequest().build();
    }
}
