package com.tm.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TariffDto {
    private Long tariffId;

    @NotEmpty(message = "Place cannot be empty")
    private String place;

    @NotEmpty(message = "Type cannot be empty")
    @Pattern(regexp = "^\\d+$", message = "Tariff must be a numeric value")
    private String tariffAmount;


    public boolean isTariffAmountValid() {
        try {
            int tariff = Integer.parseInt(tariffAmount);
            return tariff >= 50000 && tariff <= 100000;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}