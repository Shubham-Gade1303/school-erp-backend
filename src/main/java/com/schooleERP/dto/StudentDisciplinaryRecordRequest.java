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
public class StudentDisciplinaryRecordRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Incident date is required")
    private LocalDate incidentDate;

    @NotBlank(message = "Incident type is required")
    @Size(max = 100, message = "Incident type must not exceed 100 characters")
    private String incidentType;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Size(max = 500, message = "Action taken must not exceed 500 characters")
    private String actionTaken;

    @Size(max = 1000, message = "Remarks must not exceed 1000 characters")
    private String remarks;

    private boolean resolved;
}

