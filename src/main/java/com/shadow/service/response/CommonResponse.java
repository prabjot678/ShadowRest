package com.shadow.service.response;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.shadow.constant.ResponseConstant;

public class CommonResponse {

	private boolean success;
	private String message = ResponseConstant.SUCCESS;
	private Long responseCreatedAt = new Date().getTime();
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date lastModified;

	

	

	public Date getCreatedAt() {
		return createdAt;
	}



	public Date getLastModified() {
		return lastModified;
	}



	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public boolean isSuccess() {
		return success;
	}

	public Long getResponseCreatedAt() {
		return responseCreatedAt;
	}

	public void setResponseCreatedAt(Long responseCreatedAt) {
		this.responseCreatedAt = responseCreatedAt;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static CommonResponse generateFailureResponse(){
		CommonResponse failureResponse = new CommonResponse();
		failureResponse.setSuccess(false);
		failureResponse.setResponseCreatedAt(new Date().getTime());
		failureResponse.setMessage(ResponseConstant.FAILURE);
		return failureResponse;
	}
	
	public static CommonResponse generateFailureResponse(String message){
		CommonResponse failureResponse = new CommonResponse();
		failureResponse.setSuccess(false);
		failureResponse.setResponseCreatedAt(new Date().getTime());
		failureResponse.setMessage(message);
		return failureResponse;
	}

}
