package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.AcademicYearRequest;
import com.schooleERP.dto.AcademicYearResponse;
import com.schooleERP.entity.AcademicYear;
import com.schooleERP.repository.AcademicYearRepo;
import com.schooleERP.service.AcademicYearService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicYearServiceImpl
        implements AcademicYearService {

    private final AcademicYearRepo academicYearRepo;

    public AcademicYearServiceImpl(
            AcademicYearRepo academicYearRepo) {
        this.academicYearRepo = academicYearRepo;
    }

    @Override
    public AcademicYearResponse createAcademicYear(
            AcademicYearRequest request) {

        if (academicYearRepo.existsByAcademicYear(
                request.getAcademicYear())) {

            throw new IllegalArgumentException(
                    "Academic year already exists: "
                            + request.getAcademicYear()
            );
        }

        validateDates(request);

        if (request.isActive()) {
            deactivateCurrentAcademicYear();
        }

        AcademicYear academicYear = new AcademicYear();

        mapRequestToEntity(request, academicYear);

        AcademicYear savedAcademicYear =
                academicYearRepo.save(academicYear);

        return mapEntityToResponse(savedAcademicYear);
    }

    @Override
    public List<AcademicYearResponse> getAllAcademicYears() {

        return academicYearRepo.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    @Override
    public AcademicYearResponse getAcademicYearById(
            Long id) {

        AcademicYear academicYear =
                academicYearRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Academic year not found with id: "
                                                + id
                                ));

        return mapEntityToResponse(academicYear);
    }

    @Override
    public AcademicYearResponse updateAcademicYear(
            Long id,
            AcademicYearRequest request) {

        AcademicYear academicYear =
                academicYearRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Academic year not found with id: "
                                                + id
                                ));

        validateDates(request);

        if (!academicYear.getAcademicYear()
                .equals(request.getAcademicYear())
                && academicYearRepo.existsByAcademicYear(
                request.getAcademicYear())) {

            throw new IllegalArgumentException(
                    "Academic year already exists: "
                            + request.getAcademicYear()
            );
        }

        if (request.isActive()) {
            deactivateOtherAcademicYears(id);
        }

        mapRequestToEntity(request, academicYear);

        AcademicYear updatedAcademicYear =
                academicYearRepo.save(academicYear);

        return mapEntityToResponse(updatedAcademicYear);
    }

    @Override
    public void deleteAcademicYear(Long id) {

        if (!academicYearRepo.existsById(id)) {

            throw new IllegalArgumentException(
                    "Academic year not found with id: " + id
            );
        }

        academicYearRepo.deleteById(id);
    }

    @Override
    public AcademicYearResponse getActiveAcademicYear() {

        AcademicYear activeAcademicYear =
                academicYearRepo.findByActiveTrue()
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "No active academic year found"
                                ));

        return mapEntityToResponse(activeAcademicYear);
    }

    private void deactivateCurrentAcademicYear() {

        academicYearRepo.findByActiveTrue()
                .ifPresent(currentYear -> {

                    currentYear.setActive(false);

                    academicYearRepo.save(currentYear);
                });
    }

    private void deactivateOtherAcademicYears(Long currentId) {

        academicYearRepo.findByActiveTrue()
                .ifPresent(activeYear -> {

                    if (!activeYear.getId().equals(currentId)) {

                        activeYear.setActive(false);

                        academicYearRepo.save(activeYear);
                    }
                });
    }

    private void validateDates(
            AcademicYearRequest request) {

        if (request.getStartDate()
                .isAfter(request.getEndDate())) {

            throw new IllegalArgumentException(
                    "Start date cannot be after end date"
            );
        }
    }

    private void mapRequestToEntity(
            AcademicYearRequest request,
            AcademicYear academicYear) {

        academicYear.setAcademicYear(
                request.getAcademicYear()
        );

        academicYear.setStartDate(
                request.getStartDate()
        );

        academicYear.setEndDate(
                request.getEndDate()
        );

        academicYear.setActive(
                request.isActive()
        );
    }

    private AcademicYearResponse mapEntityToResponse(
            AcademicYear academicYear) {

        return new AcademicYearResponse(
                academicYear.getId(),
                academicYear.getAcademicYear(),
                academicYear.getStartDate(),
                academicYear.getEndDate(),
                academicYear.isActive(),
                academicYear.getCreatedAt(),
                academicYear.getUpdatedAt()
        );
    }
}

