package com.bednarz.usmobile.api;

import com.bednarz.usmobile.application.ApiResponse;
import com.bednarz.usmobile.application.dto.CreateDailyUsageResponse;
import com.bednarz.usmobile.application.dto.CurrentCycleUsageResponse;
import com.bednarz.usmobile.application.dto.DailyUsageRequest;
import com.bednarz.usmobile.application.service.UsageApplicationService;
import com.bednarz.usmobile.infrastructure.shared.ValidMdn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/daily-usage")
@RequiredArgsConstructor
@Validated
public class DailyUsageController {

    private final UsageApplicationService dailyUsageApplicationService;

    @GetMapping("/current")
    public ResponseEntity<ApiResponse<List<CurrentCycleUsageResponse>>> getCurrentCycleUsage(
            @RequestParam @ValidMdn String mdn,
            @RequestParam String userId) {
        List<CurrentCycleUsageResponse> usages = dailyUsageApplicationService.getCurrentCycleUsage(mdn, userId);
        return ResponseEntity.ok(ApiResponse.success(usages));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateDailyUsageResponse>> createDailyUsage(@RequestBody DailyUsageRequest dailyUsageRequest) {
        CreateDailyUsageResponse createdUsage = dailyUsageApplicationService.createDailyUsage(dailyUsageRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdUsage));
    }
}
