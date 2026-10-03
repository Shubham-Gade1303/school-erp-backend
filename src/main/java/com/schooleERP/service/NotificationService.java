package com.schooleERP.service;

import com.schooleERP.dto.NotificationRequest;
import com.schooleERP.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

    NotificationResponse createNotification(NotificationRequest request);

    List<NotificationResponse> getAllNotifications();

    NotificationResponse getNotificationById(Long id);

    List<NotificationResponse> getNotificationsByUser(Long userId);

    List<NotificationResponse> getUnreadNotificationsByUser(Long userId);

    List<NotificationResponse> getNotificationsByType(String notificationType);

    NotificationResponse markAsRead(Long id);

    void deleteNotification(Long id);
}
