package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
