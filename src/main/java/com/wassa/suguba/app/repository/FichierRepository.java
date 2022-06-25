package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichierRepository extends JpaRepository<Fichier, Long> {
}
