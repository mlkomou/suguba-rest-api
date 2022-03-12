package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Immobilier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImmobilierRepository extends JpaRepository<Immobilier, Long> {
}
