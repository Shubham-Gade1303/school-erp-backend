package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentFeesResponse {

    private Long id;

    private Long studentId;
    private String admissionNumber;
    private String studentName;

    private Long academicYearId;
    private String academicYear;

    private String feeType;
    private BigDecimal amount;

    private LocalDate dueDate;

    private String status;
    private String remarks;
}