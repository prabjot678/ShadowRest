package com.shadow.dao;

import com.shadow.domain.User;

public interface UserRegistrationDao {
	
	User doRegistration(User user);
	
	User saveUser(User user);

}
