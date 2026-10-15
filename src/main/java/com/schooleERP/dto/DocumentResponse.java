package com.schooleERP.dto;

import com.schooleERP.enums.DocumentType;
import java.time.LocalDateTime;

public class DocumentResponse {

    private Long id;
    private Long studentId;
    private DocumentType documentType;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private LocalDateTime uploadedAt;

    public DocumentResponse() {
    }

    public DocumentResponse(
            Long id,
            Long studentId,
            DocumentType documentType,
            String fileName,
            String contentType,
            Long fileSize,
            LocalDateTime uploadedAt) {
        this.id = id;
        this.studentId = studentId;
        this.documentType = documentType;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.uploadedAt = uploadedAt;
    }

    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public DocumentType getDocumentType() { return documentType; }
    public String getFileName() { return fileName; }
    public String getContentType() { return contentType; }
    public Long getFileSize() { return fileSize; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }

    public void setId(Long id) { this.id = id; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}