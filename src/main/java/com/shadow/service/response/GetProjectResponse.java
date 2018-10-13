package com.shadow.service.response;

import java.util.List;

import com.shadow.domain.Project;

public class GetProjectResponse extends CommonResponse{
	
	private List<ProjectResponse> projects;

	public List<ProjectResponse> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectResponse	> projects) {
		this.projects = projects;
	}
	
	

}
