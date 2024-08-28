package com.bednarz.usmobile.application.service;

import com.bednarz.usmobile.application.dto.CreateCycleRequest;
import com.bednarz.usmobile.application.dto.CycleDataResponse;
import com.bednarz.usmobile.domain.billing.CycleDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingApplicationService {
    private final CycleDomainService cycleService;

    public List<CycleDataResponse> getCycleHistoryByMdn(String mdn) {
        log.info("Getting cycle history for MDN: {}", mdn);
        List<CycleDataResponse> response = cycleService.getCycleHistoryByMdn(mdn);
        log.debug("Retrieved {} cycle history entries for MDN: {}", response.size(), mdn);
        return response;
    }

    public CycleDataResponse createCycle(CreateCycleRequest cycleRequest) {
        log.info("Creating new cycle for MDN: {}", cycleRequest.getMdn());
        CycleDataResponse response = cycleService.createCycle(cycleRequest);
        log.debug("Created new cycle for MDN: {}", response.getMdn());
        return response;
    }
}