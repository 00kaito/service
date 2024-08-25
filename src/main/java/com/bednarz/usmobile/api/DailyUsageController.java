package com.bednarz.usmobile.api;

import com.bednarz.usmobile.application.ApiResponse;
import com.bednarz.usmobile.domain.dailyusage.DailyUsageService;
import com.bednarz.usmobile.domain.dto.DailyUsageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/daily-usage")
@RequiredArgsConstructor
public class DailyUsageController {

    private final DailyUsageService dailyUsageService;

    @GetMapping("/current")
    public ResponseEntity<ApiResponse<List<DailyUsageResponse>>> getCurrentCycleUsage(
            @RequestParam String mdn,
            @RequestParam String userId,
            @RequestParam Date startDate,
            @RequestParam Date endDate) {

        List<DailyUsageResponse> usages = dailyUsageService.getCurrentCycleUsage(mdn, userId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(usages));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DailyUsageResponse>> createDailyUsage(@RequestBody DailyUsageResponse dailyUsageRequest) {
        DailyUsageResponse createdUsage = dailyUsageService.createDailyUsage(dailyUsageRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdUsage));
    }
}
