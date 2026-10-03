package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;

    private Long userId;

    private String title;

    private String message;

    private String notificationType;

    private boolean read;

    private LocalDateTime createdAt;

    private LocalDateTime readAt;
}
