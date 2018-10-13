package com.shadow.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.shadow.dao.UserRegistrationDao;
import com.shadow.domain.User;

@Repository
public class UserRegistrationDaoImpl implements UserRegistrationDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public User doRegistration(User user) {
		// TODO Auto-generated method stub
		mongoTemplate.save(user);
		return user;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		mongoTemplate.save(user);
		return user;
	}

}
