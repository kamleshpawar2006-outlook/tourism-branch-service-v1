package com.tm.service;

import com.tm.dto.TariffDto;
import com.tm.entity.Tariff;
import com.tm.entity.TouristCompany;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
public interface TariffService {
    List<Tariff> updateTariffs(Long branchId, List<TariffDto> tariffDTOs, TouristCompany company);
    void deleteRemovedTariffs(List<Tariff> existingTariffs, List<Tariff> updatedTariffs);
    Optional<Tariff> findByPlaceAndBranchId(String place, Long branchId);
}
