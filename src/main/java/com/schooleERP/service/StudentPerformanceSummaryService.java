package com.schooleERP.service;

import com.schooleERP.dto.StudentPerformanceSummaryRequest;
import com.schooleERP.dto.StudentPerformanceSummaryResponse;

import java.util.List;

public interface StudentPerformanceSummaryService {

    StudentPerformanceSummaryResponse generateSummary(StudentPerformanceSummaryRequest request
    );

    List<StudentPerformanceSummaryResponse> getAllSummaries();

    StudentPerformanceSummaryResponse getSummaryById(Long id);

    StudentPerformanceSummaryResponse getSummaryByStudentAndExam(Long studentId, Long examId);

    List<StudentPerformanceSummaryResponse> getSummariesByStudent(Long studentId);

    List<StudentPerformanceSummaryResponse> getSummariesByExam(Long examId);

    StudentPerformanceSummaryResponse updateSummary(Long id, StudentPerformanceSummaryRequest request);

    void deleteSummary(Long id);
}

