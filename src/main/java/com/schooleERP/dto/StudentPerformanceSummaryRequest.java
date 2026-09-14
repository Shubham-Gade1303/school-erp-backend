package com.schooleERP.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentPerformanceSummaryRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private Long examId;
}
