package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    Optional<Fournisseur> findByCodeFournisseur(String codeFournisseur);
}
