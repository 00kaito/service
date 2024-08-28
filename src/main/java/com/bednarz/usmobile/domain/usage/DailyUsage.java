package com.bednarz.usmobile.domain.usage;

import com.bednarz.usmobile.infrastructure.shared.audit.AuditInfo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "daily_usage")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "user_mdn_usageDate_idx", def = "{'userId': 1, 'mdn': 1, 'usageDate': 1}")
})
public class DailyUsage extends AuditInfo {
    @Id
    private String id;
    private String mdn;
    private String userId;
    private LocalDate usageDate;
    private Double usedInMb;
}