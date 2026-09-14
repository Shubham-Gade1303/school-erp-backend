package com.schooleERP.controller;

import com.schooleERP.dto.StudentPerformanceSummaryRequest;
import com.schooleERP.dto.StudentPerformanceSummaryResponse;
import com.schooleERP.service.StudentPerformanceSummaryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-performance")
public class StudentPerformanceSummaryController {

    private final StudentPerformanceSummaryService summaryService;

    public StudentPerformanceSummaryController(
            StudentPerformanceSummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentPerformanceSummaryResponse> generateSummary(
            @Valid @RequestBody StudentPerformanceSummaryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(summaryService.generateSummary(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentPerformanceSummaryResponse>> getAllSummaries() {

        return ResponseEntity.ok(
                summaryService.getAllSummaries()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentPerformanceSummaryResponse> getSummaryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                summaryService.getSummaryById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentPerformanceSummaryResponse>> getSummariesByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                summaryService.getSummariesByStudent(studentId)
        );
    }

    @GetMapping("/exam/{examId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentPerformanceSummaryResponse>> getSummariesByExam(
            @PathVariable Long examId) {

        return ResponseEntity.ok(
                summaryService.getSummariesByExam(examId)
        );
    }

    @GetMapping("/student/{studentId}/exam/{examId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentPerformanceSummaryResponse>
    getSummaryByStudentAndExam(
            @PathVariable Long studentId,
            @PathVariable Long examId) {

        return ResponseEntity.ok(
                summaryService.getSummaryByStudentAndExam(
                        studentId,
                        examId
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentPerformanceSummaryResponse> updateSummary(
            @PathVariable Long id,
            @Valid @RequestBody StudentPerformanceSummaryRequest request) {

        return ResponseEntity.ok(
                summaryService.updateSummary(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteSummary(
            @PathVariable Long id) {

        summaryService.deleteSummary(id);

        return ResponseEntity.noContent().build();
    }
}
