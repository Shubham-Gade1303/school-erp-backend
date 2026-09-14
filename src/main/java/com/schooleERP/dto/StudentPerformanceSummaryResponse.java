package com.schooleERP.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentPerformanceSummaryResponse {

    private Long id;
    private Long studentId;
    private String admissionNumber;
    private String studentName;
    private Long examId;
    private String examName;
    private Double totalMarks;
    private Double marksObtained;
    private Double percentage;
    private String grade;
    private String resultStatus;


}
