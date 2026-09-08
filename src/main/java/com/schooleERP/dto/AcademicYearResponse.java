
package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicYearResponse {

    private Long id;

    private String academicYear;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean active;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}

