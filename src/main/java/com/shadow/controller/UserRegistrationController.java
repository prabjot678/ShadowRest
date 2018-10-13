package com.shadow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.service.UserRegistrationService;
import com.shadow.service.request.UserRegistrationRequest;
import com.shadow.service.response.UserRegistrationResponse;

@RestController
public class UserRegistrationController {
	
	@Autowired 
	private UserRegistrationService userRegistrationService;
	
	@PostMapping("/api/v1/user_registration")
	public UserRegistrationResponse doRegistration(@RequestBody UserRegistrationRequest userRegistrationRequest){
		
		return userRegistrationService.doRegistration(userRegistrationRequest);
	}

}
