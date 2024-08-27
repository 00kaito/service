package com.bednarz.usmobile.application.dto;

import java.util.Date;

public record DailyUsageDataResponse(
        Date usageDate,
        Double totalUsedInMb
) {
}