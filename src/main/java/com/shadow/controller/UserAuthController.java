package com.shadow.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.service.UserAuthService;
import com.shadow.service.request.ChangePasswordRequest;
import com.shadow.service.request.ForgotPasswordRequest;
import com.shadow.service.request.LoginRequest;
import com.shadow.service.response.CommonResponse;
import com.shadow.service.response.LoginResponse;

@RestController
public class UserAuthController {

	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private HttpServletRequest httpServletRequest;

	@PostMapping("/api/v1/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) throws InterruptedException {
		
		Thread.sleep(10000);

		return userAuthService.login(loginRequest);
	}

	@PostMapping("/api/v1/logout")
	public ResponseEntity<CommonResponse> logout() {

		if (StringUtils.isBlank(httpServletRequest.getHeader("authorization"))) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.generateFailureResponse());
		}

		CommonResponse commonResponse = userAuthService.logout(httpServletRequest.getHeader("authorization"));
		if (commonResponse == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.generateFailureResponse());
		}

		return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
	}

	@PostMapping("/api/v1/forgot_password")
	public ResponseEntity<CommonResponse> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {

		if (StringUtils.isBlank(forgotPasswordRequest.getEmail())) {
			CommonResponse commonResponse = CommonResponse.generateFailureResponse();
			commonResponse.setMessage("Email can't be empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
		}

		CommonResponse commonResponse = userAuthService.forgotPassword(forgotPasswordRequest);
		if (commonResponse == null) {
			commonResponse = CommonResponse.generateFailureResponse();
			commonResponse.setMessage("Email, you have entered doesn't match in our records");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
	}

	@PostMapping("/api/v1/change_password")
	public ResponseEntity<CommonResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {

		CommonResponse commonResponse = userAuthService.changePassword(changePasswordRequest,
				httpServletRequest.getHeader("authorization"));
		if (commonResponse == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.generateFailureResponse());
		}
		return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
	}

}
