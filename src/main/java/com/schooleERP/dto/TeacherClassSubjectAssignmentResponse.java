package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherClassSubjectAssignmentResponse {

    private Long id;

    private Long teacherId;
    private String teacherName;
    private String employeeCode;

    private Long classSectionId;
    private String sectionName;

    private Long classSubjectId;
    private String subjectName;
    private String subjectCode;

    private String standardName;
    private String academicYear;

    private boolean active;
}
