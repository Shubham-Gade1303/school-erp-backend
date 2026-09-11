package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.DocumentRequest;
import com.schooleERP.dto.DocumentResponse;
import com.schooleERP.entity.Document;
import com.schooleERP.entity.Student;
import com.schooleERP.repository.DocumentRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.DocumentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepo documentRepo;
    private final StudentRepo studentRepo;

    public DocumentServiceImpl(
            DocumentRepo documentRepo,
            StudentRepo studentRepo
    ) {
        this.documentRepo = documentRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public DocumentResponse createDocument(DocumentRequest request) {

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found with id: " + request.getStudentId()
                        )
                );

        if (documentRepo.existsByStudentIdAndDocumentType(
                request.getStudentId(),
                request.getDocumentType()
        )) {
            throw new IllegalArgumentException(
                    "Document of type '" + request.getDocumentType()
                            + "' already exists for student id: "
                            + request.getStudentId()
            );
        }

        Document document = new Document();

        document.setStudent(student);
        document.setDocumentType(request.getDocumentType());
        document.setDocumentName(request.getDocumentName());
        document.setDocumentUrl(request.getDocumentUrl());
        document.setUploadedDate(request.getUploadedDate());

        return mapToResponse(documentRepo.save(document));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> getAllDocuments() {

        return documentRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponse getDocumentById(Long id) {

        Document document = documentRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Document not found with id: " + id
                        )
                );

        return mapToResponse(document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> getDocumentsByStudent(Long studentId) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException(
                    "Student not found with id: " + studentId
            );
        }

        return documentRepo.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DocumentResponse updateDocument(
            Long id,
            DocumentRequest request
    ) {

        Document document = documentRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Document not found with id: " + id
                        )
                );

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found with id: " + request.getStudentId()
                        )
                );

        if (!document.getStudent().getId().equals(request.getStudentId())
                || !document.getDocumentType().equals(request.getDocumentType())) {

            if (documentRepo.existsByStudentIdAndDocumentType(
                    request.getStudentId(),
                    request.getDocumentType()
            )) {
                throw new IllegalArgumentException(
                        "Document of type '" + request.getDocumentType()
                                + "' already exists for student id: "
                                + request.getStudentId()
                );
            }
        }

        document.setStudent(student);
        document.setDocumentType(request.getDocumentType());
        document.setDocumentName(request.getDocumentName());
        document.setDocumentUrl(request.getDocumentUrl());
        document.setUploadedDate(request.getUploadedDate());

        return mapToResponse(documentRepo.save(document));
    }

    @Override
    public void deleteDocument(Long id) {

        if (!documentRepo.existsById(id)) {
            throw new IllegalArgumentException(
                    "Document not found with id: " + id
            );
        }

        documentRepo.deleteById(id);
    }

    private DocumentResponse mapToResponse(Document document) {

        Student student = document.getStudent();

        DocumentResponse response = new DocumentResponse();

        response.setId(document.getId());

        response.setStudentId(student.getId());
        response.setAdmissionNumber(student.getAdmissionNumber());

        String studentName =
                student.getFirstName()
                        + " "
                        + (student.getMiddleName() != null
                        ? student.getMiddleName() + " "
                        : "")
                        + student.getLastName();

        response.setStudentName(studentName.trim());

        response.setDocumentType(document.getDocumentType());
        response.setDocumentName(document.getDocumentName());
        response.setDocumentUrl(document.getDocumentUrl());
        response.setUploadedDate(document.getUploadedDate());

        return response;
    }
}

