package com.wassa.suguba.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Voyage;
import com.wassa.suguba.app.payload.AeroportPayload;
import com.wassa.suguba.app.payload.AreroprtFinal;
import com.wassa.suguba.app.payload.UpdateStatut;
import com.wassa.suguba.app.service.VoyageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/voyages")
public class VoyageController {
    private final VoyageService voyageService;

    public VoyageController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @PostMapping()
    public Map<String, Object> saveVoyage(@RequestParam("voyage") String voyageString,
                                             @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Voyage voyage = new ObjectMapper().readValue(voyageString, Voyage.class);
        return voyageService.saveVoyage(voyage, photo);
    }

    @PutMapping()
    public Map<String, Object> updateVoyageWithoutFile(@RequestBody Voyage voyage) {
        return voyageService.updateVoyageWithoutFile(voyage);
    }

    @PutMapping("/file")
    public Map<String, Object> updateVoyageWithFile(@RequestParam("voyage") String voyageString,
                                                       @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Voyage voyage = new ObjectMapper().readValue(voyageString, Voyage.class);
        return voyageService.updateVoyageWithFile(voyage, photo);
    }

    @GetMapping
    public Map<String, Object> getVoyages() {
        return voyageService.getVoyages();
    }

    @PostMapping("/page")
    public Map<String, Object> getVoyagesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return voyageService.getVoyagesByPage(page, size);
    }
    @PostMapping("/statut")
    public Map<String, Object> updateStatut(@RequestBody UpdateStatut updateStatut) {
        return voyageService.updateStatut(updateStatut);
    }

    @PostMapping("/aeroport")
    public Map<String, Object> saveAirport(@RequestBody List<AeroportPayload> areroprtFinals) {
        return voyageService.saveAeroport(areroprtFinals);
    }
    @PostMapping("/aeroport/liste")
    public Map<String, Object> getAllAirport(@RequestParam("page") int page, @RequestParam("size") int size) {
        return voyageService.getAirports(page, size);
    }

    @GetMapping("/aeroport/search/{term}")
    public Map<String, Object> searchAirport(
                                             @PathVariable String term) {
        return voyageService.searchAirports(term);
    }
    @ResponseBody
    @GetMapping("/download/{photo}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        String path = UploadPath.VOYAGE_DOWNLOAD_LINK;
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
