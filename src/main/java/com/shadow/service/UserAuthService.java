package com.shadow.service;

import com.shadow.service.request.ChangePasswordRequest;
import com.shadow.service.request.ForgotPasswordRequest;
import com.shadow.service.request.LoginRequest;
import com.shadow.service.response.CommonResponse;
import com.shadow.service.response.LoginResponse;

public interface UserAuthService {
	
	LoginResponse login(LoginRequest loginRequest);
	
	CommonResponse logout(String authenticationToken);
	
	CommonResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
	
	CommonResponse changePassword(ChangePasswordRequest changePasswordRequest,String authenticationToken);

}
