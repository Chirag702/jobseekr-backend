package com.jobseekr.app.jobs.service.impl;

 
import com.jobseekr.app.auth.security.jwt.JwtUtils;
import com.jobseekr.app.jobs.dto.JobDTO;
import com.jobseekr.app.jobs.entity.Job;
import com.jobseekr.app.jobs.repository.JobRepository;
import com.jobseekr.app.jobs.service.JobService;
import com.jobseekr.app.mapper.JobMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JwtUtils jwtUtils;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, JwtUtils jwtUtils) {
        this.jobRepository = jobRepository;
        this.jwtUtils = jwtUtils;
    }


    public Page<JobDTO> getAllJobs(int page, int size) {
        // Fetch the jobs in a paginated way
        Page<Job> jobPage = jobRepository.findAll(PageRequest.of(page, size));
        
        // Convert to DTOs and sort by ID in decreasing order
        List<JobDTO> jobDTOs = jobPage.getContent().stream()
                .sorted(Comparator.comparing(Job::getId).reversed())
                .map(JobMapper::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(jobDTOs, jobPage.getPageable(), jobPage.getTotalElements());
    }



    @Override
    public Optional<JobDTO> getJobDTOById(Long jobId) {
        return jobRepository.findById(jobId)
            .map(job -> JobMapper.toDTO(job)); // Assuming JobMapper is the correct mapper class
    }

    @Override
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtils.validateJwtToken(token); // Validate the token
    }
}
