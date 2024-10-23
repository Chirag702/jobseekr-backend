package com.jobseekr.app.auth.repository;

 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobseekr.app.auth.models.User;
import com.jobseekr.app.auth.models.User2;

import jakarta.transaction.Transactional;

@Repository
public interface User2Repository extends JpaRepository<User2, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	User findByEmail(String email);

	@Transactional
	void deleteByEmail(String email);
}
