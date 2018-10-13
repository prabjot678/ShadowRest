package com.shadow.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shadow.constant.ResponseConstant;
import com.shadow.dao.UserAuthDao;
import com.shadow.dao.UserRegistrationDao;
import com.shadow.domain.User;
import com.shadow.service.UserRegistrationService;
import com.shadow.service.request.UserRegistrationRequest;
import com.shadow.service.response.UserRegistrationResponse;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	@Autowired
	UserRegistrationDao userRegistrationDao;
	@Autowired
	UserAuthDao userAuthDao;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserRegistrationResponse doRegistration(UserRegistrationRequest userRegistrationRequest) {
		// TODO Auto-generated method stub

		boolean usernameExist = userAuthDao.checkUserNameExist(userRegistrationRequest.getUsername(),
				userRegistrationRequest.getEmail().toLowerCase());
		if (usernameExist) {
			UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
			userRegistrationResponse.setMessage("Username or Email already exist in our records, "
					+ "Please choose another one");
			userRegistrationResponse.setSuccess(false);
			userRegistrationResponse.setResponseCreatedAt(new Date().getTime());
			return userRegistrationResponse;
		}
		User user = new User();
		user.setUsername(userRegistrationRequest.getUsername());
		user.setFirstName(userRegistrationRequest.getFirstName());
		user.setLastName(userRegistrationRequest.getLastName());
		user.setPassword(passwordEncoder.encode(String.valueOf(userRegistrationRequest.getPassword())));
		user.setEmail(userRegistrationRequest.getEmail().toLowerCase());
		userRegistrationDao.doRegistration(user);

		UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
		userRegistrationResponse.setMessage(ResponseConstant.SUCCESS);
		userRegistrationResponse.setSuccess(true);
		userRegistrationResponse.setUserId(user.getId());
		userRegistrationResponse.setResponseCreatedAt(new Date().getTime());
		userRegistrationResponse.setUsername(user.getUsername());
		return userRegistrationResponse;
	}

}
