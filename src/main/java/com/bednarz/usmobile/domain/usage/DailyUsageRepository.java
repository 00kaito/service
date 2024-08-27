package com.bednarz.usmobile.domain.usage;

import java.util.Date;
import java.util.List;

public interface DailyUsageRepository {
    List<DailyUsage> findByMdnAndUserId(String mdn, String userId);

    List<DailyUsage> findByUserIdAndUsageDateBetween(String userId, Date startDate, Date endDate);

}
