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
public class StudentPromotionHistoryResponse {

    private Long id;

    private Long studentId;
    private String studentName;

    private Long fromAcademicYearId;
    private String fromAcademicYear;

    private Long fromStandardId;
    private String fromStandard;

    private Long fromClassSectionId;
    private String fromSection;

    private Long toAcademicYearId;
    private String toAcademicYear;

    private Long toStandardId;
    private String toStandard;

    private Long toClassSectionId;
    private String toSection;

    private LocalDate promotionDate;

    private String status;

    private String remarks;
}
