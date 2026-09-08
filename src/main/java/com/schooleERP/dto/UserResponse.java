package com.schooleERP.dto;
import com.schooleERP.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private RoleName role;
    private boolean enabled;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}