package com.wassa.suguba.smsSender;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsRepository extends JpaRepository<Sms, Long> {
    Optional<Sms> findByPhoneAndCodeConfirmation(String phone, String codeConfirmation);
    Optional<Sms> findByPhone(String phone);

}
