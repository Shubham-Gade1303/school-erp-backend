package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentMarksResponse {

    private Long id;

    private Long studentId;
    private String admissionNumber;
    private String studentName;

    private Long examId;
    private String examName;

    private Long subjectId;
    private String subjectName;
    private String subjectCode;

    private Double marksObtained;
    private Double maximumMarks;

    private String remarks;
}

