package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByNameEn(String name);
}
