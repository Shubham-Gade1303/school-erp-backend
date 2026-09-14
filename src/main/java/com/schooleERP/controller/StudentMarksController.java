package com.schooleERP.controller;

import com.schooleERP.dto.StudentMarksRequest;
import com.schooleERP.dto.StudentMarksResponse;
import com.schooleERP.service.StudentMarksService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-marks")
public class StudentMarksController {

    private final StudentMarksService studentMarksService;

    public StudentMarksController(StudentMarksService studentMarksService) {
        this.studentMarksService = studentMarksService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentMarksResponse> addMarks(@Valid @RequestBody StudentMarksRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(studentMarksService.addMarks(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentMarksResponse>> getAllMarks() {
        return ResponseEntity.ok(studentMarksService.getAllMarks());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentMarksResponse> getMarksById(@PathVariable Long id) {
        return ResponseEntity.ok(studentMarksService.getMarksById(id));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentMarksResponse>> getMarksByStudent(@PathVariable Long studentId) {

        return ResponseEntity.ok(studentMarksService.getMarksByStudent(studentId));
    }

    @GetMapping("/exam/{examId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentMarksResponse>> getMarksByExam(@PathVariable Long examId) {

        return ResponseEntity.ok(studentMarksService.getMarksByExam(examId)
        );
    }

    @GetMapping("/subject/{subjectId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentMarksResponse>> getMarksBySubject(@PathVariable Long subjectId) {

        return ResponseEntity.ok(studentMarksService.getMarksBySubject(subjectId)
        );
    }

    @GetMapping("/student/{studentId}/exam/{examId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentMarksResponse>> getMarksByStudentAndExam(@PathVariable Long studentId, @PathVariable Long examId) {
        return ResponseEntity.ok(studentMarksService.getMarksByStudentAndExam(studentId, examId));
    }

    @GetMapping("/exam/{examId}/subject/{subjectId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentMarksResponse>> getMarksByExamAndSubject(@PathVariable Long examId, @PathVariable Long subjectId) {
        return ResponseEntity.ok(studentMarksService.getMarksByExamAndSubject(examId, subjectId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentMarksResponse> updateMarks(@PathVariable Long id, @Valid @RequestBody StudentMarksRequest request) {
        return ResponseEntity.ok(studentMarksService.updateMarks(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteMarks(@PathVariable Long id) {
        studentMarksService.deleteMarks(id);
        return ResponseEntity.noContent().build();
    }
}

