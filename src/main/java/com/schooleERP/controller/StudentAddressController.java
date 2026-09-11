package com.schooleERP.controller;

import com.schooleERP.dto.StudentAddressRequest;
import com.schooleERP.dto.StudentAddressResponse;
import com.schooleERP.service.StudentAddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-addresses")
public class StudentAddressController {

    private final StudentAddressService studentAddressService;

    public StudentAddressController(
            StudentAddressService studentAddressService
    ) {
        this.studentAddressService = studentAddressService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentAddressResponse> createAddress(
            @Valid @RequestBody StudentAddressRequest request
    ) {
        StudentAddressResponse response =
                studentAddressService.createAddress(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAddressResponse>> getAllAddresses() {

        return ResponseEntity.ok(
                studentAddressService.getAllAddresses()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentAddressResponse> getAddressById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                studentAddressService.getAddressById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentAddressResponse> getAddressByStudent(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(
                studentAddressService.getAddressByStudent(studentId)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<StudentAddressResponse> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody StudentAddressRequest request
    ) {
        return ResponseEntity.ok(
                studentAddressService.updateAddress(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long id
    ) {
        studentAddressService.deleteAddress(id);

        return ResponseEntity.noContent().build();
    }
}

