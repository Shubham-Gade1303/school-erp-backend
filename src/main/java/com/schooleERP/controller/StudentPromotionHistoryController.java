package com.schooleERP.controller;

import com.schooleERP.dto.StudentPromotionHistoryRequest;
import com.schooleERP.dto.StudentPromotionHistoryResponse;
import com.schooleERP.service.StudentPromotionHistoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-promotion-history")
public class StudentPromotionHistoryController {

    private final StudentPromotionHistoryService promotionHistoryService;

    public StudentPromotionHistoryController(
            StudentPromotionHistoryService promotionHistoryService) {
        this.promotionHistoryService = promotionHistoryService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentPromotionHistoryResponse> createPromotion(
            @Valid @RequestBody StudentPromotionHistoryRequest request) {

        StudentPromotionHistoryResponse response =
                promotionHistoryService.createPromotion(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentPromotionHistoryResponse>>
    getAllPromotions() {

        return ResponseEntity.ok(
                promotionHistoryService.getAllPromotions()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentPromotionHistoryResponse>
    getPromotionById(@PathVariable Long id) {

        return ResponseEntity.ok(
                promotionHistoryService.getPromotionById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentPromotionHistoryResponse>>
    getPromotionsByStudent(@PathVariable Long studentId) {

        return ResponseEntity.ok(
                promotionHistoryService.getPromotionsByStudent(studentId)
        );
    }

    @GetMapping("/from-academic-year/{academicYearId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentPromotionHistoryResponse>>
    getPromotionsByFromAcademicYear(
            @PathVariable Long academicYearId) {

        return ResponseEntity.ok(
                promotionHistoryService
                        .getPromotionsByFromAcademicYear(academicYearId)
        );
    }

    @GetMapping("/to-academic-year/{academicYearId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentPromotionHistoryResponse>>
    getPromotionsByToAcademicYear(
            @PathVariable Long academicYearId) {

        return ResponseEntity.ok(
                promotionHistoryService
                        .getPromotionsByToAcademicYear(academicYearId)
        );
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentPromotionHistoryResponse>>
    getPromotionsByStatus(@PathVariable String status) {

        return ResponseEntity.ok(
                promotionHistoryService.getPromotionsByStatus(status)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentPromotionHistoryResponse>
    updatePromotion(
            @PathVariable Long id,
            @Valid @RequestBody StudentPromotionHistoryRequest request) {

        return ResponseEntity.ok(
                promotionHistoryService.updatePromotion(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePromotion(
            @PathVariable Long id) {

        promotionHistoryService.deletePromotion(id);

        return ResponseEntity.noContent().build();
    }
}
