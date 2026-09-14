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
public class ExamResponse {

    private Long id;

    private String examName;

    private Long academicYearId;
    private String academicYear;

    private Long standardId;
    private String standardName;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean active;
}

