package com.wassa.suguba.authentification.repo;

import com.wassa.suguba.authentification.entity.ApplicationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);
    Page<ApplicationUser> findAll(Pageable pageable);
    List<ApplicationUser> findAllByType(String type);
    Optional<ApplicationUser> getApplicationUserByOneSignalUserId(String userId);
}
