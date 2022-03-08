package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.entity.Files;
import com.wassa.suguba.app.service.FilesService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/filess")
public class FilesController {
    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }


    @PostMapping()
    public Map<String, Object> saveFiles(@RequestBody Files files) {
        return filesService.saveFiles(files);
    }

    @PutMapping()
    public Map<String, Object> updateFilesWithoutFile(@RequestBody Files files) {
        return filesService.updateFilesWithoutFile(files);
    }


    @GetMapping
    public Map<String, Object> getFiless() {
        return filesService.getFiless();
    }

    @GetMapping("/page")
    public Map<String, Object> getFilessByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return filesService.getFilessByPage(page, size);
    }

}
