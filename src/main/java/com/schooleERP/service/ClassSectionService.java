package com.schooleERP.service;

import com.schooleERP.dto.ClassSectionRequest;
import com.schooleERP.dto.ClassSectionResponse;

import java.util.List;

public interface ClassSectionService {

    ClassSectionResponse createClassSection(
            ClassSectionRequest request
    );

    List<ClassSectionResponse> getAllClassSections();

    List<ClassSectionResponse> getClassSectionsByAcademicYear(
            Long academicYearId
    );

    List<ClassSectionResponse> getClassSectionsByStandard(
            Long standardId
    );

    List<ClassSectionResponse> getClassSectionsByAcademicYearAndStandard(
            Long academicYearId,
            Long standardId
    );

    ClassSectionResponse getClassSectionById(Long id);

    ClassSectionResponse updateClassSection(
            Long id,
            ClassSectionRequest request
    );

    void deleteClassSection(Long id);
}
