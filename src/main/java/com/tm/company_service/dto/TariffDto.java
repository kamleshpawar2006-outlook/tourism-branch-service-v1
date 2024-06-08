package com.tm.company_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TariffDto {
    private Long tariffId;
    private String place;
    @Min(value = 50000, message = "Tariff must be at least 50000")
    @Max(value = 100000, message = "Tariff must be less than or equal to 100000")
    private int tariffAmount;
}