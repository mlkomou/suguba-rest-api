package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Categorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Page<Categorie> findAllByActive(Boolean active, Pageable pageable);
    List<Categorie> findAllByActive(Boolean active);
}
