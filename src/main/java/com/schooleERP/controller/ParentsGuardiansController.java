package com.schooleERP.controller;

import com.schooleERP.dto.ParentsGuardiansRequest;
import com.schooleERP.dto.ParentsGuardiansResponse;
import com.schooleERP.service.ParentsGuardiansService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents-guardians")
public class ParentsGuardiansController {

    private final ParentsGuardiansService parentsGuardiansService;

    public ParentsGuardiansController(
            ParentsGuardiansService parentsGuardiansService
    ) {
        this.parentsGuardiansService = parentsGuardiansService;
    }

    // =========================
    // CREATE PARENT / GUARDIAN
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<ParentsGuardiansResponse> createParentGuardian(
            @Valid @RequestBody ParentsGuardiansRequest request
    ) {

        ParentsGuardiansResponse response =
                parentsGuardiansService.createParentGuardian(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================
    // GET ALL
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ParentsGuardiansResponse>>
    getAllParentsGuardians() {

        return ResponseEntity.ok(
                parentsGuardiansService.getAllParentsGuardians()
        );
    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<ParentsGuardiansResponse>
    getParentGuardianById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                parentsGuardiansService
                        .getParentGuardianById(id)
        );
    }

    // =========================
    // GET BY STUDENT
    // =========================

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ParentsGuardiansResponse>>
    getParentsGuardiansByStudent(
            @PathVariable Long studentId
    ) {

        return ResponseEntity.ok(
                parentsGuardiansService
                        .getParentsGuardiansByStudent(studentId)
        );
    }

    // =========================
    // UPDATE
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<ParentsGuardiansResponse>
    updateParentGuardian(
            @PathVariable Long id,
            @Valid @RequestBody ParentsGuardiansRequest request
    ) {

        return ResponseEntity.ok(
                parentsGuardiansService
                        .updateParentGuardian(id, request)
        );
    }

    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteParentGuardian(
            @PathVariable Long id
    ) {

        parentsGuardiansService.deleteParentGuardian(id);

        return ResponseEntity.noContent().build();
    }
}
