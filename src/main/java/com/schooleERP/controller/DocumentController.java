package com.schooleERP.controller;

import com.schooleERP.dto.DocumentRequest;
import com.schooleERP.dto.DocumentResponse;
import com.schooleERP.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<DocumentResponse> createDocument(
            @Valid @RequestBody DocumentRequest request
    ) {
        DocumentResponse response =
                documentService.createDocument(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {

        return ResponseEntity.ok(
                documentService.getAllDocuments()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<DocumentResponse> getDocumentById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                documentService.getDocumentById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<DocumentResponse>> getDocumentsByStudent(
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(
                documentService.getDocumentsByStudent(studentId)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<DocumentResponse> updateDocument(
            @PathVariable Long id,
            @Valid @RequestBody DocumentRequest request
    ) {
        return ResponseEntity.ok(
                documentService.updateDocument(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long id
    ) {
        documentService.deleteDocument(id);

        return ResponseEntity.noContent().build();
    }

}

