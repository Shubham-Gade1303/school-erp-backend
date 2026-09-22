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
public class AnnouncementResponse {

    private Long id;

    private String title;

    private String content;

    private String audience;

    private LocalDateTime publishedAt;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}