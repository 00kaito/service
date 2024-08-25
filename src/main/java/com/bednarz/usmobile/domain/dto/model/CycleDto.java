package com.bednarz.usmobile.domain.dto.model;

import com.bednarz.usmobile.infrastructure.audit.AuditInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

public record CycleDto(
        @Id String id,
        @Indexed(unique = true)
        String mdn,
        Date startDate,
        Date endDate,
        @Indexed(unique = true)
        String userId,
        AuditInfo auditInfo
) {
}
