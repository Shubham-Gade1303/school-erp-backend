package com.schooleERP.dto;

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
public class StudentPromotionHistoryRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private Long fromAcademicYearId;

    @NotNull
    private Long fromStandardId;

    @NotNull
    private Long fromClassSectionId;

    @NotNull
    private Long toAcademicYearId;

    @NotNull
    private Long toStandardId;

    @NotNull
    private Long toClassSectionId;

    @NotNull
    @Size(max = 30)
    private String status;

    @Size(max = 1000)
    private String remarks;
}
