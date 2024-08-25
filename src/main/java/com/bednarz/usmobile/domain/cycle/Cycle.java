package com.bednarz.usmobile.domain.cycle;

import com.bednarz.usmobile.infrastructure.audit.AuditInfo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "cycle")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@With
public class Cycle extends AuditInfo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String mdn;

    private Date startDate;
    private Date endDate;

    @Indexed(unique = true)
    private String userId;
}
