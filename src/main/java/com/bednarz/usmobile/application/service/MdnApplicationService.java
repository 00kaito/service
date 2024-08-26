package com.bednarz.usmobile.application.service;

import com.bednarz.usmobile.domain.billing.CycleDomainService;
import com.bednarz.usmobile.domain.usage.DailyUsageDomainService;
import com.bednarz.usmobile.domain.user.UserDomainService;
import com.bednarz.usmobile.infrastructure.audit.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MdnApplicationService {

    private final CycleDomainService cycleService;
    private final DailyUsageDomainService dailyUsageService;
    private final UserDomainService userDomainService;
    private final AuditService auditService;

    @Transactional
    public void transferMdn(String mdn, String fromUserId, String toUserId) {
        validateUsers(fromUserId, toUserId);
        validateMdn(mdn);

        cycleService.transferMdn(mdn, fromUserId, toUserId);
        dailyUsageService.transferMdn(mdn, fromUserId, toUserId);
        auditService.logAction("MDN Transfer", fromUserId,
                String.format("MDN %s transferred from user %s to user %s", mdn, fromUserId, toUserId));
    }

    private void validateUsers(String fromUserId, String toUserId) {
        userDomainService.getUserById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("From user not found"));
        userDomainService.getUserById(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("To user not found"));
    }

    private void validateMdn(String mdn) {
        if (mdn == null || mdn.isBlank() || mdn.length() != CycleDomainService.phoneNumberLength) {
            throw new IllegalArgumentException("Invalid MDN");
        }
    }
}
