package com.bednarz.usmobile.domain.usage;

import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.billing.CycleRepository;
import com.bednarz.usmobile.domain.dto.DailyUsageDataResponse;
import com.bednarz.usmobile.domain.dto.DailyUsageRequest;
import com.bednarz.usmobile.domain.dto.DailyUsageResponse;
import com.bednarz.usmobile.domain.dto.mapper.DailyUsageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyUsageDomainService {

    private final DailyUsageRepository dailyUsageRepository;
    private final MongoTemplate mongoTemplate;
    private final CycleRepository cycleRepository;
    private final DailyUsageMapper dailyUsageMapper;

    public List<DailyUsageResponse> getCurrentCycleUsage(String mdn, String userId, Date startDate, Date endDate) {
        List<DailyUsage> dailyUsages = dailyUsageRepository.findByUserIdAndUsageDateBetween(userId, startDate, endDate);
        return dailyUsages.stream()
                .map(dailyUsageMapper::dailyUsageToDailyUsageResponse)
                .collect(Collectors.toList());
    }

    public DailyUsageResponse createDailyUsage(DailyUsageRequest dailyUsageRequest) {
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
                .usageDate(new Date())
                .usedInMb(0.0)
                .build();

        mongoTemplate.save(resetEntry, "daily_usage");
    }

    List<DailyUsageDataResponse> getDailyUsageByMdnAndCycle(String mdn, String userId) {
        Cycle currentCycle = getCurrentCycle(mdn, userId);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("mdn").is(mdn)
                        .and("userId").is(userId)
                        .and("usageDate").gte(currentCycle.getStartDate()).lte(currentCycle.getEndDate())),
                Aggregation.group("usageDate").sum("usedInMb").as("totalUsedInMb"),
                Aggregation.project("totalUsedInMb").and("usageDate").previousOperation()
        );

        AggregationResults<DailyUsageDataResponse> results = mongoTemplate.aggregate(aggregation, "daily_usage", DailyUsageDataResponse.class);
        return results.getMappedResults();
    }

    private Cycle getCurrentCycle(String mdn, String userId) {
        return cycleRepository.findByMdnAndUserId(mdn, userId).stream()
                .filter(this::isCurrentCycle)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No current cycle found for the given MDN."));
    }

    private boolean isCurrentCycle(Cycle cycle) {
        Date now = new Date();
        return now.after(cycle.getStartDate()) && now.before(cycle.getEndDate());
    }


}
