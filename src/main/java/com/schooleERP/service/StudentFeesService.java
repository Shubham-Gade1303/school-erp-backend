package com.schooleERP.service;

import com.schooleERP.dto.StudentFeesRequest;
import com.schooleERP.dto.StudentFeesResponse;

import java.util.List;

public interface StudentFeesService {

    StudentFeesResponse createFee(StudentFeesRequest request);

    List<StudentFeesResponse> getAllFees();

    StudentFeesResponse getFeeById(Long id);

    List<StudentFeesResponse> getFeesByStudent(Long studentId);

    List<StudentFeesResponse> getFeesByAcademicYear(Long academicYearId);

    List<StudentFeesResponse> getFeesByStudentAndAcademicYear(
            Long studentId,
            Long academicYearId
    );

    List<StudentFeesResponse> getFeesByStatus(String status);

    List<StudentFeesResponse> getFeesByType(String feeType);

    StudentFeesResponse updateFee(
            Long id,
            StudentFeesRequest request
    );

    void deleteFee(Long id);
}