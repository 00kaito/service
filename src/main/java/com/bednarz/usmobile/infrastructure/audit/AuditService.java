package com.bednarz.usmobile.infrastructure.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditTrailRepository auditTrailRepository;

    public void logAction(String action, String performedBy, String details) {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setAction(action);
        auditTrail.setPerformedBy(performedBy);
        auditTrail.setTimestamp(LocalDateTime.now());
        auditTrail.setDetails(details);
        auditTrailRepository.save(auditTrail);
    }
}
