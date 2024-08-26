package com.bednarz.usmobile.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DailyUsageRequest {
    private String userId;
    private String mdn;
    private Date usageDate;
    private Double usedInMb;
}
