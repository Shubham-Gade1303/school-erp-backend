package com.schooleERP.repository;

import com.schooleERP.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepo extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUserId(Long userId);

    List<AuditLog> findByAction(String action);

    List<AuditLog> findByEntityName(String entityName);

    List<AuditLog> findByEntityNameAndEntityId(String entityName, Long entityId
    );
}
