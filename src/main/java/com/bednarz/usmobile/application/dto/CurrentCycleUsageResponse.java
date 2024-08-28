package com.bednarz.usmobile.application.dto;

import lombok.Builder;
import lombok.With;

import java.time.LocalDate;

@Builder
@With
public record CurrentCycleUsageResponse(
        LocalDate usageDate,
        Double usedInMb
) {
}