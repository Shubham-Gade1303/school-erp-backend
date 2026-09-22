package com.schooleERP.controller;

import com.schooleERP.dto.StudentFeesRequest;
import com.schooleERP.dto.StudentFeesResponse;
import com.schooleERP.service.StudentFeesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-fees")
public class StudentFeesController {

    private final StudentFeesService studentFeesService;

    public StudentFeesController(
            StudentFeesService studentFeesService) {
        this.studentFeesService = studentFeesService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentFeesResponse> createFee(
            @Valid @RequestBody StudentFeesRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentFeesService.createFee(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentFeesResponse>> getAllFees() {

        return ResponseEntity.ok(
                studentFeesService.getAllFees()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentFeesResponse> getFeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                studentFeesService.getFeeById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentFeesResponse>> getFeesByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                studentFeesService.getFeesByStudent(studentId)
        );
    }

    @GetMapping("/academic-year/{academicYearId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentFeesResponse>> getFeesByAcademicYear(
            @PathVariable Long academicYearId) {

        return ResponseEntity.ok(
                studentFeesService.getFeesByAcademicYear(academicYearId)
        );
    }

    @GetMapping("/student/{studentId}/academic-year/{academicYearId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentFeesResponse>>
    getFeesByStudentAndAcademicYear(
            @PathVariable Long studentId,
            @PathVariable Long academicYearId) {

        return ResponseEntity.ok(
                studentFeesService.getFeesByStudentAndAcademicYear(
                        studentId,
                        academicYearId
                )
        );
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentFeesResponse>> getFeesByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                studentFeesService.getFeesByStatus(status)
        );
    }

    @GetMapping("/type/{feeType}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentFeesResponse>> getFeesByType(
            @PathVariable String feeType) {

        return ResponseEntity.ok(studentFeesService.getFeesByType(feeType)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentFeesResponse> updateFee(
            @PathVariable Long id, @Valid @RequestBody StudentFeesRequest request) {

        return ResponseEntity.ok(studentFeesService.updateFee(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteFee(
            @PathVariable Long id) {

        studentFeesService.deleteFee(id);

        return ResponseEntity.noContent().build();
    }
}
