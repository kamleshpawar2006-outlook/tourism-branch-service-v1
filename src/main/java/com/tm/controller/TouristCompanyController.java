package com.tm.controller;

import com.tm.dto.TariffDto;
import com.tm.dto.TouristCompanyDto;
import com.tm.service.TouristCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/tourism/api/v1/branch")
@Validated
public class TouristCompanyController {
    @Autowired
    private TouristCompanyService touristCompanyService;

    @Operation(summary = "Adds a tourist company with tariff details",
            description = "Add a new tourist company along with the places it operates in.")
    @PostMapping("/add-places")
    public Map<String, Long> addTouristCompany(@Parameter(description = "Tourist Company details", required = true) @Valid @RequestBody TouristCompanyDto company) {
        return touristCompanyService.addTouristCompany(company);
    }

    @Operation(summary = "Updates the tariff details of a tourist company",
            description = "Updates the tariff details of a tourist company")
    @PutMapping("/update-tariff/{branchId}")
    public TouristCompanyDto updateTariff(@Parameter(description = "List of new tariffs", required = true) @PathVariable Long branchId,@Valid @RequestBody List<TariffDto> tariffs) {
        return touristCompanyService.updateTariff(branchId, tariffs);
    }
}
