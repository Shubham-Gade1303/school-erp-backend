package com.schooleERP.repository;

import com.schooleERP.dto.DocumentResponse;
import com.schooleERP.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentRepo extends JpaRepository<Documents, Long> {

    @Query("""
        select new com.schooleERP.dto.DocumentResponse(
            d.id, d.studentId, d.documentType, d.fileName,
            d.contentType, d.fileSize, d.uploadedAt)
        from Documents d
        """)
    List<DocumentResponse> findAllMetadata();

    @Query("""
        select new com.schooleERP.dto.DocumentResponse(
            d.id, d.studentId, d.documentType, d.fileName,
            d.contentType, d.fileSize, d.uploadedAt)
        from Documents d where d.studentId = :studentId
        """)
    List<DocumentResponse> findMetadataByStudentId(@Param("studentId") Long studentId);

    @Query("""
        select new com.schooleERP.dto.DocumentResponse(
            d.id, d.studentId, d.documentType, d.fileName,
            d.contentType, d.fileSize, d.uploadedAt)
        from Documents d where d.id = :id
        """)
    Optional<DocumentResponse> findMetadataById(@Param("id") Long id);
}