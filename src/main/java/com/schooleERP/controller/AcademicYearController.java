package com.schooleERP.controller;

import com.schooleERP.dto.AcademicYearRequest;
import com.schooleERP.dto.AcademicYearResponse;
import com.schooleERP.service.AcademicYearService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-years")
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    public AcademicYearController(
            AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AcademicYearResponse> createAcademicYear(
            @Valid @RequestBody AcademicYearRequest request) {

        AcademicYearResponse response =
                academicYearService.createAcademicYear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<AcademicYearResponse>> getAllAcademicYears() {

        List<AcademicYearResponse> response =
                academicYearService.getAllAcademicYears();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<AcademicYearResponse> getAcademicYearById(
            @PathVariable Long id) {

        AcademicYearResponse response =
                academicYearService.getAcademicYearById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<AcademicYearResponse> getActiveAcademicYear() {

        AcademicYearResponse response =
                academicYearService.getActiveAcademicYear();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AcademicYearResponse> updateAcademicYear(
            @PathVariable Long id,
            @Valid @RequestBody AcademicYearRequest request) {

        AcademicYearResponse response =
                academicYearService.updateAcademicYear(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAcademicYear(
            @PathVariable Long id) {

        academicYearService.deleteAcademicYear(id);

        return ResponseEntity.noContent().build();
    }
}

