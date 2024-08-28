package com.bednarz.usmobile.domain.usage;

import com.bednarz.usmobile.application.dto.CreateDailyUsageResponse;
import com.bednarz.usmobile.application.dto.CurrentCycleUsageResponse;
import com.bednarz.usmobile.application.dto.DailyUsageRequest;
import com.bednarz.usmobile.application.dto.mapper.DailyUsageMapper;
import com.bednarz.usmobile.infrastructure.persistence.DailyUsageMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyUsageDomainService {

    private final MongoTemplate mongoTemplate;
    private final DailyUsageMongoRepository dailyUsageRepository;
    private final DailyUsageMapper dailyUsageMapper;

    public List<CurrentCycleUsageResponse> getCurrentCycleUsage(String mdn, String userId, LocalDate startDate, LocalDate endDate) {
        List<DailyUsage> dailyUsages = dailyUsageRepository.findByUserIdAndMdnAndUsageDateBetween(userId, mdn, startDate.minusDays(1), endDate.plusDays(1));
        return dailyUsages.stream()
                .map(dailyUsageMapper::dailyUsageToCurrentCycleUsageResponse)
                .collect(Collectors.toList());
    }

    public CreateDailyUsageResponse createDailyUsage(DailyUsageRequest dailyUsageRequest) {
        DailyUsage dailyUsage = dailyUsageMapper.dailyUsageRequestToDailyUsage(dailyUsageRequest);
        DailyUsage savedDailyUsage = dailyUsageRepository.save(dailyUsage);
        return dailyUsageMapper.dailyUsageToDailyUsageResponse(savedDailyUsage);
    }


    public void transferMdn(String mdn, String fromUserId, String toUserId) {
        Query query = new Query(Criteria.where("mdn").is(mdn).and("userId").is(fromUserId));
        mongoTemplate.remove(query, "daily_usage");

        DailyUsage resetEntry = DailyUsage.builder()
                .mdn(mdn)
                .userId(toUserId)
                .usageDate(LocalDate.now())
                .usedInMb(0.0)
                .build();

        mongoTemplate.save(resetEntry, "daily_usage");
    }

}
