package com.shadow.dao;

import com.shadow.domain.User;
import com.shadow.service.response.LoginResponse;

public interface UserAuthDao {
	
	LoginResponse login(String username);
	
	boolean saveAuthToken(String username,String authToken);
	
	boolean checkUserNameExist(String username,String email);
	
	User findUserById(String userId);
	
	User findUserByAuthenticationToken(String authenticationToken);
	
	User findUserByEmail(String email);

}
