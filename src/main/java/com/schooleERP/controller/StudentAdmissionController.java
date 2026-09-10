package com.schooleERP.controller;

import com.schooleERP.dto.StudentAdmissionRequest;
import com.schooleERP.dto.StudentAdmissionResponse;
import com.schooleERP.service.StudentAdmissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-admissions")
public class StudentAdmissionController {

    private final StudentAdmissionService studentAdmissionService;

    public StudentAdmissionController(
            StudentAdmissionService studentAdmissionService
    ) {
        this.studentAdmissionService = studentAdmissionService;
    }

    // =========================
    // CREATE ADMISSION
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentAdmissionResponse> createAdmission(
            @Valid @RequestBody StudentAdmissionRequest request
    ) {

        StudentAdmissionResponse response =
                studentAdmissionService.createAdmission(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================
    // GET ALL ADMISSIONS
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAdmissionResponse>> getAllAdmissions() {

        return ResponseEntity.ok(
                studentAdmissionService.getAllAdmissions()
        );
    }

    // =========================
    // GET ADMISSION BY ID
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentAdmissionResponse> getAdmissionById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                studentAdmissionService.getAdmissionById(id)
        );
    }

    // =========================
    // GET ADMISSION BY STUDENT
    // =========================

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentAdmissionResponse> getAdmissionByStudentId(
            @PathVariable Long studentId
    ) {

        return ResponseEntity.ok(
                studentAdmissionService.getAdmissionByStudentId(studentId)
        );
    }

    // =========================
    // GET ADMISSIONS BY
    // ACADEMIC YEAR
    // =========================

    @GetMapping("/academic-year/{academicYearId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAdmissionResponse>>
    getAdmissionsByAcademicYear(
            @PathVariable Long academicYearId
    ) {

        return ResponseEntity.ok(
                studentAdmissionService
                        .getAdmissionsByAcademicYear(academicYearId)
        );
    }

    // =========================
    // GET ADMISSIONS BY STANDARD
    // =========================

    @GetMapping("/standard/{standardId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAdmissionResponse>>
    getAdmissionsByStandard(
            @PathVariable Long standardId
    ) {

        return ResponseEntity.ok(
                studentAdmissionService
                        .getAdmissionsByStandard(standardId)
        );
    }

    // =========================
    // GET ADMISSIONS BY
    // CLASS SECTION
    // =========================

    @GetMapping("/class-section/{classSectionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAdmissionResponse>>
    getAdmissionsByClassSection(
            @PathVariable Long classSectionId
    ) {

        return ResponseEntity.ok(
                studentAdmissionService
                        .getAdmissionsByClassSection(classSectionId)
        );
    }

    // =========================
    // GET ADMISSIONS BY STATUS
    // =========================

    @GetMapping("/status/{admissionStatus}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAdmissionResponse>>
    getAdmissionsByStatus(
            @PathVariable String admissionStatus
    ) {

        return ResponseEntity.ok(
                studentAdmissionService
                        .getAdmissionsByStatus(admissionStatus)
        );
    }

    // =========================
    // UPDATE ADMISSION
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentAdmissionResponse> updateAdmission(
            @PathVariable Long id,
            @Valid @RequestBody StudentAdmissionRequest request
    ) {

        return ResponseEntity.ok(
                studentAdmissionService.updateAdmission(id, request)
        );
    }

    // =========================
    // DELETE ADMISSION
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteAdmission(
            @PathVariable Long id
    ) {

        studentAdmissionService.deleteAdmission(id);

        return ResponseEntity.noContent().build();
    }
}

