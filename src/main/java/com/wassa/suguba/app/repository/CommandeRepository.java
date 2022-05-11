package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    Commande getById(Long id);

    List<Commande> findAllById(Long id);
}
