package com.schooleERP.controller;

import com.schooleERP.dto.AuditLogRequest;
import com.schooleERP.dto.AuditLogResponse;
import com.schooleERP.service.AuditLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    // 1. Create audit log
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<AuditLogResponse> createAuditLog(@Valid @RequestBody AuditLogRequest request) {

        AuditLogResponse response = auditLogService.createAuditLog(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. Get all audit logs
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<List<AuditLogResponse>> getAllAuditLogs() {
        return ResponseEntity.ok(auditLogService.getAllAuditLogs());
    }

    // 3. Get audit log by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<AuditLogResponse> getAuditLogById(@PathVariable Long id) {

        return ResponseEntity.ok(auditLogService.getAuditLogById(id));
    }

    // 4. Get audit logs by user
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsByUser(@PathVariable Long userId) {

        return ResponseEntity.ok(auditLogService.getAuditLogsByUser(userId));
    }

    // 5. Get audit logs by action
    @GetMapping("/action/{action}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsByAction(@PathVariable String action) {

        return ResponseEntity.ok(auditLogService.getAuditLogsByAction(action)
        );
    }

    // 6. Get audit logs by entity name
    @GetMapping("/entity/{entityName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsByEntityName(@PathVariable String entityName) {

        return ResponseEntity.ok(auditLogService.getAuditLogsByEntityName(entityName));
    }

    // 7. Get audit logs for a specific entity
    @GetMapping("/entity/{entityName}/{entityId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsByEntity(@PathVariable String entityName, @PathVariable Long entityId) {

        return ResponseEntity.ok(auditLogService.getAuditLogsByEntity(entityName, entityId)
        );
    }

    // 8. Delete audit log
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuditLog(@PathVariable Long id) {

        auditLogService.deleteAuditLog(id);
        return ResponseEntity.noContent().build();
    }
}
