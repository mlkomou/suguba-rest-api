package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.AppVersion;
import com.wassa.suguba.app.repository.AppVersionRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AppVersionService {
    private final AppVersionRepository appVersionRepository;

    public AppVersionService(AppVersionRepository appVersionRepository) {
        this.appVersionRepository = appVersionRepository;
    }

    public Map<String, Object> getAppVersion(AppVersion appVersion) {
        try {
            Optional<AppVersion> appVersionOptional = appVersionRepository.findByVersion(appVersion.getVersion());
            return appVersionOptional.map(version -> Response.success(version, "App version")).orElseGet(() -> Response.error(new AppVersion(), "Cette version n'existe pas"));
        } catch (Exception e) {
            return Response.error(e, "Erreur de récupération de la version");
        }
    }
}
