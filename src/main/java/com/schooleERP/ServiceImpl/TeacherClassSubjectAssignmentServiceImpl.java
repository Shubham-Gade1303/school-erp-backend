package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.TeacherClassSubjectAssignmentRequest;
import com.schooleERP.dto.TeacherClassSubjectAssignmentResponse;
import com.schooleERP.entity.ClassSection;
import com.schooleERP.entity.ClassSubject;
import com.schooleERP.entity.Teacher;
import com.schooleERP.entity.TeacherClassSubjectAssignment;
import com.schooleERP.repository.ClassSectionRepo;
import com.schooleERP.repository.ClassSubjectRepo;
import com.schooleERP.repository.TeacherClassSubjectAssignmentRepo;
import com.schooleERP.repository.TeacherRepo;
import com.schooleERP.service.TeacherClassSubjectAssignmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherClassSubjectAssignmentServiceImpl
        implements TeacherClassSubjectAssignmentService {

    private final TeacherClassSubjectAssignmentRepo assignmentRepo;
    private final TeacherRepo teacherRepo;
    private final ClassSectionRepo classSectionRepo;
    private final ClassSubjectRepo classSubjectRepo;

    public TeacherClassSubjectAssignmentServiceImpl(
            TeacherClassSubjectAssignmentRepo assignmentRepo,
            TeacherRepo teacherRepo,
            ClassSectionRepo classSectionRepo,
            ClassSubjectRepo classSubjectRepo
    ) {
        this.assignmentRepo = assignmentRepo;
        this.teacherRepo = teacherRepo;
        this.classSectionRepo = classSectionRepo;
        this.classSubjectRepo = classSubjectRepo;
    }

    // =========================
    // CREATE ASSIGNMENT
    // =========================

    @Override
    public TeacherClassSubjectAssignmentResponse createAssignment(
            TeacherClassSubjectAssignmentRequest request
    ) {

        // Check duplicate assignment
        if (assignmentRepo
                .existsByTeacherIdAndClassSectionIdAndClassSubjectId(
                        request.getTeacherId(),
                        request.getClassSectionId(),
                        request.getClassSubjectId()
                )) {

            throw new IllegalArgumentException(
                    "This teacher is already assigned to this subject for this class section"
            );
        }

        // Find Teacher
        Teacher teacher = teacherRepo
                .findById(request.getTeacherId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Teacher not found with id: "
                                        + request.getTeacherId()
                        )
                );

        // Teacher must be active
        if (!teacher.isActive()) {
            throw new IllegalArgumentException(
                    "Cannot assign an inactive teacher"
            );
        }

        // Find Class Section
        ClassSection classSection = classSectionRepo
                .findById(request.getClassSectionId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Class Section not found with id: "
                                        + request.getClassSectionId()
                        )
                );

        // Find Class Subject
        ClassSubject classSubject = classSubjectRepo
                .findById(request.getClassSubjectId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Class Subject not found with id: "
                                        + request.getClassSubjectId()
                        )
                );

        // Validate ClassSubject belongs to same
        // Academic Year and Standard as ClassSection
        validateClassSubjectForSection(
                classSection,
                classSubject
        );

        // Create assignment
        TeacherClassSubjectAssignment assignment =
                new TeacherClassSubjectAssignment();

        assignment.setTeacher(teacher);
        assignment.setClassSection(classSection);
        assignment.setClassSubject(classSubject);
        assignment.setActive(request.isActive());

        TeacherClassSubjectAssignment saved =
                assignmentRepo.save(assignment);

        return mapToResponse(saved);
    }

    // =========================
    // GET ALL
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<TeacherClassSubjectAssignmentResponse>
    getAllAssignments() {

        return assignmentRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // GET BY ID
    // =========================

    @Override
    @Transactional(readOnly = true)
    public TeacherClassSubjectAssignmentResponse
    getAssignmentById(Long id) {

        TeacherClassSubjectAssignment assignment =
                assignmentRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Assignment not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(assignment);
    }

    // =========================
    // GET BY TEACHER
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<TeacherClassSubjectAssignmentResponse>
    getByTeacher(Long teacherId) {

        return assignmentRepo
                .findByTeacherId(teacherId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // GET BY CLASS SECTION
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<TeacherClassSubjectAssignmentResponse>
    getByClassSection(Long classSectionId) {

        return assignmentRepo
                .findByClassSectionId(classSectionId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // GET BY CLASS SUBJECT
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<TeacherClassSubjectAssignmentResponse>
    getByClassSubject(Long classSubjectId) {

        return assignmentRepo
                .findByClassSubjectId(classSubjectId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // GET BY SECTION + SUBJECT
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<TeacherClassSubjectAssignmentResponse>
    getByClassSectionAndClassSubject(
            Long classSectionId,
            Long classSubjectId
    ) {

        return assignmentRepo
                .findByClassSectionIdAndClassSubjectId(
                        classSectionId,
                        classSubjectId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // UPDATE
    // =========================

    @Override
    public TeacherClassSubjectAssignmentResponse updateAssignment(
            Long id,
            TeacherClassSubjectAssignmentRequest request
    ) {

        TeacherClassSubjectAssignment assignment =
                assignmentRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Assignment not found with id: "
                                                + id
                                )
                        );

        // Check duplicate assignment
        boolean duplicate =
                assignmentRepo
                        .existsByTeacherIdAndClassSectionIdAndClassSubjectId(
                                request.getTeacherId(),
                                request.getClassSectionId(),
                                request.getClassSubjectId()
                        );

        boolean sameAssignment =
                assignment.getTeacher().getId()
                        .equals(request.getTeacherId())
                        &&
                        assignment.getClassSection().getId()
                                .equals(request.getClassSectionId())
                        &&
                        assignment.getClassSubject().getId()
                                .equals(request.getClassSubjectId());

        if (duplicate && !sameAssignment) {

            throw new IllegalArgumentException(
                    "This teacher is already assigned to this subject for this class section"
            );
        }

        // Find Teacher
        Teacher teacher = teacherRepo
                .findById(request.getTeacherId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Teacher not found with id: "
                                        + request.getTeacherId()
                        )
                );

        if (!teacher.isActive()) {
            throw new IllegalArgumentException(
                    "Cannot assign an inactive teacher"
            );
        }

        // Find Class Section
        ClassSection classSection = classSectionRepo
                .findById(request.getClassSectionId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Class Section not found with id: "
                                        + request.getClassSectionId()
                        )
                );

        // Find Class Subject
        ClassSubject classSubject = classSubjectRepo
                .findById(request.getClassSubjectId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Class Subject not found with id: "
                                        + request.getClassSubjectId()
                        )
                );

        // Validate relationship
        validateClassSubjectForSection(
                classSection,
                classSubject
        );

        assignment.setTeacher(teacher);
        assignment.setClassSection(classSection);
        assignment.setClassSubject(classSubject);
        assignment.setActive(request.isActive());

        TeacherClassSubjectAssignment updated =
                assignmentRepo.save(assignment);

        return mapToResponse(updated);
    }

    // =========================
    // DELETE
    // =========================

    @Override
    public void deleteAssignment(Long id) {

        TeacherClassSubjectAssignment assignment =
                assignmentRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Assignment not found with id: "
                                                + id
                                )
                        );

        assignmentRepo.delete(assignment);
    }

    // =========================
    // VALIDATE RELATIONSHIP
    // =========================

    private void validateClassSubjectForSection(
            ClassSection classSection,
            ClassSubject classSubject
    ) {

        Long sectionAcademicYearId =
                classSection.getAcademicYear().getId();

        Long subjectAcademicYearId =
                classSubject.getAcademicYear().getId();

        Long sectionStandardId =
                classSection.getStandard().getId();

        Long subjectStandardId =
                classSubject.getStandard().getId();

        if (!sectionAcademicYearId.equals(subjectAcademicYearId)) {

            throw new IllegalArgumentException(
                    "Class Subject belongs to a different academic year"
            );
        }

        if (!sectionStandardId.equals(subjectStandardId)) {

            throw new IllegalArgumentException(
                    "Class Subject does not belong to the selected standard"
            );
        }

        if (!classSubject.isActive()) {

            throw new IllegalArgumentException(
                    "Cannot assign an inactive class subject"
            );
        }

        if (!classSection.isActive()) {

            throw new IllegalArgumentException(
                    "Cannot assign a teacher to an inactive class section"
            );
        }
    }

    // =========================
    // ENTITY → RESPONSE
    // =========================

    private TeacherClassSubjectAssignmentResponse
    mapToResponse(
            TeacherClassSubjectAssignment assignment
    ) {

        TeacherClassSubjectAssignmentResponse response =
                new TeacherClassSubjectAssignmentResponse();

        response.setId(assignment.getId());

        // Teacher
        Teacher teacher = assignment.getTeacher();

        response.setTeacherId(teacher.getId());
        response.setTeacherName(teacher.getFullName());
        response.setEmployeeCode(teacher.getEmployeeCode());

        // Class Section
        ClassSection classSection =
                assignment.getClassSection();

        response.setClassSectionId(
                classSection.getId()
        );

        response.setSectionName(
                classSection.getSectionName()
        );

        // Class Subject
        ClassSubject classSubject =
                assignment.getClassSubject();

        response.setClassSubjectId(
                classSubject.getId()
        );

        response.setSubjectName(
                classSubject.getSubject().getSubjectName()
        );

        response.setSubjectCode(
                classSubject.getSubject().getSubjectCode()
        );

        response.setStandardName(
                classSubject.getStandard().getStandardName()
        );

        response.setAcademicYear(
                classSubject.getAcademicYear().getAcademicYear()
        );

        response.setActive(assignment.isActive());

        return response;
    }
}
