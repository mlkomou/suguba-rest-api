package com.wassa.suguba.app.repository;

import com.wassa.suguba.app.entity.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
