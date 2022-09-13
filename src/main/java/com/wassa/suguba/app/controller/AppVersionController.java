package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.entity.AppVersion;
import com.wassa.suguba.app.service.AppVersionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app-versions")
public class AppVersionController {
    private final AppVersionService appVersionService;

    public AppVersionController(AppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    @GetMapping
    public Map<String, Object> getAppVersion(@RequestBody AppVersion appVersion) {
        return appVersionService.getAppVersion(appVersion);
    }
}
