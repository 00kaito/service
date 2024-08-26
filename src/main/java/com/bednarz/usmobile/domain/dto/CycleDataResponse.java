package com.bednarz.usmobile.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
public class CycleDataResponse {

    private String id;

    @NotBlank(message = "MDN must not be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "MDN must be 10 digits long")
    private String mdn;

    @NotNull(message = "Start date must not be null")
    @PastOrPresent(message = "Start date must be in the past or present")
    private Date startDate;

    @NotNull(message = "End date must not be null")
    @PastOrPresent(message = "End date must be in the past or present")
    private Date endDate;
}
