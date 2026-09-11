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
public class StudentAttendanceResponse {

    private Long id;

    private Long studentId;
    private String admissionNumber;
    private String studentName;

    private LocalDate attendanceDate;
    private String status;
    private String remarks;
}

