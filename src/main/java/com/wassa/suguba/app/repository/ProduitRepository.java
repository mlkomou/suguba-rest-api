package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Page<Produit> findAllByCategorieId(Long catId, Pageable pageable);
}
