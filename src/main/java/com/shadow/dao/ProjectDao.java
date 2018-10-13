package com.shadow.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.shadow.domain.AuditProject;
import com.shadow.domain.Project;
import com.shadow.service.response.ProjectResponse;

public interface ProjectDao {
	
	Project saveProject(Project project);
	
	List<ProjectResponse> getProjects(ObjectId userId);
	
	Project findProjectById(String projectId);
	
	Project updateProject(Project project);
	
	AuditProject saveDeletedProject(AuditProject auditProject);
	
	boolean deleteProject(Project project);
	

}
