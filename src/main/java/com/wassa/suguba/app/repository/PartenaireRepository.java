package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Partenaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartenaireRepository extends JpaRepository<Partenaire, Long> {
    Optional<Partenaire> findByCodePartenaire(String codePartenaire);
}
