package com.shadow.service.request;

public class ChangePasswordRequest {
	
	private char[] oldPassword;
	private char[] newPassword;
	private char[] confirmPassword;
	
	public char[] getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(char[] oldPassword) {
		this.oldPassword = oldPassword;
	}
	public char[] getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(char[] newPassword) {
		this.newPassword = newPassword;
	}
	public char[] getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(char[] confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	

}
