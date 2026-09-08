package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.ClassSubjectRequest;
import com.schooleERP.dto.ClassSubjectResponse;
import com.schooleERP.entity.AcademicYear;
import com.schooleERP.entity.ClassSubject;
import com.schooleERP.entity.Standard;
import com.schooleERP.entity.Subject;
import com.schooleERP.repository.AcademicYearRepo;
import com.schooleERP.repository.ClassSubjectRepo;
import com.schooleERP.repository.StandardRepo;
import com.schooleERP.repository.SubjectRepo;
import com.schooleERP.service.ClassSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassSubjectServiceImpl implements ClassSubjectService {

    private final ClassSubjectRepo classSubjectRepo;
    private final AcademicYearRepo academicYearRepo;
    private final StandardRepo standardRepo;
    private final SubjectRepo subjectRepo;

    public ClassSubjectServiceImpl(
            ClassSubjectRepo classSubjectRepo,
            AcademicYearRepo academicYearRepo,
            StandardRepo standardRepo,
            SubjectRepo subjectRepo
    ) {
        this.classSubjectRepo = classSubjectRepo;
        this.academicYearRepo = academicYearRepo;
        this.standardRepo = standardRepo;
        this.subjectRepo = subjectRepo;
    }

    @Override
    public ClassSubjectResponse createClassSubject(
            ClassSubjectRequest request
    ) {

        // Check duplicate mapping
        if (classSubjectRepo
                .existsByAcademicYearIdAndStandardIdAndSubjectId(
                        request.getAcademicYearId(),
                        request.getStandardId(),
                        request.getSubjectId()
                )) {

            throw new IllegalArgumentException(
                    "This subject is already assigned to this standard for the selected academic year"
            );
        }

        // Find Academic Year
        AcademicYear academicYear = academicYearRepo
                .findById(request.getAcademicYearId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Academic Year not found with id: "
                                        + request.getAcademicYearId()
                        )
                );

        // Find Standard
        Standard standard = standardRepo
                .findById(request.getStandardId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Standard not found with id: "
                                        + request.getStandardId()
                        )
                );

        // Find Subject
        Subject subject = subjectRepo
                .findById(request.getSubjectId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Subject not found with id: "
                                        + request.getSubjectId()
                        )
                );

        // Create mapping
        ClassSubject classSubject = new ClassSubject();

        classSubject.setAcademicYear(academicYear);
        classSubject.setStandard(standard);
        classSubject.setSubject(subject);
        classSubject.setActive(request.isActive());

        ClassSubject saved = classSubjectRepo.save(classSubject);

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubjectResponse> getAllClassSubjects() {

        return classSubjectRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClassSubjectResponse getClassSubjectById(Long id) {

        ClassSubject classSubject = classSubjectRepo
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Class Subject not found with id: " + id
                        )
                );

        return mapToResponse(classSubject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubjectResponse> getByAcademicYear(
            Long academicYearId
    ) {

        return classSubjectRepo
                .findByAcademicYearId(academicYearId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubjectResponse> getByStandard(
            Long standardId
    ) {

        return classSubjectRepo
                .findByStandardId(standardId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubjectResponse> getBySubject(
            Long subjectId
    ) {

        return classSubjectRepo
                .findBySubjectId(subjectId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubjectResponse> getByAcademicYearAndStandard(
            Long academicYearId,
            Long standardId
    ) {

        return classSubjectRepo
                .findByAcademicYearIdAndStandardId(
                        academicYearId,
                        standardId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ClassSubjectResponse updateClassSubject(
            Long id,
            ClassSubjectRequest request
    ) {

        ClassSubject classSubject = classSubjectRepo
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Class Subject not found with id: " + id
                        )
                );

        // Check duplicate only if another record has the same mapping
        boolean duplicate =
                classSubjectRepo
                        .existsByAcademicYearIdAndStandardIdAndSubjectId(
                                request.getAcademicYearId(),
                                request.getStandardId(),
                                request.getSubjectId()
                        );

        if (duplicate &&
                !(classSubject.getAcademicYear().getId()
                        .equals(request.getAcademicYearId())
                        &&
                        classSubject.getStandard().getId()
                                .equals(request.getStandardId())
                        &&
                        classSubject.getSubject().getId()
                                .equals(request.getSubjectId()))) {

            throw new IllegalArgumentException(
                    "This subject is already assigned to this standard for the selected academic year"
            );
        }

        // Find Academic Year
        AcademicYear academicYear = academicYearRepo
                .findById(request.getAcademicYearId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Academic Year not found with id: "
                                        + request.getAcademicYearId()
                        )
                );

        // Find Standard
        Standard standard = standardRepo
                .findById(request.getStandardId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Standard not found with id: "
                                        + request.getStandardId()
                        )
                );

        // Find Subject
        Subject subject = subjectRepo
                .findById(request.getSubjectId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Subject not found with id: "
                                        + request.getSubjectId()
                        )
                );

        classSubject.setAcademicYear(academicYear);
        classSubject.setStandard(standard);
        classSubject.setSubject(subject);
        classSubject.setActive(request.isActive());

        ClassSubject updated = classSubjectRepo.save(classSubject);

        return mapToResponse(updated);
    }

    @Override
    public void deleteClassSubject(Long id) {

        ClassSubject classSubject = classSubjectRepo
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Class Subject not found with id: " + id
                        )
                );

        classSubjectRepo.delete(classSubject);
    }

    private ClassSubjectResponse mapToResponse(
            ClassSubject classSubject
    ) {

        ClassSubjectResponse response =
                new ClassSubjectResponse();

        response.setId(classSubject.getId());

        response.setAcademicYearId(
                classSubject.getAcademicYear().getId()
        );

        response.setAcademicYear(
                classSubject.getAcademicYear().getAcademicYear()
        );

        response.setStandardId(
                classSubject.getStandard().getId()
        );

        response.setStandardName(
                classSubject.getStandard().getStandardName()
        );

        response.setSubjectId(
                classSubject.getSubject().getId()
        );

        response.setSubjectName(
                classSubject.getSubject().getSubjectName()
        );

        response.setSubjectCode(
                classSubject.getSubject().getSubjectCode()
        );

        response.setActive(classSubject.isActive());

        return response;
    }
}
