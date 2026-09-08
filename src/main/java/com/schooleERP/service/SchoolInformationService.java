package com.schooleERP.service;

import com.schooleERP.dto.SchoolInformationRequest;
import com.schooleERP.dto.SchoolInformationResponse;
import com.schooleERP.entity.SchoolInformation;

import java.util.List;

public interface SchoolInformationService {


    SchoolInformationResponse createSchool(SchoolInformationRequest request);

    List<SchoolInformationResponse> getAllSchools();

    SchoolInformationResponse getSchoolById(Long id);

    SchoolInformationResponse updateSchool(Long id, SchoolInformationRequest request);

    void deleteSchool(Long id);


}
