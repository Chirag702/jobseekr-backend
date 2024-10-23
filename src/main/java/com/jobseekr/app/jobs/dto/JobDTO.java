package com.jobseekr.app.jobs.dto;

import com.jobseekr.app.company.dto.CompanyDTO;
import com.jobseekr.app.company.entity.Company;

import lombok.Data;
import java.time.LocalDate;

@Data
public class JobDTO {
    private Long id;
    private String title;
    private String location;
    private String description;
    private String requirements;
    private LocalDate postedDate;
    private String employmentType;
    private String industry;
    private String seniorityLevel;
    private String salaryRange;
    private CompanyDTO companyDetails;  // Change from companyId to CompanyDTO
    private String applicationLink;
    private String benefits;
    private String skillsRequired;
}
