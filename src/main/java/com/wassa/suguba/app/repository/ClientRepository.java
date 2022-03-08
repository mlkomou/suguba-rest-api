package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
