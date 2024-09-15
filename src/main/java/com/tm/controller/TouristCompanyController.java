package com.tm.controller;

import com.tm.dto.TariffDto;
import com.tm.dto.TouristCompanyDto;
import com.tm.service.TouristCompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tourism/branch")
@Validated
public class TouristCompanyController {
    @Autowired
    private TouristCompanyService touristCompanyService;

    @PostMapping("/add-places")
    public Map<String, Long> addTouristCompany(@Valid @RequestBody TouristCompanyDto company) {
        return touristCompanyService.addTouristCompany(company);
    }

    @PutMapping("/update-tariff/{branchId}")
    public TouristCompanyDto updateTariff(@PathVariable Long branchId,@Valid @RequestBody List<TariffDto> tariffs) {
        return touristCompanyService.updateTariff(branchId, tariffs);
    }
}
