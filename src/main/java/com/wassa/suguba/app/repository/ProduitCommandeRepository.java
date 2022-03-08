package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.ProduitCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitCommandeRepository extends JpaRepository<ProduitCommande, Long> {
}
