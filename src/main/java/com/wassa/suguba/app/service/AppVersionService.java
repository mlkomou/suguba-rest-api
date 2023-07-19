package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.AppVersion;
import com.wassa.suguba.app.entity.AppVersionIos;
import com.wassa.suguba.app.repository.AppVersionIosRepository;
import com.wassa.suguba.app.repository.AppVersionRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppVersionService {
    private final AppVersionRepository appVersionRepository;
    private final AppVersionIosRepository appVersionIosRepository;

    public AppVersionService(AppVersionRepository appVersionRepository, AppVersionIosRepository appVersionIosRepository) {
        this.appVersionRepository = appVersionRepository;
        this.appVersionIosRepository = appVersionIosRepository;
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
    public Map<String, Object> getAppVersionIos() {
        try {
            List<AppVersionIos> appVersions = appVersionIosRepository.findAll();
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
