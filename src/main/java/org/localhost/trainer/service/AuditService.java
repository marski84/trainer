package org.localhost.trainer.service;

import org.localhost.trainer.model.AuditLog;
import org.localhost.trainer.repository.AuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {
    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logEvent(String message) {
        AuditLog log = new AuditLog(message);
        auditRepository.save(log);
    }
}
