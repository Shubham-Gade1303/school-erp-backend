package com.schooleERP.controller;

import com.schooleERP.dto.TeacherRequest;
import com.schooleERP.dto.TeacherResponse;
import com.schooleERP.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // =========================
    // CREATE TEACHER
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<TeacherResponse> createTeacher(
            @Valid @RequestBody TeacherRequest request
    ) {

        TeacherResponse response =
                teacherService.createTeacher(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================
    // GET ALL TEACHERS
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {

        return ResponseEntity.ok(
                teacherService.getAllTeachers()
        );
    }

    // =========================
    // GET TEACHER BY ID
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<TeacherResponse> getTeacherById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                teacherService.getTeacherById(id)
        );
    }

    // =========================
    // GET TEACHER BY USER ID
    // =========================

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<TeacherResponse> getTeacherByUserId(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                teacherService.getTeacherByUserId(userId)
        );
    }

    // =========================
    // UPDATE TEACHER
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<TeacherResponse> updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherRequest request
    ) {

        return ResponseEntity.ok(
                teacherService.updateTeacher(id, request)
        );
    }

    // =========================
    // DELETE TEACHER
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteTeacher(
            @PathVariable Long id
    ) {

        teacherService.deleteTeacher(id);

        return ResponseEntity.noContent().build();
    }
}

