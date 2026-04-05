package com.carmanagement.service;

import com.carmanagement.dao.auditLogDAO;
import com.carmanagement.entity.AuditLog;
import com.carmanagement.entity.Employee;

import java.time.LocalDateTime;
import java.util.List;

public class AuditLogService {
    private final auditLogDAO dao = new auditLogDAO();

    public void log(Employee actor, String actionType, String targetType, String targetId, String details) {
        try {
            AuditLog log = new AuditLog();
            log.setIdLog(generateId());
            log.setActorId(actor != null ? actor.getId_employee() : "SYSTEM");
            log.setActorName(actor != null ? actor.getName_employee() : "System");
            log.setActorRole(actor != null ? actor.getPosition_employee() : "System");
            log.setActionType(actionType);
            log.setTargetType(targetType);
            log.setTargetId(targetId);
            log.setDetails(details);
            log.setCreatedAt(LocalDateTime.now());
            dao.insert(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AuditLog> getAllLogs() {
        return dao.getAll();
    }

    private String generateId() {
        String seed = String.valueOf(System.currentTimeMillis());
        if (seed.length() > 12) {
            seed = seed.substring(seed.length() - 12);
        }
        return "LOG" + seed;
    }
}
