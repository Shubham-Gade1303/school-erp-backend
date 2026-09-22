package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentFeesRequest;
import com.schooleERP.dto.StudentFeesResponse;
import com.schooleERP.entity.AcademicYear;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentFees;
import com.schooleERP.repository.AcademicYearRepo;
import com.schooleERP.repository.StudentFeesRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.StudentFeesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentFeesServiceImpl implements StudentFeesService {

    private final StudentFeesRepo studentFeesRepo;
    private final StudentRepo studentRepo;
    private final AcademicYearRepo academicYearRepo;

    public StudentFeesServiceImpl(
            StudentFeesRepo studentFeesRepo,
            StudentRepo studentRepo,
            AcademicYearRepo academicYearRepo) {

        this.studentFeesRepo = studentFeesRepo;
        this.studentRepo = studentRepo;
        this.academicYearRepo = academicYearRepo;
    }

    @Override
    public StudentFeesResponse createFee(StudentFeesRequest request) {

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found with id: " + request.getStudentId()));

        AcademicYear academicYear = academicYearRepo
                .findById(request.getAcademicYearId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Academic year not found with id: " + request.getAcademicYearId()));

        boolean exists =
                studentFeesRepo
                        .existsByStudentIdAndAcademicYearIdAndFeeType(
                                request.getStudentId(), request.getAcademicYearId(), request.getFeeType());

        if (exists) {
            throw new IllegalArgumentException("Fee already exists for this student, academic year and fee type");
        }

        StudentFees studentFees = new StudentFees();

        studentFees.setStudent(student);
        studentFees.setAcademicYear(academicYear);
        studentFees.setFeeType(request.getFeeType());
        studentFees.setAmount(request.getAmount());
        studentFees.setDueDate(request.getDueDate());
        studentFees.setStatus(request.getStatus());
        studentFees.setRemarks(request.getRemarks());

        StudentFees savedFee = studentFeesRepo.save(studentFees);

        return mapToResponse(savedFee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentFeesResponse> getAllFees() {

        return studentFeesRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentFeesResponse getFeeById(Long id) {

        StudentFees studentFees = studentFeesRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Fee not found with id: " + id));

        return mapToResponse(studentFees);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentFeesResponse> getFeesByStudent(
            Long studentId) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }

        return studentFeesRepo.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentFeesResponse> getFeesByAcademicYear(
            Long academicYearId) {

        if (!academicYearRepo.existsById(academicYearId)) {
            throw new IllegalArgumentException("Academic year not found with id: " + academicYearId);
        }

        return studentFeesRepo.findByAcademicYearId(academicYearId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentFeesResponse>
    getFeesByStudentAndAcademicYear(
            Long studentId,
            Long academicYearId) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }

        if (!academicYearRepo.existsById(academicYearId)) {
            throw new IllegalArgumentException("Academic year not found with id: " + academicYearId);
        }

        return studentFeesRepo
                .findByStudentIdAndAcademicYearId(
                        studentId,
                        academicYearId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentFeesResponse> getFeesByStatus(
            String status) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Fee status cannot be empty");
        }

        return studentFeesRepo.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentFeesResponse> getFeesByType(
            String feeType) {

        if (feeType == null || feeType.isBlank()) {
            throw new IllegalArgumentException("Fee type cannot be empty");
        }

        return studentFeesRepo.findByFeeType(feeType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentFeesResponse updateFee(
            Long id,
            StudentFeesRequest request) {

        StudentFees existingFee = studentFeesRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fee not found with id: " + id));

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found with id: " + request.getStudentId()));

        AcademicYear academicYear = academicYearRepo
                .findById(request.getAcademicYearId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Academic year not found with id: " + request.getAcademicYearId()));

        boolean duplicate =
                studentFeesRepo
                        .existsByStudentIdAndAcademicYearIdAndFeeType(
                                request.getStudentId(), request.getAcademicYearId(), request.getFeeType());

        if (duplicate
                && !(existingFee.getStudent().getId()
                .equals(request.getStudentId())
                && existingFee.getAcademicYear().getId()
                .equals(request.getAcademicYearId())
                && existingFee.getFeeType()
                .equalsIgnoreCase(request.getFeeType()))) {

            throw new IllegalArgumentException("Fee already exists for this student, academic year and fee type");
        }

        existingFee.setStudent(student);
        existingFee.setAcademicYear(academicYear);
        existingFee.setFeeType(request.getFeeType());
        existingFee.setAmount(request.getAmount());
        existingFee.setDueDate(request.getDueDate());
        existingFee.setStatus(request.getStatus());
        existingFee.setRemarks(request.getRemarks());

        StudentFees updatedFee = studentFeesRepo.save(existingFee);
        return mapToResponse(updatedFee);
    }

    @Override
    public void deleteFee(Long id) {

        StudentFees studentFees = studentFeesRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fee not found with id: " + id));

        studentFeesRepo.delete(studentFees);
    }

    private StudentFeesResponse mapToResponse(
            StudentFees studentFees) {

        Student student = studentFees.getStudent();
        AcademicYear academicYear = studentFees.getAcademicYear();

        String studentName = student.getFirstName() + " " + student.getLastName();

        StudentFeesResponse response = new StudentFeesResponse();
        response.setId(studentFees.getId());
        response.setStudentId(student.getId());
        response.setAdmissionNumber(student.getAdmissionNumber());
        response.setStudentName(studentName);
        response.setAcademicYearId(academicYear.getId());
        response.setAcademicYear(academicYear.getAcademicYear());
        response.setFeeType(studentFees.getFeeType());
        response.setAmount(studentFees.getAmount());
        response.setDueDate(studentFees.getDueDate());
        response.setStatus(studentFees.getStatus());
        response.setRemarks(studentFees.getRemarks());

        return response;
    }
}