package com.jobseekr.app.company.repository;

 
 import org.springframework.data.jpa.repository.JpaRepository;

import com.jobseekr.app.company.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Additional query methods can be defined here
}
