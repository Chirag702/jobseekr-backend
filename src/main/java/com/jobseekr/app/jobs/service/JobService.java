package com.jobseekr.app.jobs.service;




import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.jobseekr.app.jobs.dto.JobDTO;
import com.jobseekr.app.jobs.entity.Job;

public interface JobService {
    Page<JobDTO> getAllJobs(int page, int size);
    Optional<JobDTO> getJobDTOById(Long jobId);
    Job saveJob(Job job);
    void deleteJob(Long id);
    boolean validateToken(String token); // Method to validate the token
}
