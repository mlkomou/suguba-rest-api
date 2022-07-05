package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    Commande getById(Long id);

    List<Commande> findAllById(Long id);

    @Query(value = "from Commande c where c.createdAt BETWEEN :startDate AND :endDate AND c.user.partenaire.id = :partenaireId")
//    @Query("select c from Commande c where c.createdAt >= ?1 and c.createdAt <= ?2 and c.user.partenaire.id = ?3")
    Page<Commande> getByRangeDate(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate, @Param("partenaireId") Long partenaireId, Pageable pageable);
}
