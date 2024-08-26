package com.bednarz.usmobile.api;

import com.bednarz.usmobile.application.ApiResponse;
import com.bednarz.usmobile.application.service.BillingApplicationService;
import com.bednarz.usmobile.domain.dto.CycleDataResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cycles")
@RequiredArgsConstructor
public class CycleController {

    private final BillingApplicationService cycleApplicationService;

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<CycleDataResponse>>> getCycleHistory(
            @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = "MDN must be 10 digits long") String mdn) {
        List<CycleDataResponse> cycles = cycleApplicationService.getCycleHistoryByMdn(mdn);
        return ResponseEntity.ok(ApiResponse.success(cycles));
    }

    @GetMapping("/usage/current")
    public ResponseEntity<ApiResponse<List<CycleDataResponse>>> getCurrentCycleUsage(
            @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = "MDN must be 10 digits long") String mdn,
            @RequestParam @NotBlank String userId) {
        List<CycleDataResponse> cycles = cycleApplicationService.getCurrentCycleUsage(mdn, userId);
        return ResponseEntity.ok(ApiResponse.success(cycles));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CycleDataResponse>> createCycle(@Valid @RequestBody CycleDataResponse cycleRequest) {
        CycleDataResponse createdCycle = cycleApplicationService.createCycle(cycleRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdCycle));
    }
}
