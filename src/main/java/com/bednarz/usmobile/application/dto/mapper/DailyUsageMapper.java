package com.bednarz.usmobile.application.dto.mapper;

import com.bednarz.usmobile.application.dto.CreateDailyUsageResponse;
import com.bednarz.usmobile.application.dto.CurrentCycleUsageResponse;
import com.bednarz.usmobile.application.dto.DailyUsageRequest;
import com.bednarz.usmobile.domain.usage.DailyUsage;
import org.mapstruct.Mapper;

@Mapper
public interface DailyUsageMapper {

    CreateDailyUsageResponse dailyUsageToDailyUsageResponse(DailyUsage dailyUsage);

    DailyUsage dailyUsageRequestToDailyUsage(DailyUsageRequest dailyUsageResponse);

    CurrentCycleUsageResponse dailyUsageToCurrentCycleUsageResponse(DailyUsage dailyUsage);
}

