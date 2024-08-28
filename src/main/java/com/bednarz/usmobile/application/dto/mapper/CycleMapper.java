package com.bednarz.usmobile.application.dto.mapper;

import com.bednarz.usmobile.application.dto.CreateCycleRequest;
import com.bednarz.usmobile.application.dto.CycleDataResponse;
import com.bednarz.usmobile.domain.billing.Cycle;
import org.mapstruct.Mapper;

@Mapper
public interface CycleMapper {
    Cycle cycleHistoryResponseToCycle(CreateCycleRequest cycleHistoryResponse);

    CycleDataResponse cycleToCycleDataResponse(Cycle cycle);


}
