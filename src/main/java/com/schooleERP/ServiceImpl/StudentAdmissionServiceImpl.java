package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentAdmissionRequest;
import com.schooleERP.dto.StudentAdmissionResponse;
import com.schooleERP.entity.AcademicYear;
import com.schooleERP.entity.ClassSection;
import com.schooleERP.entity.Standard;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentAdmission;
import com.schooleERP.repository.AcademicYearRepo;
import com.schooleERP.repository.ClassSectionRepo;
import com.schooleERP.repository.StandardRepo;
import com.schooleERP.repository.StudentAdmissionRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.StudentAdmissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentAdmissionServiceImpl implements StudentAdmissionService {

    private final StudentAdmissionRepo studentAdmissionRepo;
    private final StudentRepo studentRepo;
    private final AcademicYearRepo academicYearRepo;
    private final StandardRepo standardRepo;
    private final ClassSectionRepo classSectionRepo;

    public StudentAdmissionServiceImpl(
            StudentAdmissionRepo studentAdmissionRepo,
            StudentRepo studentRepo,
            AcademicYearRepo academicYearRepo,
            StandardRepo standardRepo,
            ClassSectionRepo classSectionRepo
    ) {
        this.studentAdmissionRepo = studentAdmissionRepo;
        this.studentRepo = studentRepo;
        this.academicYearRepo = academicYearRepo;
        this.standardRepo = standardRepo;
        this.classSectionRepo = classSectionRepo;
    }

    @Override
    public StudentAdmissionResponse createAdmission(
            StudentAdmissionRequest request
    ) {

        Student student = getStudent(request.getStudentId());

        if (studentAdmissionRepo.existsByStudentId(
                request.getStudentId()
        )) {
            throw new IllegalArgumentException(
                    "Admission already exists for student: "
                            + request.getStudentId()
            );
        }

        AcademicYear academicYear =
                getAcademicYear(request.getAcademicYearId());

        Standard standard =
                getStandard(request.getStandardId());

        ClassSection classSection =
                getClassSection(request.getClassSectionId());

        validateClassSection(
                classSection,
                academicYear,
                standard
        );

        StudentAdmission admission = new StudentAdmission();

        admission.setStudent(student);
        admission.setAcademicYear(academicYear);
        admission.setStandard(standard);
        admission.setClassSection(classSection);
        admission.setAdmissionDate(request.getAdmissionDate());
        admission.setAdmissionStatus(request.getAdmissionStatus());
        admission.setRemarks(request.getRemarks());

        StudentAdmission savedAdmission =
                studentAdmissionRepo.save(admission);

        return mapToResponse(savedAdmission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentAdmissionResponse> getAllAdmissions() {

        return studentAdmissionRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentAdmissionResponse getAdmissionById(Long id) {

        StudentAdmission admission =
                studentAdmissionRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Admission not found with id: " + id
                        ));

        return mapToResponse(admission);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentAdmissionResponse getAdmissionByStudentId(
            Long studentId
    ) {

        StudentAdmission admission =
                studentAdmissionRepo.findByStudentId(studentId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Admission not found for student id: "
                                        + studentId
                        ));

        return mapToResponse(admission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentAdmissionResponse> getAdmissionsByAcademicYear(
            Long academicYearId
    ) {

        return studentAdmissionRepo
                .findByAcademicYearId(academicYearId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentAdmissionResponse> getAdmissionsByStandard(
            Long standardId
    ) {

        return studentAdmissionRepo
                .findByStandardId(standardId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentAdmissionResponse> getAdmissionsByClassSection(
            Long classSectionId
    ) {

        return studentAdmissionRepo
                .findByClassSectionId(classSectionId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentAdmissionResponse> getAdmissionsByStatus(
            String admissionStatus
    ) {

        return studentAdmissionRepo
                .findByAdmissionStatus(admissionStatus)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentAdmissionResponse updateAdmission(
            Long id,
            StudentAdmissionRequest request
    ) {

        StudentAdmission admission =
                studentAdmissionRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Admission not found with id: " + id
                        ));

        Student student = getStudent(request.getStudentId());

        AcademicYear academicYear =
                getAcademicYear(request.getAcademicYearId());

        Standard standard =
                getStandard(request.getStandardId());

        ClassSection classSection =
                getClassSection(request.getClassSectionId());

        validateClassSection(
                classSection,
                academicYear,
                standard
        );

        if (!admission.getStudent().getId()
                .equals(request.getStudentId())
                && studentAdmissionRepo.existsByStudentId(
                request.getStudentId()
        )) {

            throw new IllegalArgumentException(
                    "Admission already exists for student: "
                            + request.getStudentId()
            );
        }

        admission.setStudent(student);
        admission.setAcademicYear(academicYear);
        admission.setStandard(standard);
        admission.setClassSection(classSection);
        admission.setAdmissionDate(request.getAdmissionDate());
        admission.setAdmissionStatus(request.getAdmissionStatus());
        admission.setRemarks(request.getRemarks());

        StudentAdmission updatedAdmission =
                studentAdmissionRepo.save(admission);

        return mapToResponse(updatedAdmission);
    }

    @Override
    public void deleteAdmission(Long id) {

        StudentAdmission admission =
                studentAdmissionRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Admission not found with id: " + id
                        ));

        studentAdmissionRepo.delete(admission);
    }

    private Student getStudent(Long studentId) {

        return studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found with id: " + studentId
                ));
    }

    private AcademicYear getAcademicYear(Long academicYearId) {

        return academicYearRepo.findById(academicYearId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Academic year not found with id: "
                                + academicYearId
                ));
    }

    private Standard getStandard(Long standardId) {

        return standardRepo.findById(standardId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Standard not found with id: " + standardId
                ));
    }

    private ClassSection getClassSection(Long classSectionId) {

        return classSectionRepo.findById(classSectionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Class section not found with id: "
                                + classSectionId
                ));
    }

    private void validateClassSection(
            ClassSection classSection,
            AcademicYear academicYear,
            Standard standard
    ) {

        if (!classSection.getAcademicYear().getId()
                .equals(academicYear.getId())) {

            throw new IllegalArgumentException(
                    "Class section does not belong to the selected "
                            + "academic year"
            );
        }

        if (!classSection.getStandard().getId()
                .equals(standard.getId())) {

            throw new IllegalArgumentException(
                    "Class section does not belong to the selected "
                            + "standard"
            );
        }

        if (!classSection.isActive()) {

            throw new IllegalArgumentException(
                    "Selected class section is inactive"
            );
        }
    }

    private StudentAdmissionResponse mapToResponse(
            StudentAdmission admission
    ) {

        Student student = admission.getStudent();
        AcademicYear academicYear = admission.getAcademicYear();
        Standard standard = admission.getStandard();
        ClassSection classSection = admission.getClassSection();

        StudentAdmissionResponse response =
                new StudentAdmissionResponse();

        response.setId(admission.getId());

        response.setStudentId(student.getId());
        response.setAdmissionNumber(
                student.getAdmissionNumber()
        );

        response.setStudentName(
                buildStudentName(student)
        );

        response.setAcademicYearId(
                academicYear.getId()
        );
        response.setAcademicYear(
                academicYear.getAcademicYear()
        );

        response.setStandardId(
                standard.getId()
        );
        response.setStandardName(
                standard.getStandardName()
        );

        response.setClassSectionId(
                classSection.getId()
        );
        response.setSectionName(
                classSection.getSectionName()
        );

        response.setAdmissionDate(
                admission.getAdmissionDate()
        );

        response.setAdmissionStatus(
                admission.getAdmissionStatus()
        );

        response.setRemarks(
                admission.getRemarks()
        );

        return response;
    }

    private String buildStudentName(Student student) {

        StringBuilder name = new StringBuilder();

        name.append(student.getFirstName());

        if (student.getMiddleName() != null
                && !student.getMiddleName().isBlank()) {

            name.append(" ")
                    .append(student.getMiddleName());
        }

        name.append(" ")
                .append(student.getLastName());

        return name.toString();
    }
}

