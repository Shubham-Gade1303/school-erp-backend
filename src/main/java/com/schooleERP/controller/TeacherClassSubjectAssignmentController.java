package com.schooleERP.controller;

import com.schooleERP.dto.TeacherClassSubjectAssignmentRequest;
import com.schooleERP.dto.TeacherClassSubjectAssignmentResponse;
import com.schooleERP.service.TeacherClassSubjectAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher-assignments")
public class TeacherClassSubjectAssignmentController {

    private final TeacherClassSubjectAssignmentService assignmentService;

    public TeacherClassSubjectAssignmentController(
            TeacherClassSubjectAssignmentService assignmentService
    ) {
        this.assignmentService = assignmentService;
    }

    // =========================
    // CREATE ASSIGNMENT
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<TeacherClassSubjectAssignmentResponse>
    createAssignment(
            @Valid @RequestBody
            TeacherClassSubjectAssignmentRequest request
    ) {

        TeacherClassSubjectAssignmentResponse response =
                assignmentService.createAssignment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================
    // GET ALL
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<
            List<TeacherClassSubjectAssignmentResponse>>
    getAllAssignments() {

        return ResponseEntity.ok(
                assignmentService.getAllAssignments()
        );
    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<TeacherClassSubjectAssignmentResponse>
    getAssignmentById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                assignmentService.getAssignmentById(id)
        );
    }

    // =========================
    // GET BY TEACHER
    // =========================

    @GetMapping("/teacher/{teacherId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<
            List<TeacherClassSubjectAssignmentResponse>>
    getByTeacher(
            @PathVariable Long teacherId
    ) {

        return ResponseEntity.ok(
                assignmentService.getByTeacher(teacherId)
        );
    }

    // =========================
    // GET BY CLASS SECTION
    // =========================

    @GetMapping("/class-section/{classSectionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<
            List<TeacherClassSubjectAssignmentResponse>>
    getByClassSection(
            @PathVariable Long classSectionId
    ) {

        return ResponseEntity.ok(
                assignmentService.getByClassSection(classSectionId)
        );
    }

    // =========================
    // GET BY CLASS SUBJECT
    // =========================

    @GetMapping("/class-subject/{classSubjectId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<
            List<TeacherClassSubjectAssignmentResponse>>
    getByClassSubject(
            @PathVariable Long classSubjectId
    ) {

        return ResponseEntity.ok(
                assignmentService.getByClassSubject(classSubjectId)
        );
    }

    // =========================
    // GET BY SECTION + SUBJECT
    // =========================

    @GetMapping(
            "/class-section/{classSectionId}/class-subject/{classSubjectId}"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<
            List<TeacherClassSubjectAssignmentResponse>>
    getByClassSectionAndClassSubject(
            @PathVariable Long classSectionId,
            @PathVariable Long classSubjectId
    ) {

        return ResponseEntity.ok(
                assignmentService.getByClassSectionAndClassSubject(
                        classSectionId,
                        classSubjectId
                )
        );
    }

    // =========================
    // UPDATE
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<TeacherClassSubjectAssignmentResponse>
    updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody
            TeacherClassSubjectAssignmentRequest request
    ) {

        return ResponseEntity.ok(
                assignmentService.updateAssignment(
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
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable Long id
    ) {

        assignmentService.deleteAssignment(id);

        return ResponseEntity.noContent().build();
    }
}
