package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Souscrition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SouscritionRepository extends JpaRepository<Souscrition, Long> {
    Page<Souscrition> findAllByActive(Boolean active, Pageable pageable);
    Souscrition findByUserIdAndActive(Long user_id, Boolean active);
}
