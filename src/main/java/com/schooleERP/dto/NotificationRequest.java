package com.schooleERP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String message;

    @NotBlank
    @Size(max = 50)
    private String notificationType;
}
