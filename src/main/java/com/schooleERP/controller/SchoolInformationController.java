package com.schooleERP.controller;

import com.schooleERP.dto.SchoolInformationRequest;
import com.schooleERP.dto.SchoolInformationResponse;
import com.schooleERP.service.SchoolInformationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school")
public class SchoolInformationController {

    private final SchoolInformationService schoolInformationService;

    public SchoolInformationController(
            SchoolInformationService schoolInformationService) {

        this.schoolInformationService = schoolInformationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SchoolInformationResponse> createSchool(
            @Valid @RequestBody SchoolInformationRequest request) {

        SchoolInformationResponse response =
                schoolInformationService.createSchool(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<SchoolInformationResponse>> getAllSchools() {

        List<SchoolInformationResponse> response =
                schoolInformationService.getAllSchools();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<SchoolInformationResponse> getSchoolById(
            @PathVariable Long id) {

        SchoolInformationResponse response =
                schoolInformationService.getSchoolById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SchoolInformationResponse> updateSchool(
            @PathVariable Long id,
            @Valid @RequestBody SchoolInformationRequest request) {

        SchoolInformationResponse response =
                schoolInformationService.updateSchool(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSchool(
            @PathVariable Long id) {

        schoolInformationService.deleteSchool(id);

        return ResponseEntity.noContent().build();
    }
}

