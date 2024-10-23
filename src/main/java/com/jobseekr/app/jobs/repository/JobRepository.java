package com.jobseekr.app.jobs.repository;

 
 import org.springframework.data.jpa.repository.JpaRepository;

import com.jobseekr.app.jobs.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    // Additional query methods can be defined here
}
