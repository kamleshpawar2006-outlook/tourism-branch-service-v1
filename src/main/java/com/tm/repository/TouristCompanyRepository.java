package com.tm.repository;

import com.tm.entity.TouristCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristCompanyRepository extends JpaRepository<TouristCompany, Long> {
}
