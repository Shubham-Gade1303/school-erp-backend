    package com.schooleERP.service;

import com.schooleERP.dto.StudentRequest;
import com.schooleERP.dto.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest request);

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(Long id);

    StudentResponse getStudentByAdmissionNumber(String admissionNumber);

    List<StudentResponse> getStudentsByClassSection(Long classSectionId);

    List<StudentResponse> getActiveStudents();

    StudentResponse updateStudent(Long id, StudentRequest request);

    void deleteStudent(Long id);
}

