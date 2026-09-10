package com.schooleERP.controller;

import com.schooleERP.dto.StudentRequest;
import com.schooleERP.dto.StudentResponse;
import com.schooleERP.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // =========================
    // CREATE STUDENT
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentResponse> createStudent(
            @Valid @RequestBody StudentRequest request
    ) {

        StudentResponse response =
                studentService.createStudent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================
    // GET ALL STUDENTS
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {

        return ResponseEntity.ok(
                studentService.getAllStudents()
        );
    }

    // =========================
    // GET STUDENT BY ID
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                studentService.getStudentById(id)
        );
    }

    // =========================
    // GET STUDENT BY
    // ADMISSION NUMBER
    // =========================

    @GetMapping("/admission/{admissionNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentResponse> getStudentByAdmissionNumber(
            @PathVariable String admissionNumber
    ) {

        return ResponseEntity.ok(
                studentService.getStudentByAdmissionNumber(
                        admissionNumber
                )
        );
    }

    // =========================
    // GET STUDENTS BY
    // CLASS SECTION
    // =========================

    @GetMapping("/class-section/{classSectionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentResponse>> getStudentsByClassSection(
            @PathVariable Long classSectionId
    ) {

        return ResponseEntity.ok(
                studentService.getStudentsByClassSection(
                        classSectionId
                )
        );
    }

    // =========================
    // GET ACTIVE STUDENTS
    // =========================

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentResponse>> getActiveStudents() {

        return ResponseEntity.ok(
                studentService.getActiveStudents()
        );
    }

    // =========================
    // UPDATE STUDENT
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request
    ) {

        return ResponseEntity.ok(
                studentService.updateStudent(id, request)
        );
    }

    // =========================
    // DELETE STUDENT
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id
    ) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}
