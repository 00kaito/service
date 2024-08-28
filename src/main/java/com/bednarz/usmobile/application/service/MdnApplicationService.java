package com.bednarz.usmobile.application.service;

import com.bednarz.usmobile.domain.billing.CycleDomainService;
import com.bednarz.usmobile.domain.usage.DailyUsageDomainService;
import com.bednarz.usmobile.domain.user.UserDomainService;
import com.bednarz.usmobile.infrastructure.shared.audit.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MdnApplicationService {

    private final CycleDomainService cycleService;
    private final DailyUsageDomainService dailyUsageService;
    private final UserDomainService userDomainService;
    private final AuditService auditService;

    @Transactional
    public void transferMdn(String mdn, String fromUserId, String toUserId) {
        log.info("Initiating MDN transfer: {} from user {} to user {}", mdn, fromUserId, toUserId);
        validateUsers(fromUserId, toUserId);
        validateMdn(mdn);

        cycleService.transferMdn(mdn, fromUserId, toUserId);
        dailyUsageService.transferMdn(mdn, fromUserId, toUserId);
        auditService.logAction("MDN Transfer", "SYSTEM",
                String.format("MDN %s transferred from user %s to user %s", mdn, fromUserId, toUserId));
        log.info("MDN transfer completed: {} from user {} to user {}", mdn, fromUserId, toUserId);

    }

    private void validateUsers(String fromUserId, String toUserId) {
        userDomainService.getUserById(fromUserId)
                .orElseThrow(() -> {
                    log.error("FromUserId not found: {}", fromUserId);
                    return new IllegalArgumentException("From user not found");
                });
        userDomainService.getUserById(toUserId)
                .orElseThrow(() -> {
                    log.error("ToUserId not found: {}", toUserId);
                    return new IllegalArgumentException("To user not found");
                });
    }

    private void validateMdn(String mdn) {
        log.debug("Validating MDN: {}", mdn);
        if (mdn == null || mdn.isBlank() || mdn.length() != CycleDomainService.phoneNumberLength) {
            log.error("Invalid MDN: {}", mdn);
            throw new IllegalArgumentException("Invalid MDN");
        }
    }
}
