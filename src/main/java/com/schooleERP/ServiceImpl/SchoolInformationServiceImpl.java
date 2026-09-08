package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.SchoolInformationRequest;
import com.schooleERP.dto.SchoolInformationResponse;
import com.schooleERP.entity.SchoolInformation;
import com.schooleERP.repository.SchoolInformationRepo;
import com.schooleERP.service.SchoolInformationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolInformationServiceImpl
        implements SchoolInformationService {

    private final SchoolInformationRepo schoolInformationRepo;

    public SchoolInformationServiceImpl(
            SchoolInformationRepo schoolInformationRepo) {

        this.schoolInformationRepo = schoolInformationRepo;
    }

    @Override
    public SchoolInformationResponse createSchool(
            SchoolInformationRequest request) {

        SchoolInformation school = new SchoolInformation();

        mapRequestToEntity(request, school);

        SchoolInformation savedSchool =
                schoolInformationRepo.save(school);

        return mapEntityToResponse(savedSchool);
    }

    @Override
    public List<SchoolInformationResponse> getAllSchools() {

        return schoolInformationRepo.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    @Override
    public SchoolInformationResponse getSchoolById(Long id) {

        SchoolInformation school =
                schoolInformationRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "School information not found with id: " + id
                                ));

        return mapEntityToResponse(school);
    }

    @Override
    public SchoolInformationResponse updateSchool(
            Long id,
            SchoolInformationRequest request) {

        SchoolInformation school =
                schoolInformationRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "School information not found with id: " + id
                                ));

        mapRequestToEntity(request, school);

        SchoolInformation updatedSchool =
                schoolInformationRepo.save(school);

        return mapEntityToResponse(updatedSchool);
    }

    @Override
    public void deleteSchool(Long id) {

        if (!schoolInformationRepo.existsById(id)) {

            throw new IllegalArgumentException(
                    "School information not found with id: " + id
            );
        }

        schoolInformationRepo.deleteById(id);
    }

    private void mapRequestToEntity(
            SchoolInformationRequest request,
            SchoolInformation school) {

        school.setSchoolName(request.getSchoolName());
        school.setSchoolCode(request.getSchoolCode());
        school.setEmail(request.getEmail());
        school.setPhone(request.getPhone());
        school.setAddress(request.getAddress());
        school.setCity(request.getCity());
        school.setState(request.getState());
        school.setPincode(request.getPincode());
        school.setWebsite(request.getWebsite());
        school.setPrincipalName(request.getPrincipalName());
        school.setEstablishedDate(request.getEstablishedDate());
    }

    private SchoolInformationResponse mapEntityToResponse(
            SchoolInformation school) {

        return new SchoolInformationResponse(
                school.getId(),
                school.getSchoolName(),
                school.getSchoolCode(),
                school.getEmail(),
                school.getPhone(),
                school.getAddress(),
                school.getCity(),
                school.getState(),
                school.getPincode(),
                school.getWebsite(),
                school.getPrincipalName(),
                school.getEstablishedDate(),
                school.getCreatedAt(),
                school.getUpdatedAt()
        );
    }
}

