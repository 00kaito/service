package com.bednarz.usmobile.domain.dailyusage;

import com.bednarz.usmobile.infrastructure.audit.AuditInfo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "daily_usage")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "user_usageDate_idx", def = "{'userId': 1, 'usageDate': 1}"),
        @CompoundIndex(name = "mdn_idx", def = "{'mdn': 1}")
})
public class DailyUsage extends AuditInfo {
    @Id
    private String id;
    @Indexed(unique = true)
    private String mdn;
    private String userId;
    @Indexed
    private Date usageDate;
    private Double usedInMb;
}