package com.jobseekr.app.auth.controllers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobseekr.app.auth.models.ERole;
import com.jobseekr.app.auth.models.Role;
import com.jobseekr.app.auth.models.User;
import com.jobseekr.app.auth.payload.request.LoginRequest;
import com.jobseekr.app.auth.payload.request.SignupRequest;
import com.jobseekr.app.auth.payload.response.UserInfoResponse;
import com.jobseekr.app.auth.payload.response.MessageResponse;
import com.jobseekr.app.auth.repository.PasswordResetTokenRepository;
import com.jobseekr.app.auth.repository.RoleRepository;
import com.jobseekr.app.auth.repository.UserRepository;
import com.jobseekr.app.auth.security.jwt.JwtUtils;
import com.jobseekr.app.auth.security.services.UserDetailsImpl;
import com.jobseekr.app.email.EmailService;
import com.jobseekr.app.auth.models.PasswordResetToken;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	PasswordResetTokenRepository tokenRepository;

	@Autowired
	public JwtUtils jwt;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	EmailService emailService;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new UserInfoResponse(
				userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, jwtCookie.toString()));
	}

	public static int generateRandomNumber(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		String otp = String.valueOf(generateRandomNumber(100000, 999999));
		User user;

		if (signUpRequest.getCompanyName() == null || signUpRequest.getCompanyRole() == null) {
			user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
					encoder.encode(signUpRequest.getPassword()), otp, false);
		} else {
			user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
					encoder.encode(signUpRequest.getPassword()), otp, false, signUpRequest.getCompanyName(),
					signUpRequest.getCompanyRole());
		}

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);

		emailService.sendHtmlEmail(signUpRequest.getEmail(), "Email Address Validation", "<!DOCTYPE html>"
				+ "<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>OTP Verification</title>"
				+ "<link href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css' rel='stylesheet'><style>"
				+ "body { background-color: #f8f9fa; } .email-container { background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); margin: 50px auto; max-width: 600px; }"
				+ ".otp { font-size: 24px; font-weight: bold; letter-spacing: 5px; margin: 20px 0; } .btn-primary { background-color: #007bff; border-color: #007bff; text-decoration: none; color: #ffffff; padding: 10px 20px; border-radius: 5px; }"
				+ "</style></head><body><div class='container'><div class='email-container'><p>Hi JobSeekr,</p><p>Thank you for registering with JobSeekr. Please use the OTP below to verify your account:</p>"
				+ "<div class='otp text-center'>" + otp
				+ "</div><p class='text-center'>This OTP is valid for 10 minutes.</p><p>If you did not request this code, please ignore this email.</p><br><p>Thank you,<br>JobSeekr Team</p></div></div></body></html>");

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), signUpRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles2 = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new UserInfoResponse(
				userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles2, jwtCookie.toString()));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("You've been signed out!"));
	}

	@PostMapping("/check/email")
	public Boolean checkEmailExists(@RequestBody SignupRequest signupRequest) {
		return userRepository.existsByEmail(signupRequest.getEmail());
	}

	@PostMapping("/verify/email")
	public Boolean verifyEmail(@RequestHeader("Authorization") String authorizationHeader, @RequestBody User user1) {
		String token = authorizationHeader.substring(7);
		String email = jwt.getUserNameFromJwtToken(token);
		User user = userRepository.findByEmail(email);

		if (user != null && user.getOtp().equals(user1.getOtp())) {
			user.setIsEmailVerified(true);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@PostMapping("/is/email/verify")
	public Boolean isEmailVerified(@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7);
		String email = jwt.getUserNameFromJwtToken(token);
		User user = userRepository.findByEmail(email);

		return user != null && user.getIsEmailVerified();
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) throws MessagingException {
		if (!userRepository.existsByEmail(email)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email not found!"));
		}

		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setToken(UUID.randomUUID().toString());
		resetToken.setEmail(email);
		resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(10));
		tokenRepository.save(resetToken);

		String subject = "Password Reset Request";
		String body = "You requested to reset your password. Use the following token to reset it: "
				+ "<a href=\"https://onefactor.in/#/r/reset/password/" + resetToken.getToken() + "\">Reset Password</a>";

		emailService.sendHtmlEmail(email, subject, body);

		return ResponseEntity.ok().body(new MessageResponse("Password reset token sent to your email"));
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestParam("token") String token, 
	                                       @RequestBody String newPassword) {
	    // Retrieve the token
	    PasswordResetToken resetToken = tokenRepository.findByToken(token);
	    
	    // Check if token exists and is valid
	    if (resetToken == null) {
	        return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid token!"));
	    }
	    if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
	        return ResponseEntity.badRequest().body(new MessageResponse("Error: Token expired!"));
	    }

	    // Find the user by email associated with the token
	    User user = userRepository.findByEmail(resetToken.getEmail());

	    // Update the user's password (make sure to encode it)
	    user.setPassword(encoder.encode(newPassword));
	    userRepository.save(user);

	    // Invalidate the token after use
	    tokenRepository.delete(resetToken);

	    return ResponseEntity.ok(new MessageResponse("Password reset successfully."));
	}

	@PostMapping("/validate-token")
	public ResponseEntity<?> validate(@RequestParam("token") String token) {
	    // Retrieve the token
	    PasswordResetToken resetToken = tokenRepository.findByToken(token);
	    
	    // Check if token exists and is valid
	    if (resetToken == null) {
	        return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid token!"));
	    }
	    if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
	        return ResponseEntity.badRequest().body(new MessageResponse("Error: Token expired!"));
	    }

	    return ResponseEntity.ok("token is valid");
	}

}
