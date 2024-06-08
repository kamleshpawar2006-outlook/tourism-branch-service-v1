package com.tm.company_service.service.serviceImpl;

import com.tm.company_service.dto.TariffDto;
import com.tm.company_service.dto.TouristCompanyDto;
import com.tm.company_service.entity.Tariff;
import com.tm.company_service.entity.TouristCompany;
import com.tm.company_service.exception.CustomException;
import com.tm.company_service.repository.TariffRepository;
import com.tm.company_service.repository.TouristCompanyRepository;
import com.tm.company_service.service.TariffService;
import com.tm.company_service.service.TouristCompanyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TouristCompanyServiceImpl implements TouristCompanyService {
    @Autowired
    private TouristCompanyRepository touristCompanyRepository;

    @Autowired
    private TariffRepository tariffRepository;

    @Autowired
    private TariffService tariffService;

    @Override
    @Transactional
    public Map<String, Long> addTouristCompany(TouristCompanyDto companyDTO) throws CustomException {
        TouristCompany company = convertToEntity(companyDTO);
        if(company.getTariffs().size() > 0) {
            for (Tariff tariff : company.getTariffs()) {
                tariff.setTouristCompany(company);
            }
        } else {
            throw new CustomException("No places selected. Please add at least one place.");
        }

        try {
            TouristCompany savedCompany = touristCompanyRepository.save(company);
            Map<String, Long> response = new HashMap<>();
            response.put("branchId", savedCompany.getBranchId());
            return response;
        } catch (DataIntegrityViolationException ex) {
            throw new CustomException("Duplicate place(s) found");
        }
    }

    @Override
    @Transactional
    public TouristCompanyDto updateTariff(Long branchId, List<TariffDto> tariffDTOs) {
        TouristCompany company = findCompanyById(branchId);
        List<Tariff> existingTariffs = company.getTariffs();
        List<Tariff> updatedTariffs = tariffService.updateTariffs(branchId, tariffDTOs, company);
        tariffService.deleteRemovedTariffs(existingTariffs, updatedTariffs);
        company.getTariffs().clear();
        company.getTariffs().addAll(updatedTariffs);
        TouristCompany updatedCompany = touristCompanyRepository.save(company);
        return convertToDTO(updatedCompany);
    }

    private TouristCompany findCompanyById(Long branchId) {
        Optional<TouristCompany> companyOpt = touristCompanyRepository.findById(branchId);
        System.out.println("Company found: " + companyOpt.isPresent());
        if (!companyOpt.isPresent()) {
            throw new CustomException("Invalid Branch ID");
        }
        return companyOpt.get();
    }

    private void deleteRemovedTariffs(List<Tariff> existingTariffs, List<Tariff> updatedTariffs) {
        List<Long> updatedTariffIds = updatedTariffs.stream().map(Tariff::getTariffId).collect(Collectors.toList());
        List<Tariff> tariffsToDelete = existingTariffs.stream()
                .filter(tariff -> !updatedTariffIds.contains(tariff.getTariffId()))
                .collect(Collectors.toList());
        tariffRepository.deleteAll(tariffsToDelete);
    }

    private List<Tariff> getUpdatedTariffs(Long branchId, List<TariffDto> tariffDTOs, TouristCompany company) {
        return tariffDTOs.stream().map(tariffDTO -> {
            Optional<Tariff> existingTariffOpt = tariffRepository.findByPlaceAndTouristCompany_BranchId(tariffDTO.getPlace(), branchId);
            Tariff tariff;
            if (existingTariffOpt.isPresent()) {
                tariff = existingTariffOpt.get();
                tariff.setPlace(tariffDTO.getPlace());
                tariff.setTariffAmount(tariffDTO.getTariffAmount());
            } else {
                tariff = convertToEntity(tariffDTO);
                tariff.setTouristCompany(company);
            }
            return tariff;
        }).collect(Collectors.toList());
    }

    private TouristCompany convertToEntity(TouristCompanyDto companyDTO) {
        TouristCompany company = new TouristCompany();
        company.setBranchName(companyDTO.getBranchName());
        company.setPlace(companyDTO.getPlace());
        company.setWebsite(companyDTO.getWebsite());
        company.setContact(companyDTO.getContact());
        company.setEmail(companyDTO.getEmail());
        List<Tariff> tariffs = companyDTO.getTariffs().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        company.setTariffs(tariffs);
        return company;
    }

    private Tariff convertToEntity(TariffDto tariffDto) {
        Tariff tariff = new Tariff();
        tariff.setTariffId(tariff.getTariffId());
        tariff.setPlace(tariffDto.getPlace());
        tariff.setTariffAmount(tariffDto.getTariffAmount());
        return tariff;
    }

    private TouristCompanyDto convertToDTO(TouristCompany company) {
        TouristCompanyDto companyDTO = new TouristCompanyDto();
        companyDTO.setBranchId(company.getBranchId());
        companyDTO.setBranchName(company.getBranchName());
        companyDTO.setPlace(company.getPlace());
        companyDTO.setWebsite(company.getWebsite());
        companyDTO.setContact(company.getContact());
        companyDTO.setEmail(company.getEmail());
        List<TariffDto> tariffs = company.getTariffs().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        companyDTO.setTariffs(tariffs);
        return companyDTO;
    }

    private TariffDto convertToDto(Tariff tariff) {
        TariffDto tariffDTO = new TariffDto();
        tariffDTO.setTariffId(tariff.getTariffId());
        tariffDTO.setPlace(tariff.getPlace());
        tariffDTO.setTariffAmount(tariff.getTariffAmount());
        return tariffDTO;
    }
}