package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.DocumentRequest;
import com.schooleERP.dto.DocumentResponse;
import com.schooleERP.entity.Documents;
import com.schooleERP.repository.DocumentRepo;
import com.schooleERP.service.DocumentService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private static final long MAX_FILE_SIZE = 1024L * 1024;

    private final DocumentRepo documentRepo;

    public DocumentServiceImpl(DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }

    @Override
    public DocumentResponse createDocument(
            DocumentRequest request, MultipartFile file) {

        validateRequest(request);

        // Verify the student exists here using your existing
        // student/admission repository before saving.

        Documents document = new Documents();
        document.setStudentId(request.getStudentId());
        document.setDocumentType(request.getDocumentType());

        applyFile(document, file);

        return toResponse(documentRepo.save(document));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> getAllDocuments() {
        return documentRepo.findAllMetadata();
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponse getDocumentById(Long id) {
        checkId(id);

        return documentRepo.findMetadataById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Document not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> getDocumentsByStudent(Long studentId) {
        checkId(studentId);
        return documentRepo.findMetadataByStudentId(studentId);
    }

    @Override
    public DocumentResponse updateDocument(
            Long id, DocumentRequest request) {

        checkId(id);
        validateRequest(request);

        Documents document = findDocument(id);

        // Verify the new student exists before changing studentId.
        document.setStudentId(request.getStudentId());
        document.setDocumentType(request.getDocumentType());

        return toResponse(documentRepo.save(document));
    }

    @Override
    public DocumentResponse replaceDocumentFile(
            Long id, MultipartFile file) {

        Documents document = findDocument(id);
        applyFile(document, file);

        return toResponse(documentRepo.save(document));
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentDownload downloadDocument(Long id) {
        Documents document = findDocument(id);

        return new DocumentDownload(
                document.getFileName(),
                document.getContentType(),
                document.getFileData()
        );
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepo.delete(findDocument(id));
    }

    private void validateRequest(DocumentRequest request) {
        if (request == null
                || request.getStudentId() == null
                || request.getStudentId() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid student ID");
        }

        if (request.getDocumentType() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Document type is required");
        }
    }

    private void applyFile(Documents document, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "A file is required");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ResponseStatusException(
                    HttpStatus.PAYLOAD_TOO_LARGE,
                    "File size must not exceed 1 MB");
        }

        String originalName = file.getOriginalFilename();
        String fileName = originalName == null
                ? ""
                : originalName.trim();

        if (fileName.isBlank()
                || fileName.length() > 255
                || fileName.contains("/")
                || fileName.contains("\\")
                || fileName.contains("..")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid file name");
        }

        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unable to read uploaded file");
        }

        String detectedType = detectContentType(bytes);
        String extension = getExtension(fileName);

        if (detectedType == null
                || !extensionMatches(extension, detectedType)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only valid PDF, JPG, JPEG and PNG files are allowed");
        }

        document.setFileName(fileName);
        document.setContentType(detectedType);
        document.setFileSize((long) bytes.length);
        document.setFileData(bytes);
    }

    private void checkId(Long id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid ID");
        }
    }

    private Documents findDocument(Long id) {
        checkId(id);

        return documentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Document not found"));
    }

    private DocumentResponse toResponse(Documents document) {
        return new DocumentResponse(
                document.getId(),
                document.getStudentId(),
                document.getDocumentType(),
                document.getFileName(),
                document.getContentType(),
                document.getFileSize(),
                document.getUploadedAt()
        );
    }

    private String getExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');

        if (dot < 0 || dot == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(dot + 1).toLowerCase(Locale.ROOT);
    }

    private String detectContentType(byte[] data) {
        if (startsWith(data,
                new byte[]{0x25, 0x50, 0x44, 0x46, 0x2D})) {
            return "application/pdf";
        }

        if (startsWith(data,
                new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF})) {
            return "image/jpeg";
        }

        if (startsWith(data, new byte[]{
                (byte) 0x89, 0x50, 0x4E, 0x47,
                0x0D, 0x0A, 0x1A, 0x0A
        })) {
            return "image/png";
        }

        return null;
    }

    private boolean startsWith(byte[] data, byte[] signature) {
        return data.length >= signature.length
                && Arrays.equals(
                Arrays.copyOf(data, signature.length),
                signature);
    }

    private boolean extensionMatches(
            String extension, String contentType) {

        return switch (contentType) {
            case "application/pdf" -> extension.equals("pdf");
            case "image/jpeg" ->
                    extension.equals("jpg") || extension.equals("jpeg");
            case "image/png" -> extension.equals("png");
            default -> false;
        };
    }
}
