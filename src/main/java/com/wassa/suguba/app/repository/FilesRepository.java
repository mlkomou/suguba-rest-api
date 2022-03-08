package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<Files, Long> {
}
