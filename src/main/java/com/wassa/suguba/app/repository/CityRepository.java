package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByNameEn(String name);
}
