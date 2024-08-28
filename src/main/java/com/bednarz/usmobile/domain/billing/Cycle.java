package com.bednarz.usmobile.domain.billing;

import com.bednarz.usmobile.infrastructure.shared.audit.AuditInfo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "cycle")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@With
@CompoundIndex(name = "mdn_userid_idx", def = "{'mdn': 1, 'userId': 1}", unique = true)
public class Cycle extends AuditInfo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String mdn;

    private LocalDate startDate;
    private LocalDate endDate;

    @Indexed
    private String userId;
}
