package com.bednarz.usmobile.domain.dto.model;

import com.bednarz.usmobile.infrastructure.audit.AuditInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

public record DailyUsageDto(
        @Id String id,
        @Indexed(unique = true)
        String mdn,
        @Indexed
        String userId,
        Date usageDate,
        Double usedInMb,
        AuditInfo auditInfo
) {
}
