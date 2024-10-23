package com.jobseekr.app.user.service.impl;

import java.security.cert.Certificate;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobseekr.app.auth.models.Certification;
import com.jobseekr.app.auth.models.Link;
import com.jobseekr.app.auth.models.User;
import com.jobseekr.app.auth.models.User2;
import com.jobseekr.app.auth.repository.User2Repository;
import com.jobseekr.app.auth.repository.UserRepository;
import com.jobseekr.app.user.repository.LinkRepository;
import com.jobseekr.app.user.dto.UserDTO;
import com.jobseekr.app.user.mapper.UserMapper;
import com.jobseekr.app.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	User2Repository user2Repo;

	@Autowired
	LinkRepository linkRepo;



	@Override
	public UserDTO getUserProfile(String email) {
		User user = userRepo.findByEmail(email);
		UserDTO userDTO = UserMapper.toDto(user);
		return userDTO;
	}

	@Override
	public UserDTO saveUserInitialProfile(String email, UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = userRepo.findByEmail(email);
		user.setFname(userDTO.getFname());
		user.setLname(userDTO.getLname());
		user.setGender(userDTO.getGender());
		userRepo.save(user);
		UserDTO uDTO = UserMapper.toDto(user);
		return uDTO;

	}

	@Override
	public UserDTO saveUserProfile(String email, UserDTO userDTO) {
		User user = userRepo.findByEmail(email);

		// Check if the userDTO has a non-null date of birth
		if (userDTO.getDob() != null) {
			user.setDob(userDTO.getDob());
		}

		if (userDTO.getAbout() != null) {
			user.setAbout(userDTO.getAbout());
		}

		if (userDTO.getFname() != null) {
			user.setFname(userDTO.getFname());
		}

		if (userDTO.getLname() != null) {
			user.setLname(userDTO.getLname());
		}

		if (userDTO.getPhone() != null) {
			user.setPhone(userDTO.getPhone());
		}

		if (userDTO.getLink() != null && !userDTO.getLink().isEmpty()) {
			for (Link dtoLink : userDTO.getLink()) {
				if (dtoLink.getId() != null) {
					// Update case: Update existing link in user's links
					UserMapper.updateLinkById(user.getLink(), dtoLink);
				} else {
					// Add new link to user's links
					user.getLink().add(new Link(dtoLink.getType(), dtoLink.getUrl()));
				}
			}
		}

		if (userDTO.getCertification() != null && !userDTO.getCertification().isEmpty()) {
			for (Certification dtoCertificate : userDTO.getCertification()) {
				if (dtoCertificate.getId() != null) {
					// Update case: Update existing link in user's links
					UserMapper.updateCertificationById(user.getCertification(), dtoCertificate);
				} else {
					// Add new link to user's links
					user.getCertification()
							.add(new Certification(dtoCertificate.getName(), dtoCertificate.getIssuingOrganization(),
									dtoCertificate.getIssueDate(), dtoCertificate.getExpirationDate(),
									dtoCertificate.isPresentDate(), dtoCertificate.getCredentialId(),
									dtoCertificate.getCredentialUrl(), dtoCertificate.getDescription()));
				}
			}
		}

		userRepo.save(user);
		UserDTO uDTO = UserMapper.toDto(user);
		return uDTO;
	}

	@Override
	public User getUserRProfile(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public void deleteUserProfileLinkById(String email) {
		User user = userRepo.findByEmail(email);
		User2 user2 = new User2();
		user2.setEmail(user.getEmail());
		user2.setFname(user.getFname());
		user2.setGender(user.getGender());
		user2.setLname(user.getLname());
		user2.setPhone(user.getPhone());
		user2.setUsername(user.getUsername());

		user2Repo.save(user2);

		userRepo.deleteByEmail(email);

	}



}
