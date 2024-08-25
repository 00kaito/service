package com.bednarz.usmobile.domain.dto.mapper;

import com.bednarz.usmobile.domain.dailyusage.DailyUsage;
import com.bednarz.usmobile.domain.dto.DailyUsageResponse;
import org.mapstruct.Mapper;

@Mapper
public interface DailyUsageMapper {

    DailyUsageResponse dailyUsageToDailyUsageResponse(DailyUsage dailyUsage);

    DailyUsage dailyUsageResponseToDailyUsage(DailyUsageResponse dailyUsageResponse);
}

