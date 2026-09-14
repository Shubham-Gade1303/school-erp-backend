package com.schooleERP.service;

import com.schooleERP.dto.StudentMarksRequest;
import com.schooleERP.dto.StudentMarksResponse;

import java.util.List;

public interface StudentMarksService {

    StudentMarksResponse addMarks(StudentMarksRequest request);

    List<StudentMarksResponse> getAllMarks();

    StudentMarksResponse getMarksById(Long id);

    List<StudentMarksResponse> getMarksByStudent(Long studentId);

    List<StudentMarksResponse> getMarksByExam(Long examId);

    List<StudentMarksResponse> getMarksBySubject(Long subjectId);

    List<StudentMarksResponse> getMarksByStudentAndExam(Long studentId, Long examId);

    List<StudentMarksResponse> getMarksByExamAndSubject(Long examId, Long subjectId);

    StudentMarksResponse updateMarks(Long id, StudentMarksRequest request);

    void deleteMarks(Long id);
}
