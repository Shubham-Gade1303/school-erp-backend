package com.schooleERP.controller;

import com.schooleERP.dto.StandardRequest;
import com.schooleERP.dto.StandardResponse;
import com.schooleERP.service.StandardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/standards")
public class StandardController {

    private final StandardService standardService;

    public StandardController(
            StandardService standardService) {
        this.standardService = standardService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> createStandard(
            @Valid @RequestBody StandardRequest request) {

        StandardResponse response =
                standardService.createStandard(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StandardResponse>> getAllStandards() {

        List<StandardResponse> response =
                standardService.getAllStandards();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StandardResponse> getStandardById(
            @PathVariable Long id) {

        StandardResponse response =
                standardService.getStandardById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> updateStandard(
            @PathVariable Long id,
            @Valid @RequestBody StandardRequest request) {

        StandardResponse response =
                standardService.updateStandard(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStandard(
            @PathVariable Long id) {

        standardService.deleteStandard(id);

        return ResponseEntity.noContent().build();
    }
}

