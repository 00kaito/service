package com.bednarz.usmobile.infrastructure.shared.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditTrailRepository auditTrailRepository;

    public void logAction(String action, String performedBy, String details) {
        log.info("Logging action: {}, performed by: {}", action, performedBy);
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setAction(action);
        auditTrail.setPerformedBy(performedBy);
        auditTrail.setTimestamp(LocalDateTime.now());
        auditTrail.setDetails(details);
        auditTrailRepository.save(auditTrail);
    }
}
