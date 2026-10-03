package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.NotificationRequest;
import com.schooleERP.dto.NotificationResponse;
import com.schooleERP.entity.Notification;
import com.schooleERP.entity.User;
import com.schooleERP.repository.NotificationRepo;
import com.schooleERP.repository.UserRepo;
import com.schooleERP.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;
    private final UserRepo userRepo;

    public NotificationServiceImpl(
            NotificationRepo notificationRepo,
            UserRepo userRepo) {
        this.notificationRepo = notificationRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public NotificationResponse createNotification(NotificationRequest request) {

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + request.getUserId()));

        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setNotificationType(request.getNotificationType());
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setReadAt(null);

        Notification savedNotification =
                notificationRepo.save(notification);

        return mapToResponse(savedNotification);
    }

    @Override
    public List<NotificationResponse> getAllNotifications() {

        return notificationRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public NotificationResponse getNotificationById(Long id) {

        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found with id: " + id));

        return mapToResponse(notification);
    }

    @Override
    public List<NotificationResponse> getNotificationsByUser(Long userId) {

        if (!userRepo.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return notificationRepo.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<NotificationResponse> getUnreadNotificationsByUser(Long userId) {

        if (!userRepo.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return notificationRepo.findByUserIdAndRead(userId, false)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<NotificationResponse> getNotificationsByType(String notificationType) {

        return notificationRepo.findByNotificationType(notificationType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public NotificationResponse markAsRead(Long id) {

        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found with id: " + id));

        notification.setRead(true);
        notification.setReadAt(LocalDateTime.now());

        Notification updatedNotification =
                notificationRepo.save(notification);

        return mapToResponse(updatedNotification);
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {

        if (!notificationRepo.existsById(id)) {
            throw new RuntimeException("Notification not found with id: " + id);
        }

        notificationRepo.deleteById(id);
    }

    private NotificationResponse mapToResponse(Notification notification) {

        return new NotificationResponse(
                notification.getId(),
                notification.getUser().getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getNotificationType(),
                notification.isRead(),
                notification.getCreatedAt(),
                notification.getReadAt()
        );
    }
}
