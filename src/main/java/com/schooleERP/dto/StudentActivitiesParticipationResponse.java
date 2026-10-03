
        package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentActivitiesParticipationResponse {

    private Long id;

    private Long studentId;

    private String studentName;

    private Long activityId;

    private String activityName;

    private LocalDate participationDate;

    private String role;

    private String result;

    private String position;

    private String remarks;
}
