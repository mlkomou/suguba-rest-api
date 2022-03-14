package com.wassa.suguba.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Banque;
import com.wassa.suguba.app.service.BanqueService;
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
@RequestMapping("/banques")
public class BanqueController {
    private final BanqueService banqueService;

    public BanqueController(BanqueService banqueService) {
        this.banqueService = banqueService;
    }

    @PostMapping()
    public Map<String, Object> saveBanque(@RequestParam("banque") String banqueString,
                                             @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Banque banque = new ObjectMapper().readValue(banqueString, Banque.class);
        return banqueService.saveBanque(banque, photo);
    }

    @PutMapping()
    public Map<String, Object> updateBanqueWithoutFile(@RequestBody Banque banque) {
        return banqueService.updateBanqueWithoutFile(banque);
    }

    @PutMapping("/file")
    public Map<String, Object> updateBanqueWithFile(@RequestParam("banque") String banqueString,
                                                       @RequestParam("photo") MultipartFile photo) throws JsonProcessingException {
        Banque banque = new ObjectMapper().readValue(banqueString, Banque.class);
        return banqueService.updateBanqueWithFile(banque, photo);
    }

    @GetMapping
    public Map<String, Object> getBanques() {
        return banqueService.getBanques();
    }

    @GetMapping("/page")
    public Map<String, Object> getBanquesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return banqueService.getBanquesByPage(page, size);
    }

    @ResponseBody
    @GetMapping("/download/{photo}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        String path = UploadPath.BANQUE_DOWNLOAD_LINK;
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
