package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Page<Produit> findAllByCategorieId(Long catId, Pageable pageable);

//    @Query("select prod from Produit prod where prod.description like  %:searchTerm%")
//    Page<Produit> deepSearchDescription(@Param("searchTerm") String searchTerm, Pageable pageable);
//
//    @Query("select prod from Produit prod where prod.name like  %:searchTerm%")
//    Page<Produit> deepSearchName(@Param("searchTerm") String searchTerm, Pageable pageable);
//
//    @Query("select prod from Produit prod where prod.categorie.name like  %:searchTerm%")
//    Page<Produit> deepSearchCategorieName(@Param("searchTerm") String searchTerm, Pageable pageable);
//
//    @Query("select prod from Produit prod where CONVERT(prod.prix,char) like  %:searchTerm%")
//    Page<Produit> deepSearchCategorieName(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("select prod from Produit prod where prod.name like  %:searchTerm%")
    Page<Produit> deepSearchName(String searchTerm, Pageable pageable);

    @Query("select prod from Produit prod where CONCAT(prod.prix, '') like  %:searchTerm%")
    Page<Produit> deepSearchPrix(String searchTerm, Pageable pageable);

    @Query("select prod from Produit prod where prod.categorie.name like  %:searchTerm%")
    Page<Produit> deepSearchCategorieName(String searchTerm, Pageable pageable);

    @Query("select prod from Produit prod where prod.description like  %:searchTerm%")
    Page<Produit> deepSearchDescription(String searchTerm, Pageable pageable);

    @Query("select prod from Produit prod where prod.reference like  %:searchTerm%")
    Page<Produit> deepSearchReference(String searchTerm, Pageable pageable);
}
