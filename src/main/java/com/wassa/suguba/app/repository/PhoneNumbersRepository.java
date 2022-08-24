package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.PhoneNumbers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumbersRepository extends JpaRepository<PhoneNumbers, Long> {
}
