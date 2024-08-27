package com.bednarz.usmobile.api;

import com.bednarz.usmobile.application.ApiResponse;
import com.bednarz.usmobile.application.dto.DailyUsageRequest;
import com.bednarz.usmobile.application.dto.DailyUsageResponse;
import com.bednarz.usmobile.application.service.UsageApplicationService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/daily-usage")
@RequiredArgsConstructor
public class DailyUsageController {

    private final UsageApplicationService dailyUsageApplicationService;

    @GetMapping("/current")
    public ResponseEntity<ApiResponse<List<DailyUsageResponse>>> getCurrentCycleUsage(
            @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = "MDN must be 10 digits long") String mdn,
            @RequestParam String userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<DailyUsageResponse> usages = dailyUsageApplicationService.getCurrentCycleUsage(mdn, userId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(usages));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DailyUsageResponse>> createDailyUsage(@RequestBody DailyUsageRequest dailyUsageRequest) {
        DailyUsageResponse createdUsage = dailyUsageApplicationService.createDailyUsage(dailyUsageRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdUsage));
    }
}
