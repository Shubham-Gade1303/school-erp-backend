package com.schooleERP.dto;

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
public class StudentActivitiesParticipationRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private Long activityId;

    @NotNull
    private LocalDate participationDate;

    @Size(max = 100)
    private String role;

    @Size(max = 100)
    private String result;

    @Size(max = 50)
    private String position;

    @Size(max = 1000)
    private String remarks;
}
