package com.schooleERP.controller;

import com.schooleERP.dto.ClassSubjectRequest;
import com.schooleERP.dto.ClassSubjectResponse;
import com.schooleERP.service.ClassSubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-subjects")
public class ClassSubjectController {

    private final ClassSubjectService classSubjectService;

    public ClassSubjectController(
            ClassSubjectService classSubjectService
    ) {
        this.classSubjectService = classSubjectService;
    }

    // =========================
    // CREATE
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<ClassSubjectResponse> createClassSubject(
            @Valid @RequestBody ClassSubjectRequest request
    ) {

        ClassSubjectResponse response =
                classSubjectService.createClassSubject(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    // =========================
    // GET ALL
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ClassSubjectResponse>> getAllClassSubjects() {

        return ResponseEntity.ok(
                classSubjectService.getAllClassSubjects()
        );
    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<ClassSubjectResponse> getClassSubjectById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                classSubjectService.getClassSubjectById(id)
        );
    }

    // =========================
    // GET BY ACADEMIC YEAR
    // =========================

    @GetMapping("/academic-year/{academicYearId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ClassSubjectResponse>> getByAcademicYear(
            @PathVariable Long academicYearId
    ) {

        return ResponseEntity.ok(
                classSubjectService.getByAcademicYear(
                        academicYearId
                )
        );
    }

    // =========================
    // GET BY STANDARD
    // =========================

    @GetMapping("/standard/{standardId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ClassSubjectResponse>> getByStandard(
            @PathVariable Long standardId
    ) {

        return ResponseEntity.ok(
                classSubjectService.getByStandard(
                        standardId
                )
        );
    }

    // =========================
    // GET BY SUBJECT
    // =========================

    @GetMapping("/subject/{subjectId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ClassSubjectResponse>> getBySubject(
            @PathVariable Long subjectId
    ) {

        return ResponseEntity.ok(
                classSubjectService.getBySubject(
                        subjectId
                )
        );
    }

    // =========================
    // GET BY ACADEMIC YEAR + STANDARD
    // =========================

    @GetMapping("/academic-year/{academicYearId}/standard/{standardId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ClassSubjectResponse>>
    getByAcademicYearAndStandard(
            @PathVariable Long academicYearId,
            @PathVariable Long standardId
    ) {

        return ResponseEntity.ok(
                classSubjectService.getByAcademicYearAndStandard(
                        academicYearId,
                        standardId
                )
        );
    }

    // =========================
    // UPDATE
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<ClassSubjectResponse> updateClassSubject(
            @PathVariable Long id,
            @Valid @RequestBody ClassSubjectRequest request
    ) {

        return ResponseEntity.ok(
                classSubjectService.updateClassSubject(
                        id,
                        request
                )
        );
    }

    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteClassSubject(
            @PathVariable Long id
    ) {

        classSubjectService.deleteClassSubject(id);

        return ResponseEntity.noContent().build();
    }
}

