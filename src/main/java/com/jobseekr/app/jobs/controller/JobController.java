package com.jobseekr.app.jobs.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobseekr.app.jobs.dto.JobDTO;
import com.jobseekr.app.jobs.entity.Job;
import com.jobseekr.app.jobs.service.JobService;

import java.util.List;
@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @GetMapping("/api/jobs")
     public ResponseEntity<Page<JobDTO>> getAllJobs(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        String token = extractBearerToken(authorizationHeader);
        if (token != null && jobService.validateToken(token)) {
            Page<JobDTO> jobs = jobService.getAllJobs(page, size);
            return ResponseEntity.ok(jobs);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id,
                                              @RequestHeader(value = "Authorization") String authorizationHeader) {
        String token = extractBearerToken(authorizationHeader);
        if (token != null && jobService.validateToken(token)) {
            return jobService.getJobDTOById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        throw new RuntimeException("Invalid or missing token");
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remove "Bearer " prefix
        }
        return null; // Return null if token is not present
    }
}
