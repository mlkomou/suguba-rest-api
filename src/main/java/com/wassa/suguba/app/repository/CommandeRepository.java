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

    Page<Commande> findAllByUserId(Long user_id, Pageable pageable);
    List<Commande> getAllByUserId(Long user_id);

    @Query(value = "from Commande c where c.createdAt BETWEEN :startDate AND :endDate AND c.user.servicePaiement.id = :partenaireId")
    Page<Commande> getByRangeDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("partenaireId") Long partenaireId, Pageable pageable);
    @Query(value = "from Commande c where c.createdAt BETWEEN :startDate AND :endDate")
    Page<Commande> getByRangeDateAdmin(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    //all liste with paging
    @Query(value = "from Commande c where c.createdAt BETWEEN :startDate AND :endDate AND c.user.servicePaiement.id = :partenaireId")
    List<Commande> getByRangeDateWithoutPage(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("partenaireId") Long partenaireId);
    @Query(value = "from Commande c where c.createdAt BETWEEN :startDate AND :endDate")
    List<Commande> getByRangeDateAdminWithoutPage(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
