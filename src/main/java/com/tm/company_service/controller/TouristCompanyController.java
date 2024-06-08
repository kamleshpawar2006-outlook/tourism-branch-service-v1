package com.tm.company_service.controller;

import com.tm.company_service.dto.TariffDto;
import com.tm.company_service.dto.TouristCompanyDto;
import com.tm.company_service.service.TouristCompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tourism/branch")
public class TouristCompanyController {
    @Autowired
    private TouristCompanyService touristCompanyService;

    @PostMapping("/add-places")
    public Map<String, Long> addTouristCompany(@Valid @RequestBody TouristCompanyDto company) {
        return touristCompanyService.addTouristCompany(company);
    }

    @PutMapping("/update-tariff/{branchId}")
    public TouristCompanyDto updateTariff(@PathVariable Long branchId, @RequestBody List<TariffDto> tariffs) {
        return touristCompanyService.updateTariff(branchId, tariffs);
    }
}
