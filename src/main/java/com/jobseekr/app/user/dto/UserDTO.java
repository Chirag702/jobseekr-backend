package com.jobseekr.app.user.dto;

import java.util.List;


import com.jobseekr.app.auth.models.Awards;
import com.jobseekr.app.auth.models.Certification;
import com.jobseekr.app.auth.models.Education;
import com.jobseekr.app.auth.models.Experience;
import com.jobseekr.app.auth.models.Link;


import lombok.Data;

@Data
public class UserDTO {
	private String username;
	private String email;
	private List<Experience> experiences;
	private String about;
	private List<Education> education;
	private List<Awards> awards;
	private List<Certification> certification;
	private List<Link> link;
	private String phone;
	private String dob;
	private String fname;
	private String lname;
	private String gender;
	private Boolean isEmailVerified;
	private Boolean isPhoneVerified;
 	private String companyName;
	private String companyRole;
	private Boolean isReferrerFormSubmitted;
	private Boolean isReferrer;
}
