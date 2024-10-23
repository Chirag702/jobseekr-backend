package com.jobseekr.app.company.dto;

import lombok.Data;
import java.util.List;

@Data
public class CompanyDTO {
    private Long id;
    private String companyName;
    private String logoUrl;
    private String companyDescription;
    private String industry;
    private String companyType;
    private String websiteUrl;
    private String headquarters;
    private String companySize;
    private List<Long> jobIds; // Store job IDs instead of full job objects
}
