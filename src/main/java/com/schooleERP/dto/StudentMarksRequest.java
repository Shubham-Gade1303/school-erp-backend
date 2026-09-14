package com.schooleERP.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentMarksRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private long examId;

    @NotNull
    private Long subjectId;

    @NotNull
    @DecimalMin(value = "0.0",inclusive = true)
    private Double marksObtained;


    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Double maximumMarks;


    @Size(max = 25)
    private String remarks;


}
