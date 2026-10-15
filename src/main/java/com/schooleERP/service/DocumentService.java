package com.schooleERP.service;

import com.schooleERP.dto.DocumentRequest;
import com.schooleERP.dto.DocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    DocumentResponse createDocument(DocumentRequest request, MultipartFile file);

    List<DocumentResponse> getAllDocuments();

    DocumentResponse getDocumentById(Long id);

    List<DocumentResponse> getDocumentsByStudent(Long studentId);

    DocumentResponse updateDocument(Long id, DocumentRequest request);

    DocumentResponse replaceDocumentFile(Long id, MultipartFile file);

    DocumentDownload downloadDocument(Long id);

    void deleteDocument(Long id);

    record DocumentDownload(String fileName, String contentType, byte[] fileData) {
    }
}