package com.bednarz.usmobile.domain.usage;

import java.time.LocalDate;
import java.util.List;

public interface DailyUsageRepository {
    List<DailyUsage> findByUserIdAndMdnAndUsageDateBetween(String userId, String mdn, LocalDate startDate, LocalDate endDate);
}
