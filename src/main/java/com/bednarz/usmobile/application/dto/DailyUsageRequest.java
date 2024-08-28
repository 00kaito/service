package com.bednarz.usmobile.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyUsageRequest {
    private String userId;
    private String mdn;
    private LocalDate usageDate;
    private Double usedInMb;

}
