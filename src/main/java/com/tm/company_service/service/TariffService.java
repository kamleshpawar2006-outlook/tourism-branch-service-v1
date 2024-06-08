package com.tm.company_service.service;

import com.tm.company_service.dto.TariffDto;
import com.tm.company_service.entity.Tariff;
import com.tm.company_service.entity.TouristCompany;

import java.util.List;
import java.util.Optional;

public interface TariffService {
    List<Tariff> updateTariffs(Long branchId, List<TariffDto> tariffDTOs, TouristCompany company);
    void deleteRemovedTariffs(List<Tariff> existingTariffs, List<Tariff> updatedTariffs);
    Optional<Tariff> findByPlaceAndBranchId(String place, Long branchId);
}
