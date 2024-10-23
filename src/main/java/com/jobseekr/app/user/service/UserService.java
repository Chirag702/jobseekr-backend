package com.jobseekr.app.user.service;

import java.util.List;

import com.jobseekr.app.auth.models.Link;
import com.jobseekr.app.auth.models.User;
import com.jobseekr.app.user.dto.UserDTO;
import com.jobseekr.app.user.service.impl.UserServiceImpl;

public interface UserService {

	UserDTO getUserProfile(String email);

	UserDTO saveUserInitialProfile(String email, UserDTO userDTO);

	UserDTO saveUserProfile(String email, UserDTO userDTO);

	User getUserRProfile(String email);

	void deleteUserProfileLinkById(String email);



}