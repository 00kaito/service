package com.bednarz.usmobile.application.service;

import com.bednarz.usmobile.domain.dto.DailyUsageRequest;
import com.bednarz.usmobile.domain.dto.DailyUsageResponse;
import com.bednarz.usmobile.domain.usage.DailyUsageDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsageApplicationService {
    private final DailyUsageDomainService dailyUsageService;

    public List<DailyUsageResponse> getCurrentCycleUsage(String mdn, String userId, Date startDate, Date endDate) {
        return dailyUsageService.getCurrentCycleUsage(mdn, userId, startDate, endDate);
    }

    public DailyUsageResponse createDailyUsage(DailyUsageRequest dailyUsageRequest) {
        return dailyUsageService.createDailyUsage(dailyUsageRequest);
    }
}