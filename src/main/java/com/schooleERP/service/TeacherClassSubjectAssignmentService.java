package com.schooleERP.service;

import com.schooleERP.dto.TeacherClassSubjectAssignmentRequest;
import com.schooleERP.dto.TeacherClassSubjectAssignmentResponse;

import java.util.List;

public interface TeacherClassSubjectAssignmentService {

    TeacherClassSubjectAssignmentResponse createAssignment(
            TeacherClassSubjectAssignmentRequest request
    );

    List<TeacherClassSubjectAssignmentResponse> getAllAssignments();

    TeacherClassSubjectAssignmentResponse getAssignmentById(
            Long id
    );

    List<TeacherClassSubjectAssignmentResponse> getByTeacher(
            Long teacherId
    );

    List<TeacherClassSubjectAssignmentResponse> getByClassSection(
            Long classSectionId
    );

    List<TeacherClassSubjectAssignmentResponse> getByClassSubject(
            Long classSubjectId
    );

    List<TeacherClassSubjectAssignmentResponse>
    getByClassSectionAndClassSubject(
            Long classSectionId,
            Long classSubjectId
    );

    TeacherClassSubjectAssignmentResponse updateAssignment(
            Long id,
            TeacherClassSubjectAssignmentRequest request
    );

    void deleteAssignment(Long id);
}
