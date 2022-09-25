package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.constante.UploadPath;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class DownloadController {
    @ResponseBody
    @GetMapping("/download/{file}/{typeFile}")
    public ResponseEntity<ByteArrayResource> getFiles(@PathVariable("file") String file, @PathVariable("typeFile") String typeFile) {
        String path = "";
        switch (typeFile) {
            case "identite":
                path = UploadPath.PIECE_DOWNLOAD_LINK;
                break;
            case "signature":
                path = UploadPath.SIGNATURE_DOWNLOAD_LINK;
                break;
            case "pub":
                path = UploadPath.PUBLICITE_DOWNLOAD_LINK;
                break;
        }
        try {
            Path fileName = Paths.get(path, file);
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
