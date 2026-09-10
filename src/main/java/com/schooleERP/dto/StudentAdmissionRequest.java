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
public class StudentAdmissionRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Academic year ID is required")
    private Long academicYearId;

    @NotNull(message = "Standard ID is required")
    private Long standardId;

    @NotNull(message = "Class section ID is required")
    private Long classSectionId;

    @NotNull(message = "Admission date is required")
    private LocalDate admissionDate;

    @NotBlank(message = "Admission status is required")
    @Size(max = 20, message = "Admission status must not exceed 20 characters")
    private String admissionStatus;

    @Size(max = 500, message = "Remarks must not exceed 500 characters")
    private String remarks;
}

