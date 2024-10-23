package com.jobseekr.app.auth.payload.request;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Data;
 @Data
public class SignupRequest {
    @NotBlank
    @Size( max = 50)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
	private String otp;

    

	private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    private String companyName;
    
    private String companyRole;

}
