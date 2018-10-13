package com.shadow.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shadow.constant.ResponseConstant;
import com.shadow.dao.UserAuthDao;
import com.shadow.dao.UserRegistrationDao;
import com.shadow.domain.User;
import com.shadow.service.EmailService;
import com.shadow.service.UserAuthService;
import com.shadow.service.request.ChangePasswordRequest;
import com.shadow.service.request.ForgotPasswordRequest;
import com.shadow.service.request.LoginRequest;
import com.shadow.service.response.CommonResponse;
import com.shadow.service.response.LoginResponse;
import com.shadow.util.GenerateRandomString;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	@Autowired
	private UserAuthDao userAuthDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRegistrationDao userRegistrationDao;
	@Autowired
	private EmailService emailService;

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		LoginResponse loginResponse = userAuthDao.login(loginRequest.getUsername());
		if (loginResponse == null
				|| !passwordEncoder.matches(loginRequest.getPassword(), loginResponse.getPassword())) {
			loginResponse = new LoginResponse();
			loginResponse.setSuccess(false);
			loginResponse.setResponseCreatedAt(new Date().getTime());
			loginResponse.setMessage(ResponseConstant.FAILURE);
			return loginResponse;
		}
		String authenticationToken = UUID.randomUUID().toString();
		boolean acknowldged = userAuthDao.saveAuthToken(loginRequest.getUsername(), authenticationToken);

		if (acknowldged) {
			loginResponse.setAuthenticationToken(authenticationToken);
		}
		loginResponse.setPassword(null);
		loginResponse.setMessage(ResponseConstant.SUCCESS);
		loginResponse.setSuccess(true);
		loginResponse.setResponseCreatedAt(new Date().getTime());
		return loginResponse;
	}

	@Override
	public CommonResponse logout(String authenticationToken) {
		// TODO Auto-generated method stub

		User user = userAuthDao.findUserByAuthenticationToken(authenticationToken);
		if (user == null) {
			return null;
		}
		user.setAuthenticationToken(null);
		userRegistrationDao.saveUser(user);
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setMessage(ResponseConstant.SUCCESS);
		commonResponse.setSuccess(true);
		commonResponse.setResponseCreatedAt(new Date().getTime());
		return commonResponse;
	}

	@Override
	public CommonResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
		// TODO Auto-generated method stub

		User user = userAuthDao.findUserByEmail(forgotPasswordRequest.getEmail().toLowerCase());
		if (user == null) {
			return null;
		}
		String temporaryPassword = GenerateRandomString.getRendomString();
		user.setPassword(passwordEncoder.encode(temporaryPassword));
		user.setPasswordNeedsToChange(true);
		userRegistrationDao.saveUser(user);
		String subject = "Password update";
		emailService.sendSimpleMessage(forgotPasswordRequest.getEmail().toLowerCase(), subject, temporaryPassword);
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setMessage("Password has been sent successfully on your email");
		commonResponse.setResponseCreatedAt(new Date().getTime());
		commonResponse.setSuccess(true);
		return commonResponse;
	}

	@Override
	public CommonResponse changePassword(ChangePasswordRequest changePasswordRequest,String authenticationToken) {
		
		
		
		User user = userAuthDao.findUserByAuthenticationToken(authenticationToken);
		CommonResponse commonResponse = null;
		if(user == null){
			return null;
		} else if(!passwordEncoder.matches(String.valueOf(changePasswordRequest.getOldPassword()), user.getPassword())) {
			commonResponse = CommonResponse.generateFailureResponse("Incorrect current password");
		} else if(!String.valueOf(changePasswordRequest.getNewPassword()).equals(String.valueOf(changePasswordRequest.getConfirmPassword()))) {
			commonResponse = CommonResponse.generateFailureResponse("New password and confirm password doesn't match!!");
		} else {
			user.setPassword(passwordEncoder.encode(String.valueOf(changePasswordRequest.getConfirmPassword())));
			user.setPasswordNeedsToChange(false);
			userRegistrationDao.saveUser(user);
			commonResponse = new CommonResponse();
			commonResponse.setMessage("Password updated successfully");
			commonResponse.setSuccess(true);
			commonResponse.setResponseCreatedAt(new Date().getTime());
		}
		return commonResponse;
	}

}
