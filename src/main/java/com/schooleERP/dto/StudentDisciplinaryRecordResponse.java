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
public class StudentDisciplinaryRecordResponse {

    private Long id;

    private Long studentId;

    private String admissionNumber;

    private String studentName;

    private LocalDate incidentDate;

    private String incidentType;

    private String description;

    private String actionTaken;

    private String remarks;

    private boolean resolved;

    private LocalDate recordedDate;
}
