
package com.schooleERP.service;

import com.schooleERP.dto.DocumentRequest;
import com.schooleERP.dto.DocumentResponse;

import java.util.List;

public interface DocumentService {

    DocumentResponse createDocument(DocumentRequest request);

    List<DocumentResponse> getAllDocuments();

    DocumentResponse getDocumentById(Long id);

    List<DocumentResponse> getDocumentsByStudent(Long studentId);

    DocumentResponse updateDocument(Long id, DocumentRequest request);

    void deleteDocument(Long id);
}

