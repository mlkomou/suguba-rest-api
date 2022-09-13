package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
    Optional<AppVersion> findByVersion(String version);
}
