package com.bednarz.usmobile.domain.dto.mapper;

import com.bednarz.usmobile.domain.dto.DailyUsageRequest;
import com.bednarz.usmobile.domain.dto.DailyUsageResponse;
import com.bednarz.usmobile.domain.usage.DailyUsage;
import org.mapstruct.Mapper;

@Mapper
public interface DailyUsageMapper {

    DailyUsageResponse dailyUsageToDailyUsageResponse(DailyUsage dailyUsage);

    DailyUsage dailyUsageRequestToDailyUsage(DailyUsageRequest dailyUsageResponse);
}

