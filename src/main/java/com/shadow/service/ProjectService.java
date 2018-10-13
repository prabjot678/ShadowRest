package com.shadow.service;

import org.springframework.web.multipart.MultipartFile;

import com.shadow.service.request.ProjectRequest;
import com.shadow.service.request.UpdateProjectRequest;
import com.shadow.service.request.UpdateProjectResponse;
import com.shadow.service.response.CommonResponse;
import com.shadow.service.response.GetAttachmentResponse;
import com.shadow.service.response.GetProjectResponse;
import com.shadow.service.response.ProjectResponse;

public interface ProjectService {
	
	ProjectResponse saveProject(ProjectRequest projectRequest,String authenticationToken);
	
	GetProjectResponse getProjects(String authenticationToken);
	
	UpdateProjectResponse updateProject(UpdateProjectRequest updateProjectRequest);
	
	CommonResponse deleteProject(String projectId);
	
	UpdateProjectResponse updateProjectName(UpdateProjectRequest updateProjectRequest);
	
	CommonResponse saveDocument(String authToken,String projectId,MultipartFile multipartFile);
	
	GetAttachmentResponse getAttachments(String authToken,String projectId);
	
	
}
