package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Compteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompteurRepository extends JpaRepository<Compteur, Long> {
    @Query("select Max(c.nombre) from Compteur c")
    Integer findMaxNumber();
}
