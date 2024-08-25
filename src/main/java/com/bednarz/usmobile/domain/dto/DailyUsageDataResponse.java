package com.bednarz.usmobile.domain.dto;

import java.util.Date;

public record DailyUsageDataResponse(
        Date usageDate,
        Double totalUsedInMb
) {
}