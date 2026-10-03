package com.schooleERP.repository;

import com.schooleERP.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);

    List<Notification> findByUserIdAndRead(Long userId, boolean read);

    List<Notification> findByNotificationType(String notificationType);
}