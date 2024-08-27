package com.bednarz.usmobile.application.service;

import com.bednarz.usmobile.application.dto.CycleDataResponse;
import com.bednarz.usmobile.domain.billing.CycleDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingApplicationService {
    private final CycleDomainService cycleService;

    public List<CycleDataResponse> getCycleHistoryByMdn(String mdn) {
        return cycleService.getCycleHistoryByMdn(mdn);
    }

    public List<CycleDataResponse> getCurrentCycleUsage(String mdn, String userId) {
        return cycleService.getCurrentCycleUsage(mdn, userId);
    }

    public CycleDataResponse createCycle(CycleDataResponse cycleRequest) {
        return cycleService.createCycle(cycleRequest);
    }
}