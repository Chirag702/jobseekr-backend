package com.jobseekr.app.user.mapper;

import com.jobseekr.app.user.dto.UserDTO;

import java.util.List;

import com.jobseekr.app.auth.models.Certification;
import com.jobseekr.app.auth.models.Link;
import com.jobseekr.app.auth.models.User;

public class UserMapper {

	public static User toEntity(UserDTO dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setAbout(dto.getAbout());
		user.setPhone(dto.getPhone());
		user.setDob(dto.getDob());

		// Convert lists of related entities
		user.setExperiences(dto.getExperiences());
		user.setEducation(dto.getEducation());
		user.setAwards(dto.getAwards());
		user.setCertification(dto.getCertification());
		user.setLink(dto.getLink());
		user.setFname(dto.getFname());
		user.setLname(dto.getLname());
		user.setGender(dto.getGender());
		user.setIsEmailVerified(dto.getIsEmailVerified());
		user.setIsPhoneVerified(dto.getIsPhoneVerified());
 		user.setCompanyName(dto.getCompanyName());
		user.setCompanyRole(dto.getCompanyRole());
		user.setIsReferrerFormSubmitted(dto.getIsReferrerFormSubmitted());
		user.setIsReferrer(dto.getIsReferrer());
		return user;
	}

	public static UserDTO toDto(User entity) {
		UserDTO dto = new UserDTO();
		dto.setUsername(entity.getUsername());
		dto.setEmail(entity.getEmail());
		dto.setAbout(entity.getAbout());
		dto.setPhone(entity.getPhone());
		dto.setDob(entity.getDob());

		// Convert lists of related entities
		dto.setExperiences(entity.getExperiences());
		dto.setEducation(entity.getEducation());
		dto.setAwards(entity.getAwards());
		dto.setCertification(entity.getCertification());
		dto.setLink(entity.getLink());
		dto.setFname(entity.getFname());
		dto.setLname(entity.getLname());
		dto.setGender(entity.getGender());
		dto.setIsEmailVerified(entity.getIsEmailVerified());
		dto.setIsPhoneVerified(entity.getIsPhoneVerified());
 		dto.setCompanyName(entity.getCompanyName());
		dto.setCompanyRole(entity.getCompanyRole());
		dto.setIsReferrerFormSubmitted(entity.getIsReferrerFormSubmitted());
		dto.setIsReferrer(entity.getIsReferrer());
		return dto;
	}
	
	
	  public static void updateLinkById(List<Link> userLinks, Link updatedLink) {
	        for (int i = 0; i < userLinks.size(); i++) {
	            Link link = userLinks.get(i);
	            if (link.getId() != null && link.getId().equals(updatedLink.getId())) {
	                userLinks.set(i, updatedLink);
	                return;
	            }
	        }
	        // Handle case if link with given ID is not found (optional)
	    }

	  public static void updateCertificationById(List<Certification> userCertifications, Certification updatedCertification) {
	        for (int i = 0; i < userCertifications.size(); i++) {
	            Certification cert = userCertifications.get(i);
	            if (cert.getId() != null && cert.getId().equals(updatedCertification.getId())) {
	                userCertifications.set(i, updatedCertification);
	                return;
	            }
	        }
	        // Handle case if link with given ID is not found (optional)
	    }



}
