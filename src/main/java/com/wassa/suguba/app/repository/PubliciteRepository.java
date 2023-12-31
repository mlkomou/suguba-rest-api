package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Publicite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubliciteRepository extends JpaRepository<Publicite, Long> {
    Page<Publicite> findAllByActive(Boolean active, Pageable pageable);
}
