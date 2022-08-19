package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.DemandeSouscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DemandeSouscriptionRepository extends JpaRepository<DemandeSouscription, Long> {
    Optional<DemandeSouscription> findByUserIdAndStatut(Long userId, String status);
    Optional<DemandeSouscription> findByIdAndStatut(Long id, String status);
    Page<DemandeSouscription> findAllByStatutAndActive(String statut, boolean active, Pageable pageable);
}
