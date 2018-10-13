package com.shadow.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shadow.service.ProjectService;
import com.shadow.service.request.ProjectRequest;
import com.shadow.service.request.UpdateProjectRequest;
import com.shadow.service.request.UpdateProjectResponse;
import com.shadow.service.response.CommonResponse;
import com.shadow.service.response.GetAttachmentResponse;
import com.shadow.service.response.GetProjectResponse;
import com.shadow.service.response.ProjectResponse;

@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	HttpServletRequest httpServletRequest;

	@PostMapping("/api/v1/project")
	public ResponseEntity<ProjectResponse> saveProject(@RequestBody ProjectRequest projectRequest) {

		if (StringUtils.isBlank(httpServletRequest.getHeader("authorization"))) {
			ProjectResponse projectResponse = new ProjectResponse();
			projectResponse.setSuccess(false);
			projectResponse.setResponseCreatedAt(new Date().getTime());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(projectResponse);
		}
		ProjectResponse projectResponse = projectService.saveProject(projectRequest,
				httpServletRequest.getHeader("authorization"));
		return ResponseEntity.ok().body(projectResponse);
	}

	@GetMapping("/api/v1/project")
	public ResponseEntity<GetProjectResponse> getProjects() {
		if (StringUtils.isBlank(httpServletRequest.getHeader("authorization"))) {
			GetProjectResponse projectResponse = new GetProjectResponse();
			projectResponse.setSuccess(false);
			projectResponse.setResponseCreatedAt(new Date().getTime());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(projectResponse);
		}
		GetProjectResponse projectResponse = projectService.getProjects(httpServletRequest.getHeader("authorization"));
		return ResponseEntity.ok().body(projectResponse);

	}

	@PutMapping("/api/v1/project")
	public ResponseEntity<UpdateProjectResponse> updateProject(@RequestBody UpdateProjectRequest updateProjectRequest) {

		if (StringUtils.isBlank(updateProjectRequest.getId())) {
			UpdateProjectResponse updateProjectResponse = new UpdateProjectResponse();
			updateProjectResponse.setSuccess(false);
			updateProjectResponse.setMessage("Bad Request");
			updateProjectResponse.setResponseCreatedAt(new Date().getTime());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateProjectResponse);
		}

		UpdateProjectResponse updateProjectResponse = projectService.updateProject(updateProjectRequest);
		if (updateProjectResponse == null) {
			updateProjectResponse = new UpdateProjectResponse();
			updateProjectResponse.setSuccess(false);
			updateProjectResponse.setMessage("Project not found");
			updateProjectResponse.setResponseCreatedAt(new Date().getTime());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updateProjectResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(updateProjectResponse);
	}

	@DeleteMapping("/api/v1/project")
	public ResponseEntity<CommonResponse> deleteProject() {

		if (StringUtils.isBlank(httpServletRequest.getHeader("id"))) {
			CommonResponse commonResponse = CommonResponse.generateFailureResponse();
			commonResponse.setMessage("Invalid project id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
		}

		CommonResponse commonResponse = projectService.deleteProject(httpServletRequest.getHeader("id"));
		if (commonResponse == null) {
			commonResponse = CommonResponse.generateFailureResponse();
			commonResponse.setMessage("Invalid project id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
	}

	@PutMapping("/api/v1/project_name")
	public ResponseEntity<UpdateProjectResponse> updateProjectName(
			@RequestBody UpdateProjectRequest updateProjectRequest) {

		if (StringUtils.isBlank(updateProjectRequest.getId())) {
			UpdateProjectResponse updateProjectResponse = new UpdateProjectResponse();
			updateProjectResponse.setSuccess(false);
			updateProjectResponse.setMessage("Bad Request");
			updateProjectResponse.setResponseCreatedAt(new Date().getTime());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateProjectResponse);
		}

		UpdateProjectResponse updateProjectResponse = projectService.updateProjectName(updateProjectRequest);
		if (updateProjectResponse == null) {
			updateProjectResponse = new UpdateProjectResponse();
			updateProjectResponse.setSuccess(false);
			updateProjectResponse.setMessage("Project not found");
			updateProjectResponse.setResponseCreatedAt(new Date().getTime());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updateProjectResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(updateProjectResponse);
	}

	@PostMapping("/api/v1/project/{projectId}/document")
	public ResponseEntity<CommonResponse> uploadDocument(@PathVariable String projectId,
			@RequestParam("file") MultipartFile multipartFile) throws InterruptedException {
		
		Thread.sleep(5000);

		if (StringUtils.isBlank(httpServletRequest.getHeader("authorization"))) {
			ProjectResponse projectResponse = new ProjectResponse();
			projectResponse.setSuccess(false);
			projectResponse.setResponseCreatedAt(new Date().getTime());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(projectResponse);
		}

		CommonResponse commonResponse = projectService.saveDocument(httpServletRequest.getHeader("authorization"),
				projectId, multipartFile);
		return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
	}

	@GetMapping("/api/v1/project/{projectId}/document")
	public ResponseEntity<CommonResponse> getAttachments(@PathVariable String projectId) {

		if (StringUtils.isBlank(httpServletRequest.getHeader("authorization"))) {
			ProjectResponse projectResponse = new ProjectResponse();
			projectResponse.setSuccess(false);
			projectResponse.setResponseCreatedAt(new Date().getTime());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(projectResponse);
		}

		GetAttachmentResponse getAttachmentResponse = projectService
				.getAttachments(httpServletRequest.getHeader("authorization"), projectId);

		return ResponseEntity.status(HttpStatus.OK).body(getAttachmentResponse);
	}

}
