package com.jobseekr.app.company.entity;

 
import lombok.Data;

 import java.util.List;

import com.jobseekr.app.jobs.entity.Job;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    private String logoUrl; // URL for the company's logo

    @Column(columnDefinition = "TEXT")
    private String companyDescription; // Basic details about the company

    private String industry; // e.g., IT, Finance

    private String companyType; // e.g., Private, Public

    private String websiteUrl; // URL for the company's website

    private String headquarters; // Location of the company's headquarters

    private String companySize; // e.g., "51-200 employees"

    @OneToMany(mappedBy = "company")
    private List<Job> jobs; // List of associated jobs
}



