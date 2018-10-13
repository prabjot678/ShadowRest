package com.shadow.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class AuditProject  {
	
	@Id private String id;
	private String name;
	private String description;
	private ObjectId userId;
	private String mongoProjectId;
	private Date createdOn;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ObjectId getUserId() {
		return userId;
	}
	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
	public String getMongoProjectId() {
		return mongoProjectId;
	}
	public void setMongoProjectId(String mongoProjectId) {
		this.mongoProjectId = mongoProjectId;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
}
