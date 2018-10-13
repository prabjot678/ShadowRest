package com.shadow.service.response;

public class LoginResponse extends CommonResponse {

	private String authenticationToken;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private boolean passwordNeedsToChange;

	public boolean isPasswordNeedsToChange() {
		return passwordNeedsToChange;
	}

	public void setPasswordNeedsToChange(boolean passwordNeedsToChange) {
		this.passwordNeedsToChange = passwordNeedsToChange;
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

	@Override
	public String toString() {
		return "LoginResponse [username=" + username + "]";
	}

}
