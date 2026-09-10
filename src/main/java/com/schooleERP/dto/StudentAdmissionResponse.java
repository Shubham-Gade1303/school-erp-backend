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
public class StudentAdmissionResponse {

    private Long id;

    private Long studentId;
    private String admissionNumber;
    private String studentName;

    private Long academicYearId;
    private String academicYear;

    private Long standardId;
    private String standardName;

    private Long classSectionId;
    private String sectionName;

    private LocalDate admissionDate;

    private String admissionStatus;

    private String remarks;
}

