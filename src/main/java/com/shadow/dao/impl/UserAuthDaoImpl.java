package com.shadow.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;
import com.shadow.dao.UserAuthDao;
import com.shadow.domain.User;
import com.shadow.service.response.LoginResponse;

@Repository
public class UserAuthDaoImpl implements UserAuthDao {
	
	@Autowired private MongoTemplate mongoTemplate;
	
	
	@Override
	public LoginResponse login(String username) {
		// TODO Auto-generated method stub
		
		Query query = Query.query(Criteria.where("username").is(username));
		return mongoTemplate.findOne(query, LoginResponse.class, "user");
	}


	@Override
	public boolean saveAuthToken(String username,String authToken) {
		
		Query query = Query.query(Criteria.where("username").is(username));
		
		Update update = new Update();
		update.set("authenticationToken", authToken);
		UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
		return updateResult.wasAcknowledged();
	}


	@Override
	public boolean checkUserNameExist(String username,String email) {
		// TODO Auto-generated method stub
		Query query = Query.query(new Criteria().orOperator(Criteria.where("username").is(username),Criteria.where("email").is(email)));
		return mongoTemplate.exists(query, User.class);
	}


	@Override
	public User findUserById(String userId) {
		// TODO Auto-generated method stub
		return mongoTemplate.findById(userId, User.class);
	}
	
	@Override
	public User findUserByAuthenticationToken(String authenticationToken) {
		// TODO Auto-generated method stub
		Query query = Query.query(Criteria.where("authenticationToken").is(authenticationToken));
		return mongoTemplate.findOne(query, User.class);
	}


	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		Query query = Query.query(Criteria.where("email").is(email));
		return mongoTemplate.findOne(query, User.class);
	}

}
