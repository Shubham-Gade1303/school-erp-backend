package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.ExamRequest;
import com.schooleERP.dto.ExamResponse;
import com.schooleERP.entity.AcademicYear;
import com.schooleERP.entity.Exam;
import com.schooleERP.entity.Standard;
import com.schooleERP.repository.AcademicYearRepo;
import com.schooleERP.repository.ExamRepo;
import com.schooleERP.repository.StandardRepo;
import com.schooleERP.service.ExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    private final ExamRepo examRepo;
    private final AcademicYearRepo academicYearRepo;
    private final StandardRepo standardRepo;

    public ExamServiceImpl(
            ExamRepo examRepo,
            AcademicYearRepo academicYearRepo,
            StandardRepo standardRepo
    ) {
        this.examRepo = examRepo;
        this.academicYearRepo = academicYearRepo;
        this.standardRepo = standardRepo;
    }

    @Override
    public ExamResponse createExam(ExamRequest request) {

        AcademicYear academicYear = academicYearRepo.findById(
                request.getAcademicYearId()
        ).orElseThrow(() ->
                new IllegalArgumentException(
                        "Academic year not found with id: "
                                + request.getAcademicYearId()
                )
        );

        Standard standard = standardRepo.findById(
                request.getStandardId()
        ).orElseThrow(() ->
                new IllegalArgumentException(
                        "Standard not found with id: "
                                + request.getStandardId()
                )
        );

        validateDates(request);

        if (examRepo.existsByExamNameAndAcademicYearIdAndStandardId(
                request.getExamName(),
                request.getAcademicYearId(),
                request.getStandardId()
        )) {
            throw new IllegalArgumentException(
                    "Exam already exists for this academic year and standard"
            );
        }

        Exam exam = new Exam();

        exam.setExamName(request.getExamName());
        exam.setAcademicYear(academicYear);
        exam.setStandard(standard);
        exam.setStartDate(request.getStartDate());
        exam.setEndDate(request.getEndDate());
        exam.setActive(request.isActive());

        return mapToResponse(examRepo.save(exam));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResponse> getAllExams() {

        return examRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResponse getExamById(Long id) {

        Exam exam = examRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Exam not found with id: " + id
                        )
                );

        return mapToResponse(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResponse> getExamsByAcademicYear(
            Long academicYearId
    ) {

        if (!academicYearRepo.existsById(academicYearId)) {
            throw new IllegalArgumentException(
                    "Academic year not found with id: "
                            + academicYearId
            );
        }

        return examRepo.findByAcademicYearId(academicYearId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResponse> getExamsByStandard(
            Long standardId
    ) {

        if (!standardRepo.existsById(standardId)) {
            throw new IllegalArgumentException(
                    "Standard not found with id: " + standardId
            );
        }

        return examRepo.findByStandardId(standardId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResponse> getExamsByAcademicYearAndStandard(
            Long academicYearId,
            Long standardId
    ) {

        if (!academicYearRepo.existsById(academicYearId)) {
            throw new IllegalArgumentException(
                    "Academic year not found with id: "
                            + academicYearId
            );
        }

        if (!standardRepo.existsById(standardId)) {
            throw new IllegalArgumentException(
                    "Standard not found with id: " + standardId
            );
        }

        return examRepo
                .findByAcademicYearIdAndStandardId(
                        academicYearId,
                        standardId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ExamResponse updateExam(
            Long id,
            ExamRequest request
    ) {

        Exam exam = examRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Exam not found with id: " + id
                        )
                );

        AcademicYear academicYear = academicYearRepo.findById(
                request.getAcademicYearId()
        ).orElseThrow(() ->
                new IllegalArgumentException(
                        "Academic year not found with id: "
                                + request.getAcademicYearId()
                )
        );

        Standard standard = standardRepo.findById(
                request.getStandardId()
        ).orElseThrow(() ->
                new IllegalArgumentException(
                        "Standard not found with id: "
                                + request.getStandardId()
                )
        );

        validateDates(request);

        boolean examChanged =
                !exam.getExamName().equals(request.getExamName())
                        || !exam.getAcademicYear().getId()
                        .equals(request.getAcademicYearId())
                        || !exam.getStandard().getId()
                        .equals(request.getStandardId());

        if (examChanged &&
                examRepo.existsByExamNameAndAcademicYearIdAndStandardId(
                        request.getExamName(),
                        request.getAcademicYearId(),
                        request.getStandardId()
                )) {

            throw new IllegalArgumentException(
                    "Exam already exists for this academic year and standard"
            );
        }

        exam.setExamName(request.getExamName());
        exam.setAcademicYear(academicYear);
        exam.setStandard(standard);
        exam.setStartDate(request.getStartDate());
        exam.setEndDate(request.getEndDate());
        exam.setActive(request.isActive());

        return mapToResponse(examRepo.save(exam));
    }

    @Override
    public void deleteExam(Long id) {

        if (!examRepo.existsById(id)) {
            throw new IllegalArgumentException(
                    "Exam not found with id: " + id
            );
        }

        examRepo.deleteById(id);
    }

    private void validateDates(ExamRequest request) {

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException(
                    "Exam start date cannot be after end date"
            );
        }
    }

    private ExamResponse mapToResponse(Exam exam) {

        ExamResponse response = new ExamResponse();

        response.setId(exam.getId());
        response.setExamName(exam.getExamName());

        response.setAcademicYearId(
                exam.getAcademicYear().getId()
        );
        response.setAcademicYear(
                exam.getAcademicYear().getAcademicYear()
        );

        response.setStandardId(
                exam.getStandard().getId()
        );
        response.setStandardName(
                exam.getStandard().getStandardName()
        );

        response.setStartDate(exam.getStartDate());
        response.setEndDate(exam.getEndDate());
        response.setActive(exam.isActive());

        return response;
    }
}

