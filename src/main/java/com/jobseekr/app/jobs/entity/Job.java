package com.jobseekr.app.jobs.entity;

import lombok.Data;
import java.time.LocalDate;
import jakarta.persistence.*;

import com.jobseekr.app.company.entity.Company;

@Data
@Entity
@Table(name = "jobs")
public class Job {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    private LocalDate postedDate;

    private String employmentType; // e.g., Full-time, Part-time, Contract

    private String industry; // e.g., IT, Finance

    private String seniorityLevel; // e.g., Junior, Mid, Senior

    private String salaryRange; // e.g., "$60,000 - $80,000"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // No need for insertable = false, updatable = false
    private Company company; // Associated Company

    // Additional fields for clarity
    private String applicationLink; // Link to apply for the job
    private String benefits; // e.g., "Health insurance, 401(k)"
    private String skillsRequired; // e.g., "Java, Spring, SQL"
}
