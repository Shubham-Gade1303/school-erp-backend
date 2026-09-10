package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentRequest;
import com.schooleERP.dto.StudentResponse;
import com.schooleERP.entity.ClassSection;
import com.schooleERP.entity.Student;
import com.schooleERP.repository.ClassSectionRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final ClassSectionRepo classSectionRepo;

    public StudentServiceImpl(
            StudentRepo studentRepo,
            ClassSectionRepo classSectionRepo
    ) {
        this.studentRepo = studentRepo;
        this.classSectionRepo = classSectionRepo;
    }

    @Override
    public StudentResponse createStudent(StudentRequest request) {

        if (studentRepo.existsByAdmissionNumber(request.getAdmissionNumber())) {
            throw new IllegalArgumentException(
                    "Student with admission number already exists: "
                            + request.getAdmissionNumber()
            );
        }

        ClassSection classSection = getClassSection(request.getClassSectionId());

        Student student = new Student();

        mapRequestToEntity(student, request, classSection);

        Student savedStudent = studentRepo.save(student);

        return mapToResponse(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {

        return studentRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {

        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found with id: " + id
                ));

        return mapToResponse(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentByAdmissionNumber(
            String admissionNumber
    ) {

        Student student = studentRepo
                .findByAdmissionNumber(admissionNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found with admission number: "
                                + admissionNumber
                ));

        return mapToResponse(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByClassSection(
            Long classSectionId
    ) {

        // Verify class section exists
        getClassSection(classSectionId);

        return studentRepo.findByClassSectionId(classSectionId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getActiveStudents() {

        return studentRepo.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentResponse updateStudent(
            Long id,
            StudentRequest request
    ) {

        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found with id: " + id
                ));

        if (studentRepo.existsByAdmissionNumberAndIdNot(
                request.getAdmissionNumber(),
                id
        )) {
            throw new IllegalArgumentException(
                    "Student with admission number already exists: "
                            + request.getAdmissionNumber()
            );
        }

        ClassSection classSection =
                getClassSection(request.getClassSectionId());

        mapRequestToEntity(student, request, classSection);

        Student updatedStudent = studentRepo.save(student);

        return mapToResponse(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {

        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found with id: " + id
                ));

        studentRepo.delete(student);
    }

    private ClassSection getClassSection(Long classSectionId) {

        return classSectionRepo.findById(classSectionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Class section not found with id: "
                                + classSectionId
                ));
    }

    private void mapRequestToEntity(
            Student student,
            StudentRequest request,
            ClassSection classSection
    ) {

        student.setAdmissionNumber(request.getAdmissionNumber());
        student.setFirstName(request.getFirstName());
        student.setMiddleName(request.getMiddleName());
        student.setLastName(request.getLastName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setBloodGroup(request.getBloodGroup());
        student.setClassSection(classSection);
        student.setActive(request.isActive());
    }

    private StudentResponse mapToResponse(Student student) {

        ClassSection classSection = student.getClassSection();

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setAdmissionNumber(student.getAdmissionNumber());
        response.setFirstName(student.getFirstName());
        response.setMiddleName(student.getMiddleName());
        response.setLastName(student.getLastName());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setGender(student.getGender());
        response.setBloodGroup(student.getBloodGroup());

        response.setClassSectionId(classSection.getId());
        response.setSectionName(classSection.getSectionName());

        if (classSection.getStandard() != null) {
            response.setStandardName(
                    classSection.getStandard().getStandardName()
            );
        }

        if (classSection.getAcademicYear() != null) {
            response.setAcademicYear(
                    classSection.getAcademicYear().getAcademicYear()
            );
        }

        response.setActive(student.isActive());

        return response;
    }
}
