package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
