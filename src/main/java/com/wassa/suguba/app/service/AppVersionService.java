package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.AppVersion;
import com.wassa.suguba.app.repository.AppVersionRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppVersionService {
    private final AppVersionRepository appVersionRepository;

    public AppVersionService(AppVersionRepository appVersionRepository) {
        this.appVersionRepository = appVersionRepository;
    }

    public Map<String, Object> getAppVersion() {
        try {
            List<AppVersion> appVersions = appVersionRepository.findAll();
            if (!appVersions.isEmpty()) {
                return Response.success(appVersions.get(0), "Version actuelle");
            } else {
                return Response.error(null, "Il n'y a pas de version disponible");
            }
        } catch (Exception e) {
            return Response.error(e, "Erreur de récupération de la version");
        }
    }
}
