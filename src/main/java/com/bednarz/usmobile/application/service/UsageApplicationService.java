package com.bednarz.usmobile.application.service;

import com.bednarz.usmobile.application.dto.CreateDailyUsageResponse;
import com.bednarz.usmobile.application.dto.CurrentCycleUsageResponse;
import com.bednarz.usmobile.application.dto.CycleDataResponse;
import com.bednarz.usmobile.application.dto.DailyUsageRequest;
import com.bednarz.usmobile.domain.billing.CycleDomainService;
import com.bednarz.usmobile.domain.usage.DailyUsageDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsageApplicationService {
    private final DailyUsageDomainService dailyUsageService;
    private final CycleDomainService cycleDomainService;

    public List<CurrentCycleUsageResponse> getCurrentCycleUsage(String mdn, String userId) {
        log.info("Getting current cycle usage for MDN: {} and userId: {}", mdn, userId);
        CycleDataResponse currentCycle = cycleDomainService.getCurrentCycle(mdn, userId);
        log.debug("Retrieved current cycle for MDN: {} and userId: {}", mdn, userId);
        return dailyUsageService.getCurrentCycleUsage(mdn, userId, currentCycle.getStartDate(), currentCycle.getEndDate());
    }

    public CreateDailyUsageResponse createDailyUsage(DailyUsageRequest dailyUsageRequest) {
        return dailyUsageService.createDailyUsage(dailyUsageRequest);
    }
}