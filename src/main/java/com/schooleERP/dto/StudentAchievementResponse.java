
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
public class StudentAchievementResponse {

    private Long id;

    private Long studentId;

    private String studentName;

    private LocalDate achievementDate;

    private String achievementType;

    private String title;

    private String description;

    private String level;

    private String position;

    private String awardedBy;

    private String certificateUrl;

    private String remarks;

    private LocalDate recordedDate;
}
