package com.tm.service;

import com.tm.dto.TariffDto;
import com.tm.dto.TouristCompanyDto;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Validated
public interface TouristCompanyService {
    Map<String, Long> addTouristCompany(TouristCompanyDto companyDTO);
    TouristCompanyDto updateTariff(Long branchId, List<TariffDto> tariffDTOs);
}