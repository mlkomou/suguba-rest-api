package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Aeroport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AeroportRepository extends JpaRepository<Aeroport, Long> {
    Optional<Aeroport> findByNameEn(String name);
}
