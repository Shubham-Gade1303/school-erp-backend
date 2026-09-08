package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.ClassSectionRequest;
import com.schooleERP.dto.ClassSectionResponse;
import com.schooleERP.entity.AcademicYear;
import com.schooleERP.entity.ClassSection;
import com.schooleERP.entity.Standard;
import com.schooleERP.repository.AcademicYearRepo;
import com.schooleERP.repository.ClassSectionRepo;
import com.schooleERP.repository.StandardRepo;
import com.schooleERP.service.ClassSectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassSectionServiceImpl
        implements ClassSectionService {

    private final ClassSectionRepo classSectionRepo;
    private final AcademicYearRepo academicYearRepo;
    private final StandardRepo standardRepo;

    public ClassSectionServiceImpl(
            ClassSectionRepo classSectionRepo,
            AcademicYearRepo academicYearRepo,
            StandardRepo standardRepo) {

        this.classSectionRepo = classSectionRepo;
        this.academicYearRepo = academicYearRepo;
        this.standardRepo = standardRepo;
    }

    @Override
    public ClassSectionResponse createClassSection(
            ClassSectionRequest request) {

        AcademicYear academicYear =
                getAcademicYear(request.getAcademicYearId());

        Standard standard =
                getStandard(request.getStandardId());

        validateDuplicate(
                request.getAcademicYearId(),
                request.getStandardId(),
                request.getSectionName()
        );

        ClassSection classSection = new ClassSection();

        classSection.setAcademicYear(academicYear);
        classSection.setStandard(standard);
        classSection.setSectionName(
                request.getSectionName()
        );
        classSection.setActive(
                request.isActive()
        );

        ClassSection savedClassSection =
                classSectionRepo.save(classSection);

        return mapEntityToResponse(savedClassSection);
    }

    @Override
    public List<ClassSectionResponse> getAllClassSections() {

        return classSectionRepo.findAllWithDetails()
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    @Override
    public List<ClassSectionResponse>
    getClassSectionsByAcademicYear(
            Long academicYearId) {

        getAcademicYear(academicYearId);

        return classSectionRepo
                .findByAcademicYearIdWithDetails(academicYearId)
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    @Override
    public List<ClassSectionResponse>
    getClassSectionsByStandard(
            Long standardId) {

        getStandard(standardId);

        return classSectionRepo
                .findByStandardIdWithDetails(standardId)
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    @Override
    public List<ClassSectionResponse>
    getClassSectionsByAcademicYearAndStandard(
            Long academicYearId,
            Long standardId) {

        getAcademicYear(academicYearId);
        getStandard(standardId);

        return classSectionRepo
                .findByAcademicYearIdAndStandardIdWithDetails(
                        academicYearId,
                        standardId
                )
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    @Override
    public ClassSectionResponse getClassSectionById(
            Long id) {

        ClassSection classSection =
                classSectionRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Class section not found with id: "
                                                + id
                                ));

        return mapEntityToResponse(classSection);
    }

    @Override
    public ClassSectionResponse updateClassSection(
            Long id,
            ClassSectionRequest request) {

        ClassSection classSection =
                classSectionRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Class section not found with id: "
                                                + id
                                ));

        AcademicYear academicYear =
                getAcademicYear(
                        request.getAcademicYearId()
                );

        Standard standard =
                getStandard(
                        request.getStandardId()
                );

        boolean combinationChanged =
                !classSection.getAcademicYear()
                        .getId()
                        .equals(request.getAcademicYearId())
                        ||
                        !classSection.getStandard()
                                .getId()
                                .equals(request.getStandardId())
                        ||
                        !classSection.getSectionName()
                                .equals(request.getSectionName());

        if (combinationChanged) {

            validateDuplicate(
                    request.getAcademicYearId(),
                    request.getStandardId(),
                    request.getSectionName()
            );
        }

        classSection.setAcademicYear(academicYear);
        classSection.setStandard(standard);
        classSection.setSectionName(
                request.getSectionName()
        );
        classSection.setActive(
                request.isActive()
        );

        ClassSection updatedClassSection =
                classSectionRepo.save(classSection);

        return mapEntityToResponse(updatedClassSection);
    }

    @Override
    public void deleteClassSection(Long id) {

        if (!classSectionRepo.existsById(id)) {

            throw new IllegalArgumentException(
                    "Class section not found with id: " + id
            );
        }

        classSectionRepo.deleteById(id);
    }

    private AcademicYear getAcademicYear(Long id) {

        return academicYearRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Academic year not found with id: "
                                        + id
                        ));
    }

    private Standard getStandard(Long id) {

        return standardRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Standard not found with id: "
                                        + id
                        ));
    }

    private void validateDuplicate(
            Long academicYearId,
            Long standardId,
            String sectionName) {

        if (classSectionRepo
                .existsByAcademicYearIdAndStandardIdAndSectionName(
                        academicYearId,
                        standardId,
                        sectionName
                )) {

            throw new IllegalArgumentException(
                    "Class section already exists for the selected "
                            + "academic year, standard and section"
            );
        }
    }

    private ClassSectionResponse mapEntityToResponse(
            ClassSection classSection) {

        return new ClassSectionResponse(
                classSection.getId(),

                classSection.getAcademicYear().getId(),
                classSection.getAcademicYear().getAcademicYear(),

                classSection.getStandard().getId(),
                classSection.getStandard().getStandardName(),

                classSection.getSectionName(),

                classSection.isActive(),

                classSection.getCreatedAt(),
                classSection.getUpdatedAt()
        );
    }
}

