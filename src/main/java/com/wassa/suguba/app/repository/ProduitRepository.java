package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findAllByCategorieId(Long catId);
}
