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
public class ClassSectionResponse {

    private Long id;

    private Long academicYearId;

    private String academicYear;

    private Long standardId;

    private String standardName;

    private String sectionName;

    private boolean active;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}

