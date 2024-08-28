package com.bednarz.usmobile.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateDailyUsageResponse {
    private String userId;
    private String mdn;
    private LocalDate usageDate;
    private Double usedInMb;
}
