package com.jobseekr.app.mapper;

import com.jobseekr.app.company.dto.CompanyDTO;
import com.jobseekr.app.company.entity.Company;
import com.jobseekr.app.jobs.entity.Job;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    public static CompanyDTO toDTO(Company company) {
        if (company == null) return null;

        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setCompanyName(company.getCompanyName());
        dto.setLogoUrl(company.getLogoUrl());
        dto.setCompanyDescription(company.getCompanyDescription());
        dto.setIndustry(company.getIndustry());
        dto.setCompanyType(company.getCompanyType());
        dto.setWebsiteUrl(company.getWebsiteUrl());
        dto.setHeadquarters(company.getHeadquarters());
        dto.setCompanySize(company.getCompanySize());
         return dto;
    }

    public static Company toEntity(CompanyDTO dto) {
        if (dto == null) return null;

        Company company = new Company();
        company.setId(dto.getId());
        company.setCompanyName(dto.getCompanyName());
        company.setLogoUrl(dto.getLogoUrl());
        company.setCompanyDescription(dto.getCompanyDescription());
        company.setIndustry(dto.getIndustry());
        company.setCompanyType(dto.getCompanyType());
        company.setWebsiteUrl(dto.getWebsiteUrl());
        company.setHeadquarters(dto.getHeadquarters());
        company.setCompanySize(dto.getCompanySize());
        return company;
    }
}
