package com.schooleERP.controller;

import com.schooleERP.dto.ExamRequest;
import com.schooleERP.dto.ExamResponse;
import com.schooleERP.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<ExamResponse> createExam(
            @Valid @RequestBody ExamRequest request
    ) {
        ExamResponse response = examService.createExam(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ExamResponse>> getAllExams() {

        return ResponseEntity.ok(
                examService.getAllExams()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<ExamResponse> getExamById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                examService.getExamById(id)
        );
    }

    @GetMapping("/academic-year/{academicYearId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ExamResponse>> getExamsByAcademicYear(
            @PathVariable Long academicYearId
    ) {
        return ResponseEntity.ok(
                examService.getExamsByAcademicYear(academicYearId)
        );
    }

    @GetMapping("/standard/{standardId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ExamResponse>> getExamsByStandard(
            @PathVariable Long standardId
    ) {
        return ResponseEntity.ok(
                examService.getExamsByStandard(standardId)
        );
    }

    @GetMapping("/academic-year/{academicYearId}/standard/{standardId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ExamResponse>>
    getExamsByAcademicYearAndStandard(
            @PathVariable Long academicYearId,
            @PathVariable Long standardId
    ) {
        return ResponseEntity.ok(
                examService.getExamsByAcademicYearAndStandard(
                        academicYearId,
                        standardId
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<ExamResponse> updateExam(
            @PathVariable Long id,
            @Valid @RequestBody ExamRequest request
    ) {
        return ResponseEntity.ok(
                examService.updateExam(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteExam(
            @PathVariable Long id
    ) {
        examService.deleteExam(id);

        return ResponseEntity.noContent().build();
    }
}
