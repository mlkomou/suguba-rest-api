package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Aeroport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AeroportRepository extends JpaRepository<Aeroport, Long> {
    Optional<Aeroport> findByNameEn(String name);

    @Query("select a from Aeroport a where a.nameFr like %:searchTerm% or a.city.nameFr like %:searchTerm% or a.city.country.nameFr like %:searchTerm%")
    List<Aeroport> realtimeSearch(@Param("searchTerm") String searchTerm);
}
