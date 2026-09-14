package com.schooleERP.service;

import com.schooleERP.dto.ExamRequest;
import com.schooleERP.dto.ExamResponse;

import java.util.List;

public interface ExamService {

    ExamResponse createExam(ExamRequest request);

    List<ExamResponse> getAllExams();

    ExamResponse getExamById(Long id);

    List<ExamResponse> getExamsByAcademicYear(Long academicYearId);

    List<ExamResponse> getExamsByStandard(Long standardId);

    List<ExamResponse> getExamsByAcademicYearAndStandard(
            Long academicYearId,
            Long standardId
    );

    ExamResponse updateExam(Long id, ExamRequest request);

    void deleteExam(Long id);
}

