package com.schooleERP.controller;

import com.schooleERP.dto.StudentDisciplinaryRecordRequest;
import com.schooleERP.dto.StudentDisciplinaryRecordResponse;
import com.schooleERP.service.StudentDisciplinaryRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinary-records")
public class StudentDisciplinaryRecordController {
    private final StudentDisciplinaryRecordService disciplinaryRecordService;
    public StudentDisciplinaryRecordController(
            StudentDisciplinaryRecordService disciplinaryRecordService
    ) {
        this.disciplinaryRecordService = disciplinaryRecordService;
    }

    // CREATE RECORD
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentDisciplinaryRecordResponse>
    createRecord(@Valid @RequestBody StudentDisciplinaryRecordRequest request) {
        StudentDisciplinaryRecordResponse response = disciplinaryRecordService.createRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL RECORDS
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentDisciplinaryRecordResponse>>
    getAllRecords() {
        return ResponseEntity.ok(disciplinaryRecordService.getAllRecords());
    }

    // GET RECORD BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentDisciplinaryRecordResponse>
    getRecordById(@PathVariable Long id) {

        return ResponseEntity.ok(disciplinaryRecordService.getRecordById(id));
    }

    // GET RECORDS BY STUDENT
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentDisciplinaryRecordResponse>>
    getRecordsByStudent(@PathVariable Long studentId) {

        return ResponseEntity.ok(disciplinaryRecordService.getRecordsByStudent(studentId));
    }

    // GET RECORDS BY INCIDENT TYPE
    @GetMapping("/incident-type/{incidentType}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentDisciplinaryRecordResponse>>
    getRecordsByIncidentType(@PathVariable String incidentType
    ) {
        return ResponseEntity.ok(disciplinaryRecordService.getRecordsByIncidentType(incidentType));
    }

    // GET RECORDS BY RESOLVED STATUS
    @GetMapping("/resolved/{resolved}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentDisciplinaryRecordResponse>>
    getRecordsByResolvedStatus(@PathVariable boolean resolved) {
        return ResponseEntity.ok(disciplinaryRecordService.getRecordsByResolvedStatus(resolved));
    }

    // GET BY STUDENT + RESOLVED STATUS

    @GetMapping("/student/{studentId}/resolved/{resolved}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentDisciplinaryRecordResponse>>
    getRecordsByStudentAndResolvedStatus(@PathVariable Long studentId, @PathVariable boolean resolved) {
        return ResponseEntity.ok(disciplinaryRecordService.getRecordsByStudentAndResolvedStatus(studentId, resolved));
    }

    // UPDATE RECORD
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentDisciplinaryRecordResponse>
    updateRecord(@PathVariable Long id, @Valid @RequestBody StudentDisciplinaryRecordRequest request
    ) {
        return ResponseEntity.ok(disciplinaryRecordService.updateRecord(id, request));
    }

    // DELETE RECORD
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        disciplinaryRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
