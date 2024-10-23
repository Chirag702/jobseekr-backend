package com.jobseekr.app.user.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobseekr.app.auth.models.User;
import com.jobseekr.app.auth.repository.UserRepository;
import com.jobseekr.app.auth.security.jwt.JwtUtils;

import com.jobseekr.app.user.dto.UserDTO;
import com.jobseekr.app.user.service.UserService;

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

	@Autowired
	JwtUtils jwt;

	@Autowired
	UserService userService;

	@GetMapping("api/user/profile")
	public UserDTO getUserProfile(@RequestHeader("Authorization") String authorizationHeader) {
		// Extract the token from the Authorization header
		String token = authorizationHeader.substring(7);
		String email = jwt.getUserNameFromJwtToken(token);
		UserDTO user = userService.getUserProfile(email);
		return user;
	}

	@PostMapping("api/user/init/profile")
	public UserDTO saveUserInitProfile(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody UserDTO userDTO) {
		String token = authorizationHeader.substring(7);
		String email = jwt.getUserNameFromJwtToken(token);
		UserDTO uDTO = userService.saveUserInitialProfile(email, userDTO);
		return uDTO;
	}

	@PostMapping("api/user/profile")
	public UserDTO saveUserDob(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody UserDTO userDTO) {
		String token = authorizationHeader.substring(7);
		String email = jwt.getUserNameFromJwtToken(token);
		UserDTO uDTO = userService.saveUserProfile(email, userDTO);
		return uDTO;
	}

	@GetMapping("api/user/r/profile")
	public User getUserRProfile(@RequestHeader("Authorization") String authorizationHeader) {
		// Extract the token from the Authorization header
		String token = authorizationHeader.substring(7);
		String email = jwt.getUserNameFromJwtToken(token);
		User user = userService.getUserRProfile(email);
		return user;
	}

	@GetMapping("api/user/profile/delete")
	public void deleteUserProfileLinkById(@RequestHeader("Authorization") String authorizationHeader) {
		// Extract the token from the Authorization header
		String token = authorizationHeader.substring(7);
		String email = jwt.getUserNameFromJwtToken(token);
		userService.deleteUserProfileLinkById(email);
	}

}
