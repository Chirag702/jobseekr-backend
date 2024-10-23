package com.jobseekr.app.auth.models;

import java.util.ArrayList;

import java.util.HashSet;

import java.util.List;
import java.util.Set;



import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	private String fname;

	private String lname;

	private String gender;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	private String otp;

	private Boolean isEmailVerified;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // This will create a user_id column in the experiences table
	private List<Experience> experiences;

	private String about;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // This will create a user_id column in the experiences table
	private List<Education> education;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private List<Awards> awards;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // This will create a user_id column in the experiences table
	private List<Certification> certification;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // This will create a user_id column in the experiences table
	private List<Link> link;
	private String phone;

	private String dob;

	private Boolean isPhoneVerified;
	
	private String companyName;
	
	private String companyRole;
	

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "connect_id") // This will create a user_id column in the experiences table
	private Connects connects;


	
	private Boolean isReferrerFormSubmitted=false;
	
	private Boolean isReferrer=false;

	public User(@NotBlank @Size(max = 50) String username, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password, String otp, Boolean isEmailVerified) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.otp = otp;
		this.isEmailVerified = isEmailVerified;
	}
	
	





	public User(@NotBlank @Size(max = 50) String username, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password, String otp, Boolean isEmailVerified, String companyName,
			String companyRole) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.otp = otp;
		this.isEmailVerified = isEmailVerified;
		this.companyName = companyName;
		this.companyRole = companyRole;
	}
}
