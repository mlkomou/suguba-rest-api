package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.DemandeSouscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DemandeSouscriptionRepository extends JpaRepository<DemandeSouscription, Long> {
    Optional<DemandeSouscription> findByUserIdAndStatut(Long userId, String status);
    Optional<DemandeSouscription> findByIdAndStatut(Long id, String status);
    Page<DemandeSouscription> findAllByStatutAndStatutBanqueAndActive(String statut, String statutBanque, boolean active, Pageable pageable);
    Page<DemandeSouscription> findAllByStatutAndStatutBanqueAndPartenaireIdAndActive(String statut, String statutBanque, Long partenaireId, boolean active, Pageable pageable);
}
