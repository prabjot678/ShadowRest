package com.shadow.service;

import com.shadow.service.request.UserRegistrationRequest;
import com.shadow.service.response.UserRegistrationResponse;

public interface UserRegistrationService {
	
	UserRegistrationResponse doRegistration(UserRegistrationRequest userRegistrationRequest);

}
