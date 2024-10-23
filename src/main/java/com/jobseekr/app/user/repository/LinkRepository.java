package com.jobseekr.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobseekr.app.auth.models.Link;

public interface LinkRepository extends JpaRepository<Link, Long>{

}
