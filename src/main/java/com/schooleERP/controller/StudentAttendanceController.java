package com.schooleERP.controller;

import com.schooleERP.dto.StudentAttendanceRequest;
import com.schooleERP.dto.StudentAttendanceResponse;
import com.schooleERP.service.StudentAttendanceService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class StudentAttendanceController {

    private final StudentAttendanceService studentAttendanceService;

    public StudentAttendanceController(
            StudentAttendanceService studentAttendanceService
    ) {
        this.studentAttendanceService = studentAttendanceService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentAttendanceResponse> markAttendance(
            @Valid @RequestBody StudentAttendanceRequest request
    ) {
        StudentAttendanceResponse response =
                studentAttendanceService.markAttendance(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAttendanceResponse>> getAllAttendance() {

        return ResponseEntity.ok(
                studentAttendanceService.getAllAttendance()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentAttendanceResponse> getAttendanceById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                studentAttendanceService.getAttendanceById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAttendanceResponse>>
    getAttendanceByStudent(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(
                studentAttendanceService.getAttendanceByStudent(studentId)
        );
    }

    @GetMapping("/date/{attendanceDate}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAttendanceResponse>>
    getAttendanceByDate(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate attendanceDate
    ) {
        return ResponseEntity.ok(
                studentAttendanceService.getAttendanceByDate(attendanceDate)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentAttendanceResponse> updateAttendance(
            @PathVariable Long id,
            @Valid @RequestBody StudentAttendanceRequest request
    ) {
        return ResponseEntity.ok(
                studentAttendanceService.updateAttendance(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable Long id
    ) {
        studentAttendanceService.deleteAttendance(id);

        return ResponseEntity.noContent().build();
    }
}

