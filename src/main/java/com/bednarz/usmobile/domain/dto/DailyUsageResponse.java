package com.bednarz.usmobile.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DailyUsageResponse {
    private String id;
    private String mdn;
    private Date usageDate;
    private Double usedInMb;
}
