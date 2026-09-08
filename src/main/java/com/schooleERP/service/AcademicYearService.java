package com.schooleERP.service;

import com.schooleERP.dto.AcademicYearRequest;
import com.schooleERP.dto.AcademicYearResponse;

import java.util.List;

public interface AcademicYearService {

    AcademicYearResponse createAcademicYear(
            AcademicYearRequest request
    );

    List<AcademicYearResponse> getAllAcademicYears();

    AcademicYearResponse getAcademicYearById(Long id);

    AcademicYearResponse updateAcademicYear(
            Long id,
            AcademicYearRequest request
    );

    void deleteAcademicYear(Long id);

    AcademicYearResponse getActiveAcademicYear();
}

