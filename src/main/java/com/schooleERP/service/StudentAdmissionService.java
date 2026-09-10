package com.schooleERP.service;

import com.schooleERP.dto.StudentAdmissionRequest;
import com.schooleERP.dto.StudentAdmissionResponse;

import java.util.List;

public interface StudentAdmissionService {

    StudentAdmissionResponse createAdmission(
            StudentAdmissionRequest request
    );

    List<StudentAdmissionResponse> getAllAdmissions();

    StudentAdmissionResponse getAdmissionById(Long id);

    StudentAdmissionResponse getAdmissionByStudentId(Long studentId);

    List<StudentAdmissionResponse> getAdmissionsByAcademicYear(
            Long academicYearId
    );

    List<StudentAdmissionResponse> getAdmissionsByStandard(
            Long standardId
    );

    List<StudentAdmissionResponse> getAdmissionsByClassSection(
            Long classSectionId
    );

    List<StudentAdmissionResponse> getAdmissionsByStatus(
            String admissionStatus
    );

    StudentAdmissionResponse updateAdmission(
            Long id,
            StudentAdmissionRequest request
    );

    void deleteAdmission(Long id);
}

