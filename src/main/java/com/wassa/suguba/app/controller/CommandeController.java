package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Commande;
import com.wassa.suguba.app.payload.CommandePDFExporter;
import com.wassa.suguba.app.payload.CommandePayload;
import com.wassa.suguba.app.payload.IntervalleDate;
import com.wassa.suguba.app.payload.UpdateStatut;
import com.wassa.suguba.app.service.CommandeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/commandes")
public class CommandeController {
    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> saveCommande(@RequestBody CommandePayload commande) {
        return commandeService.saveCommande(commande);
    }

    @PostMapping("/statut")
    public Map<String, Object> updateStatut(@RequestBody UpdateStatut updateStatut) {
        return commandeService.updateStatut(updateStatut);
    }

    @PutMapping()
    public Map<String, Object> updateCommandeWithoutFile(@RequestBody Commande commande) {
        return commandeService.updateCommandeWithoutFile(commande);
    }


    @GetMapping
    public Map<String, Object> getCommandes() {
        return commandeService.getCommandes();
    }

    @GetMapping("/id/{id}")
    public Map<String, Object> getCommandeById(@PathVariable Long id) {
        return commandeService.getCommandeById(id);
    }

    @PostMapping("/page")
    public Map<String, Object> getCommandesByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return commandeService.getCommandesByPage(page, size);
    }

    @PostMapping("/dateRange")
    public Map<String, Object> getByIntervall(@RequestBody IntervalleDate intervalleDate) {
        return commandeService.getByIntervalleDate(intervalleDate);
    }

    @PostMapping("/dateRangeAdmin")
    public Map<String, Object> getByIntervallAdmin(@RequestBody IntervalleDate intervalleDate) {
        return commandeService.getByIntervalleDateAdmin(intervalleDate);
    }

    @PostMapping("/export/pdf")
    public Map<String, Object> exportToPDF(@RequestBody IntervalleDate intervalleDate, HttpServletResponse response) {
       return commandeService.exportToPDF(intervalleDate, response);
    }
//    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
//        response.setContentType("application/pdf");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
//        response.setHeader(headerKey, headerValue);
//
//        List<Commande> commandeList = commandeService.getAllCommande();
//
//        CommandePDFExporter exporter = new CommandePDFExporter(commandeList);
//        exporter.export(response);
//
//    }

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
