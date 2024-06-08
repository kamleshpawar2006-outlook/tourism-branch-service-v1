package com.tm.company_service.service;

import com.tm.company_service.dto.TariffDto;
import com.tm.company_service.dto.TouristCompanyDto;

import java.util.List;
import java.util.Map;

public interface TouristCompanyService {
    Map<String, Long> addTouristCompany(TouristCompanyDto companyDTO);
    TouristCompanyDto updateTariff(Long branchId, List<TariffDto> tariffDTOs);
}