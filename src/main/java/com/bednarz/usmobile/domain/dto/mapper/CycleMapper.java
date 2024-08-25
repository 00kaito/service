package com.bednarz.usmobile.domain.dto.mapper;

import com.bednarz.usmobile.domain.cycle.Cycle;
import com.bednarz.usmobile.domain.dto.CycleDataResponse;
import org.mapstruct.Mapper;

@Mapper
public interface CycleMapper {
    //    @Mapping(target = "startDate", source = "cycleHistoryResponse.startDate")
//    @Mapping(target = "endDate", source = "cycleHistoryResponse.endDate")
    Cycle cycleHistoryResponseToCycle(CycleDataResponse cycleHistoryResponse);

    //    @Mapping(target = "startDate", source = "cycle.startDate")
//    @Mapping(target = "endDate", source = "cycle.endDate")
    CycleDataResponse cycleToCycleHistoryResponse(Cycle cycle);
}
