package com.schooleERP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAchievementRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private LocalDate achievementDate;

    @NotBlank
    @Size(max = 100)
    private String achievementType;

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @Size(max = 50)
    private String level;

    @Size(max = 50)
    private String position;

    @Size(max = 200)
    private String awardedBy;

    @Size(max = 500)
    private String certificateUrl;

    @Size(max = 1000)
    private String remarks;
}
