package com.schooleERP.service;

import com.schooleERP.dto.ClassSubjectRequest;
import com.schooleERP.dto.ClassSubjectResponse;

import java.util.List;

public interface ClassSubjectService {

    ClassSubjectResponse createClassSubject(ClassSubjectRequest request);

    List<ClassSubjectResponse> getAllClassSubjects();

    ClassSubjectResponse getClassSubjectById(Long id);

    List<ClassSubjectResponse> getByAcademicYear(Long academicYearId);

    List<ClassSubjectResponse> getByStandard(Long standardId);

    List<ClassSubjectResponse> getBySubject(Long subjectId);

    List<ClassSubjectResponse> getByAcademicYearAndStandard(
            Long academicYearId,
            Long standardId
    );

    ClassSubjectResponse updateClassSubject(
            Long id,
            ClassSubjectRequest request
    );

    void deleteClassSubject(Long id);
}

