package com.bednarz.usmobile.api;

import com.bednarz.usmobile.application.ApiResponse;
import com.bednarz.usmobile.application.dto.CreateCycleRequest;
import com.bednarz.usmobile.application.dto.CycleDataResponse;
import com.bednarz.usmobile.application.service.BillingApplicationService;
import com.bednarz.usmobile.infrastructure.shared.ValidMdn;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cycles")
@RequiredArgsConstructor
@Validated
public class CycleController {

    private final BillingApplicationService billingApplicationService;

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<CycleDataResponse>>> getCycleHistory(
            @RequestParam @ValidMdn String mdn) {
        List<CycleDataResponse> cycles = billingApplicationService.getCycleHistoryByMdn(mdn);
        return ResponseEntity.ok(ApiResponse.success(cycles));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CycleDataResponse>> createCycle(@Valid @RequestBody CreateCycleRequest cycleRequest) {
        CycleDataResponse createdCycle = billingApplicationService.createCycle(cycleRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdCycle));
    }
}
