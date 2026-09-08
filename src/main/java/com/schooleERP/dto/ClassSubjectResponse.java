package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubjectResponse {

    private Long id;

    private Long academicYearId;
    private String academicYear;

    private Long standardId;
    private String standardName;

    private Long subjectId;
    private String subjectName;
    private String subjectCode;

    private boolean active;
}

