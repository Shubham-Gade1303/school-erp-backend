package com.schooleERP.dto;

import com.schooleERP.enums.DocumentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DocumentRequest {

    @NotNull(message = "Student ID is required")
    @Positive(message = "Invalid student ID")
    private Long studentId;

    @NotNull(message = "Document type is required")
    private DocumentType documentType;

    public DocumentRequest() {
    }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public DocumentType getDocumentType() { return documentType; }
    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}