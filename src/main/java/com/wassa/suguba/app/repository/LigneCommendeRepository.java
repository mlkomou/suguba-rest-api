package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommendeRepository extends JpaRepository<LigneCommande, Long> {
    List<LigneCommande>findAllByCommandeId(Long commandeId);
}
