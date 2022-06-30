package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.PhoneVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneVerificationRepository extends JpaRepository<PhoneVerification, Long> {
    Optional<PhoneVerification> findByPhone(String phone);
    Optional<PhoneVerification> findByPhoneAndVerificationCode(String phone, String verificationCode);
}
