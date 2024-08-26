package com.bednarz.usmobile.domain.dto.mapper;

import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.dto.CycleDataResponse;
import org.mapstruct.Mapper;

@Mapper
public interface CycleMapper {
    Cycle cycleHistoryResponseToCycle(CycleDataResponse cycleHistoryResponse);

    CycleDataResponse cycleToCycleHistoryResponse(Cycle cycle);
}
