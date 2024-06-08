package com.tm.company_service.service.serviceImpl;

import com.tm.company_service.dto.TariffDto;
import com.tm.company_service.entity.Tariff;
import com.tm.company_service.entity.TouristCompany;
import com.tm.company_service.repository.TariffRepository;
import com.tm.company_service.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TariffServiceImpl implements TariffService {

    @Autowired
    private TariffRepository tariffRepository;

    @Override
    public List<Tariff> updateTariffs(Long branchId, List<TariffDto> tariffDTOs, TouristCompany company) {
        return tariffDTOs.stream().map(tariffDTO -> {
            Optional<Tariff> existingTariffOpt = findByPlaceAndBranchId(tariffDTO.getPlace(), branchId);
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

    @Override
    public void deleteRemovedTariffs(List<Tariff> existingTariffs, List<Tariff> updatedTariffs) {
        List<Long> updatedTariffIds = updatedTariffs.stream().map(Tariff::getTariffId).collect(Collectors.toList());
        List<Tariff> tariffsToDelete = existingTariffs.stream()
                .filter(tariff -> !updatedTariffIds.contains(tariff.getTariffId()))
                .collect(Collectors.toList());
        tariffRepository.deleteAll(tariffsToDelete);
    }

    @Override
    public Optional<Tariff> findByPlaceAndBranchId(String place, Long branchId) {
        return tariffRepository.findByPlaceAndTouristCompany_BranchId(place, branchId);
    }

    private Tariff convertToEntity(TariffDto tariffDTO) {
        Tariff tariff = new Tariff();
        tariff.setPlace(tariffDTO.getPlace());
        tariff.setTariffAmount(tariffDTO.getTariffAmount());
        return tariff;
    }
}
