package com.shadow.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User extends BaseEntity {
	
	private String username;
	private String password;
	private String authenticationToken;
	private String firstName;
	private String lastName;
	private boolean passwordNeedsToChange;
	
	@Id
	private String Id;	
	
	private String email;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAuthenticationToken() {
		return authenticationToken;
	}
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isPasswordNeedsToChange() {
		return passwordNeedsToChange;
	}
	public void setPasswordNeedsToChange(boolean passwordNeedsToChange) {
		this.passwordNeedsToChange = passwordNeedsToChange;
	}
	
}
