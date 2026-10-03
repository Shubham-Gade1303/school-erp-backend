package com.schooleERP.controller;

import com.schooleERP.dto.StudentActivitiesParticipationRequest;
import com.schooleERP.dto.StudentActivitiesParticipationResponse;
import com.schooleERP.service.StudentActivitiesParticipationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/student-activities-participation")
public class StudentActivitiesParticipationController {

    private final StudentActivitiesParticipationService participationService;

    public StudentActivitiesParticipationController(
            StudentActivitiesParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentActivitiesParticipationResponse>
    createParticipation(
            @Valid @RequestBody StudentActivitiesParticipationRequest request) {

        StudentActivitiesParticipationResponse response =
                participationService.createParticipation(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentActivitiesParticipationResponse>>
    getAllParticipations() {

        return ResponseEntity.ok(
                participationService.getAllParticipations()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentActivitiesParticipationResponse>
    getParticipationById(@PathVariable Long id) {

        return ResponseEntity.ok(
                participationService.getParticipationById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentActivitiesParticipationResponse>>
    getParticipationsByStudent(@PathVariable Long studentId) {

        return ResponseEntity.ok(
                participationService.getParticipationsByStudent(studentId)
        );
    }

    @GetMapping("/activity/{activityId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentActivitiesParticipationResponse>>
    getParticipationsByActivity(@PathVariable Long activityId) {

        return ResponseEntity.ok(
                participationService.getParticipationsByActivity(activityId)
        );
    }

    @GetMapping("/date/{participationDate}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentActivitiesParticipationResponse>>
    getParticipationsByDate(
            @PathVariable LocalDate participationDate) {

        return ResponseEntity.ok(
                participationService.getParticipationsByDate(
                        participationDate)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentActivitiesParticipationResponse>
    updateParticipation(
            @PathVariable Long id,
            @Valid @RequestBody StudentActivitiesParticipationRequest request) {

        return ResponseEntity.ok(
                participationService.updateParticipation(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteParticipation(
            @PathVariable Long id) {

        participationService.deleteParticipation(id);

        return ResponseEntity.noContent().build();
    }
}
