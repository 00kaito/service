package com.bednarz.usmobile.infrastructure.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Document(collection = "audit_trails")
public class AuditTrail {

    @Id
    private String id;

    private String action;

    private String performedBy;

    private LocalDateTime timestamp;

    private String details;
}
