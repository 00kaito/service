package com.bednarz.usmobile.infrastructure.audit;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


public class AuditInfo {
    @CreatedDate
    LocalDateTime createdAt;
    @LastModifiedDate
    LocalDateTime updatedAt;
}