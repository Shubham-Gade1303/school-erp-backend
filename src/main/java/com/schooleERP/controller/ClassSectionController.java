package com.schooleERP.controller;

import com.schooleERP.dto.ClassSectionRequest;
import com.schooleERP.dto.ClassSectionResponse;
import com.schooleERP.service.ClassSectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-sections")
public class ClassSectionController {

    private final ClassSectionService classSectionService;

    public ClassSectionController(
            ClassSectionService classSectionService) {
        this.classSectionService = classSectionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassSectionResponse> createClassSection(
            @Valid @RequestBody ClassSectionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(classSectionService.createClassSection(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ClassSectionResponse>> getAllClassSections() {

        return ResponseEntity.ok(
                classSectionService.getAllClassSections()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<ClassSectionResponse> getClassSectionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                classSectionService.getClassSectionById(id)
        );
    }

    @GetMapping("/academic-year/{academicYearId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ClassSectionResponse>>
    getByAcademicYear(
            @PathVariable Long academicYearId) {

        return ResponseEntity.ok(
                classSectionService
                        .getClassSectionsByAcademicYear(academicYearId)
        );
    }

    @GetMapping("/standard/{standardId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ClassSectionResponse>>
    getByStandard(
            @PathVariable Long standardId) {

        return ResponseEntity.ok(
                classSectionService
                        .getClassSectionsByStandard(standardId)
        );
    }

    @GetMapping(
            "/academic-year/{academicYearId}/standard/{standardId}"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ClassSectionResponse>>
    getByAcademicYearAndStandard(
            @PathVariable Long academicYearId,
            @PathVariable Long standardId) {

        return ResponseEntity.ok(
                classSectionService
                        .getClassSectionsByAcademicYearAndStandard(
                                academicYearId,
                                standardId
                        )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassSectionResponse> updateClassSection(
            @PathVariable Long id,
            @Valid @RequestBody ClassSectionRequest request) {

        return ResponseEntity.ok(
                classSectionService.updateClassSection(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClassSection(
            @PathVariable Long id) {

        classSectionService.deleteClassSection(id);

        return ResponseEntity.noContent().build();
    }
}

