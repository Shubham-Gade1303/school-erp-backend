package com.schooleERP.service;

import com.schooleERP.dto.SubjectRequest;
import com.schooleERP.dto.SubjectResponse;

import java.util.List;

public interface SubjectService {

    SubjectResponse createSubject(SubjectRequest request);

    List<SubjectResponse> getAllSubjects();

    SubjectResponse getSubjectById(Long id);

    SubjectResponse updateSubject(Long id, SubjectRequest request);

    void deleteSubject(Long id);
}

