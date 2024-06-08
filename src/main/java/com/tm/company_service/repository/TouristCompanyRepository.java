package com.tm.company_service.repository;

import com.tm.company_service.entity.TouristCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristCompanyRepository extends JpaRepository<TouristCompany, Long> {
}
