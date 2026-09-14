package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentAttendanceRequest;
import com.schooleERP.dto.StudentAttendanceResponse;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentAttendance;
import com.schooleERP.repository.StudentAttendanceRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.StudentAttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StudentAttendanceServiceImpl implements StudentAttendanceService {

    private final StudentAttendanceRepo studentAttendanceRepo;
    private final StudentRepo studentRepo;

    public StudentAttendanceServiceImpl(
            StudentAttendanceRepo studentAttendanceRepo,
            StudentRepo studentRepo
    ) {
        this.studentAttendanceRepo = studentAttendanceRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public StudentAttendanceResponse markAttendance(
            StudentAttendanceRequest request
    ) {

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found with id: " + request.getStudentId()
                        )
                );

        if (studentAttendanceRepo.existsByStudentIdAndAttendanceDate(
                request.getStudentId(),
                request.getAttendanceDate()
        )) {
            throw new IllegalArgumentException(
                    "Attendance already exists for student id: "
                            + request.getStudentId()
                            + " on "
                            + request.getAttendanceDate()
            );
        }

        StudentAttendance attendance = new StudentAttendance();

        attendance.setStudent(student);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setStatus(request.getStatus());
        attendance.setRemarks(request.getRemarks());

        return mapToResponse(studentAttendanceRepo.save(attendance));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentAttendanceResponse> getAllAttendance() {

        return studentAttendanceRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentAttendanceResponse getAttendanceById(Long id) {

        StudentAttendance attendance =
                studentAttendanceRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Attendance not found with id: " + id
                                )
                        );

        return mapToResponse(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentAttendanceResponse> getAttendanceByStudent(
            Long studentId
    ) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException(
                    "Student not found with id: " + studentId
            );
        }

        return studentAttendanceRepo.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentAttendanceResponse> getAttendanceByDate(
            LocalDate attendanceDate
    ) {

        return studentAttendanceRepo.findByAttendanceDate(attendanceDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentAttendanceResponse updateAttendance(
            Long id,
            StudentAttendanceRequest request
    ) {

        StudentAttendance attendance =
                studentAttendanceRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Attendance not found with id: " + id
                                )
                        );

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found with id: "
                                        + request.getStudentId()
                        )
                );

        StudentAttendance existingAttendance =
                studentAttendanceRepo
                        .findByStudentIdAndAttendanceDate(
                                request.getStudentId(),
                                request.getAttendanceDate()
                        )
                        .stream()
                        .findFirst()
                        .orElse(null);

        if (existingAttendance != null
                && !existingAttendance.getId().equals(id)) {

            throw new IllegalArgumentException(
                    "Attendance already exists for student id: "
                            + request.getStudentId()
                            + " on "
                            + request.getAttendanceDate()
            );
        }

        attendance.setStudent(student);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setStatus(request.getStatus());
        attendance.setRemarks(request.getRemarks());

        return mapToResponse(
                studentAttendanceRepo.save(attendance)
        );
    }

    @Override
    public void deleteAttendance(Long id) {

        if (!studentAttendanceRepo.existsById(id)) {
            throw new IllegalArgumentException(
                    "Attendance not found with id: " + id
            );
        }

        studentAttendanceRepo.deleteById(id);
    }

    private StudentAttendanceResponse mapToResponse(
            StudentAttendance attendance
    ) {

        Student student = attendance.getStudent();

        StudentAttendanceResponse response =
                new StudentAttendanceResponse();

        response.setId(attendance.getId());

        response.setStudentId(student.getId());
        response.setAdmissionNumber(student.getAdmissionNumber());

        String studentName =
                student.getFirstName()
                        + " "
                        + (student.getMiddleName() != null
                        ? student.getMiddleName() + " "
                        : "")
                        + student.getLastName();

        response.setStudentName(studentName.trim());

        response.setAttendanceDate(
                attendance.getAttendanceDate()
        );
        response.setStatus(attendance.getStatus());
        response.setRemarks(attendance.getRemarks());

        return response;
    }
}

