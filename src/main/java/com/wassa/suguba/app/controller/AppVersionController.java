package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.service.AppVersionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/app-versions")
public class AppVersionController {
    private final AppVersionService appVersionService;

    public AppVersionController(AppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    @GetMapping
    public Map<String, Object> getAppVersion() {
        return appVersionService.getAppVersion();
    }
}
