package com.jobseekr.app.referrer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobseekr.app.referrer.entity.Referrer;

@Repository
public interface ReferrerRepository extends JpaRepository<Referrer, Long> {
    // Additional query methods can be defined here if needed
}
