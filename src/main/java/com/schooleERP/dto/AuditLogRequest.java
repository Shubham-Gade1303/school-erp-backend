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
public class AuditLogRequest {

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 50)
    private String action;

    @NotBlank
    @Size(max = 100)
    private String entityName;

    @NotNull
    private Long entityId;

    @Size(max = 1000)
    private String description;

    @Size(max = 100)
    private String ipAddress;
}
