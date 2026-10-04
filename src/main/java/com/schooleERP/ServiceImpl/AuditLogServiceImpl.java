package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.AuditLogRequest;
import com.schooleERP.dto.AuditLogResponse;
import com.schooleERP.entity.AuditLog;
import com.schooleERP.entity.User;
import com.schooleERP.repository.AuditLogRepo;
import com.schooleERP.repository.UserRepo;
import com.schooleERP.service.AuditLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepo auditLogRepo;
    private final UserRepo userRepo;

    public AuditLogServiceImpl(
            AuditLogRepo auditLogRepo,
            UserRepo userRepo) {
        this.auditLogRepo = auditLogRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public AuditLogResponse createAuditLog(AuditLogRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setAction(request.getAction());
        auditLog.setEntityName(request.getEntityName());
        auditLog.setEntityId(request.getEntityId());
        auditLog.setDescription(request.getDescription());
        auditLog.setIpAddress(request.getIpAddress());
        auditLog.setCreatedAt(LocalDateTime.now());
        AuditLog savedAuditLog = auditLogRepo.save(auditLog);
        return mapToResponse(savedAuditLog);
    }

    @Override
    public List<AuditLogResponse> getAllAuditLogs() {
        return auditLogRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AuditLogResponse getAuditLogById(Long id) {
        AuditLog auditLog = auditLogRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Audit log not found with id: " + id));

        return mapToResponse(auditLog);
    }

    @Override
    public List<AuditLogResponse> getAuditLogsByUser(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return auditLogRepo.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AuditLogResponse> getAuditLogsByAction(String action) {

        return auditLogRepo.findByAction(action)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AuditLogResponse> getAuditLogsByEntityName(
            String entityName) {

        return auditLogRepo.findByEntityName(entityName)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AuditLogResponse> getAuditLogsByEntity(
            String entityName,
            Long entityId) {

        return auditLogRepo
                .findByEntityNameAndEntityId(entityName, entityId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteAuditLog(Long id) {
        if (!auditLogRepo.existsById(id)) {
            throw new RuntimeException("Audit log not found with id: " + id);
        }
        auditLogRepo.deleteById(id);
    }

    private AuditLogResponse mapToResponse(AuditLog auditLog) {
        return new AuditLogResponse(
                auditLog.getId(),
                auditLog.getUser().getId(),
                auditLog.getUser().getUsername(),
                auditLog.getAction(),
                auditLog.getEntityName(),
                auditLog.getEntityId(),
                auditLog.getDescription(),
                auditLog.getIpAddress(),
                auditLog.getCreatedAt()
        );
    }
}
