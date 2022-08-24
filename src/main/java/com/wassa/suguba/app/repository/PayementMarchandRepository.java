package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.PayementMarchand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayementMarchandRepository extends JpaRepository<PayementMarchand, Long> {
    Optional<PayementMarchand> findByIdFromClient(String idFromClient);
}
