package com.bednarz.usmobile.application.dto;

import com.bednarz.usmobile.infrastructure.shared.ValidMdn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateCycleRequest {

    @NotBlank(message = "MDN must not be blank")
    @ValidMdn
    private String mdn;

    @NotNull(message = "Start date must not be null")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    @NotNull(message = "End date must not be null")
    private LocalDate endDate;

    @NotEmpty(message = "userId cannot be empty")
    String userId;
}
