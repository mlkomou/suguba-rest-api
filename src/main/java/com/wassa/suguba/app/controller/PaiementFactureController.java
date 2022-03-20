package com.wassa.suguba.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.PaiementFacture;
import com.wassa.suguba.app.payload.UpdateStatut;
import com.wassa.suguba.app.service.PaiementFactureService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/paiement_factures")
public class PaiementFactureController {
    private final PaiementFactureService paiementFactureService;

    public PaiementFactureController(PaiementFactureService paiementFactureService) {
        this.paiementFactureService = paiementFactureService;
    }

    @PostMapping("/without-file")
    public Map<String, Object> savePaiementFactureWithoutFile(@RequestBody PaiementFacture paiementFacture)  {
        return paiementFactureService.savePaiementFactureWithoutFile(paiementFacture);
    }

    @PostMapping()
    public Map<String, Object> savePaiementFacture(@RequestParam("paiementFacture") String paiementFactureString,
                                          @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        PaiementFacture paiementFacture = new ObjectMapper().readValue(paiementFactureString, PaiementFacture.class);
        return paiementFactureService.savePaiementFacture(paiementFacture, photo);
    }

    @PutMapping()
    public Map<String, Object> updatePaiementFactureWithoutFile(@RequestBody PaiementFacture paiementFacture) {
        return paiementFactureService.updatePaiementFactureWithoutFile(paiementFacture);
    }

    @PutMapping("/file")
    public Map<String, Object> updatePaiementFactureWithFile(@RequestParam("paiementFacture") String paiementFactureString,
                                                    @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        PaiementFacture paiementFacture = new ObjectMapper().readValue(paiementFactureString, PaiementFacture.class);
        return paiementFactureService.updatePaiementFactureWithFile(paiementFacture, photo);
    }

    @GetMapping
    public Map<String, Object> getPaiementFactures() {
        return paiementFactureService.getPaiementFactures();
    }

    @PostMapping("/page")
    public Map<String, Object> getPaiementFacturesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return paiementFactureService.getPaiementFacturesByPage(page, size);
    }
    @PostMapping("/statut")
    public Map<String, Object> updateStatut(@RequestBody UpdateStatut updateStatut) {
        return paiementFactureService.updateStatut(updateStatut);
    }

    @ResponseBody
    @GetMapping("/download/{photo}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        String path = UploadPath.FACTURE_DOWNLOAD_LINK;
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
