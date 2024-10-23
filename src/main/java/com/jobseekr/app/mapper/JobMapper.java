package com.jobseekr.app.mapper;

import com.jobseekr.app.jobs.dto.JobDTO;
import com.jobseekr.app.jobs.entity.Job;
import com.jobseekr.app.company.dto.CompanyDTO;
import com.jobseekr.app.company.entity.Company;

public class JobMapper {

    public static JobDTO toDTO(Job job) {
        if (job == null) return null;

        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setLocation(job.getLocation());
        dto.setDescription(job.getDescription());
        dto.setRequirements(job.getRequirements());
        dto.setPostedDate(job.getPostedDate());
        dto.setEmploymentType(job.getEmploymentType());
        dto.setIndustry(job.getIndustry());
        dto.setSeniorityLevel(job.getSeniorityLevel());
        dto.setSalaryRange(job.getSalaryRange());
        
        // If you want to include the companyId in the DTO, 
        // it should come from the associated Company object
        if (job.getCompany() != null) {
            // Convert the Company entity to a CompanyDTO using the DtoConverter
            CompanyDTO companyDTO = CompanyMapper.toDTO(job.getCompany());
            
            // Set the converted CompanyDTO to the JobDTO
            dto.setCompanyDetails(companyDTO);
        }

        
        dto.setApplicationLink(job.getApplicationLink());
        dto.setBenefits(job.getBenefits());
        dto.setSkillsRequired(job.getSkillsRequired());
        return dto;
    }

    public static Job toEntity(JobDTO dto, Company company) {
        if (dto == null) return null;

        Job job = new Job();
        job.setId(dto.getId());
        job.setTitle(dto.getTitle());
        job.setLocation(dto.getLocation());
        job.setDescription(dto.getDescription());
        job.setRequirements(dto.getRequirements());
        job.setPostedDate(dto.getPostedDate());
        job.setEmploymentType(dto.getEmploymentType());
        job.setIndustry(dto.getIndustry());
        job.setSeniorityLevel(dto.getSeniorityLevel());
        job.setSalaryRange(dto.getSalaryRange());
        
        // Set the company directly if available
        job.setCompany(company); // Set the Company object
        
        job.setApplicationLink(dto.getApplicationLink());
        job.setBenefits(dto.getBenefits());
        job.setSkillsRequired(dto.getSkillsRequired());
        return job;
    }
}
