package com.bednarz.usmobile.domain.usage;

import com.bednarz.usmobile.infrastructure.audit.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyUsageRepository extends MongoRepository<DailyUsage, String> {
    List<DailyUsage> findByMdnAndUserId(String mdn, String userId);

    List<DailyUsage> findByUserIdAndUsageDateBetween(String userId, Date startDate, Date endDate);

    @Service
    @RequiredArgsConstructor
    class DailyUsageService {

        private final DailyUsageRepository dailyUsageRepository;
        private final AuditService auditService;

        @Cacheable("currentCycleUsage")
        public List<DailyUsage> getCurrentCycleUsage(String mdn, String userId, Date startDate, Date endDate) {
            return dailyUsageRepository.findByUserIdAndUsageDateBetween(userId, startDate, endDate);
        }

        public DailyUsage createDailyUsage(DailyUsage dailyUsage) {
            String details = String.format("DailyUsage created: %s", dailyUsage.toString());
            auditService.logAction("Create Daily Usage", dailyUsage.getUserId(), details);

            return dailyUsageRepository.save(dailyUsage);
        }
    }
}
