package com.schooleERP.service;

import com.schooleERP.dto.AuditLogRequest;
import com.schooleERP.dto.AuditLogResponse;

import java.util.List;

public interface AuditLogService {

    AuditLogResponse createAuditLog(AuditLogRequest request);

    List<AuditLogResponse> getAllAuditLogs();

    AuditLogResponse getAuditLogById(Long id);

    List<AuditLogResponse> getAuditLogsByUser(Long userId);

    List<AuditLogResponse> getAuditLogsByAction(String action);

    List<AuditLogResponse> getAuditLogsByEntityName(String entityName);

    List<AuditLogResponse> getAuditLogsByEntity(
            String entityName,
            Long entityId
    );

    void deleteAuditLog(Long id);
}
