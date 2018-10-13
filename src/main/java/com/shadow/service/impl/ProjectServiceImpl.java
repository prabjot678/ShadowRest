package com.shadow.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shadow.constant.CommonConstant;
import com.shadow.constant.ResponseConstant;
import com.shadow.dao.ProjectDao;
import com.shadow.dao.UserAuthDao;
import com.shadow.domain.Attachment;
import com.shadow.domain.AuditProject;
import com.shadow.domain.Project;
import com.shadow.domain.User;
import com.shadow.service.ProjectService;
import com.shadow.service.request.ProjectRequest;
import com.shadow.service.request.UpdateProjectRequest;
import com.shadow.service.request.UpdateProjectResponse;
import com.shadow.service.response.AttachmentResponse;
import com.shadow.service.response.CommonResponse;
import com.shadow.service.response.GetAttachmentResponse;
import com.shadow.service.response.GetProjectResponse;
import com.shadow.service.response.ProjectResponse;
import com.shadow.util.DateFormat;
import com.shadow.util.GenerateRandomString;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private UserAuthDao userAuthDao;
	@Autowired
	private ProjectDao projectDao;

	@Value("${project.docs.dev.path}")
	private String docPath;

	@Override
	public ProjectResponse saveProject(ProjectRequest projectRequest, String authenticationToken) {
		// TODO Auto-generated method stub
		User user = userAuthDao.findUserByAuthenticationToken(authenticationToken);
		if (user == null) {
			ProjectResponse projectResponse = new ProjectResponse();
			projectResponse.setMessage("Invalid user");
			projectResponse.setSuccess(false);
			projectResponse.setResponseCreatedAt(new Date().getTime());
			return projectResponse;
		}

		Project project = new Project();
		project.setUserId(new ObjectId(user.getId()));
		project.setDescription(projectRequest.getDescription());
		project.setName(projectRequest.getName());
		projectDao.saveProject(project);

		ProjectResponse projectResponse = new ProjectResponse();
		projectResponse.setDescription(project.getDescription());
		projectResponse.setName(project.getName());
		projectResponse.setId(project.getId());
		projectResponse.setSuccess(true);
		projectResponse.setMessage(ResponseConstant.SUCCESS);
		return projectResponse;
	}

	@Override
	public GetProjectResponse getProjects(String authenticationToken) {
		// TODO Auto-generated method stub

		User user = userAuthDao.findUserByAuthenticationToken(authenticationToken);
		if (user == null) {
			GetProjectResponse getProjectResponse = new GetProjectResponse();
			getProjectResponse.setProjects(Collections.emptyList());
			getProjectResponse.setMessage("Invalid user");
			getProjectResponse.setSuccess(false);
			getProjectResponse.setResponseCreatedAt(new Date().getTime());
			return getProjectResponse;
		}

		List<ProjectResponse> projects = projectDao.getProjects(new ObjectId(user.getId()));
		GetProjectResponse getProjectResponse = new GetProjectResponse();
		getProjectResponse.setProjects(projects);
		getProjectResponse.setMessage(ResponseConstant.SUCCESS);
		getProjectResponse.setSuccess(true);
		getProjectResponse.setResponseCreatedAt(new Date().getTime());
		return getProjectResponse;
	}

	@Override
	public UpdateProjectResponse updateProject(UpdateProjectRequest updateProjectRequest) {
		// TODO Auto-generated method stub

		Project project = projectDao.findProjectById(updateProjectRequest.getId());
		if (project == null) {
			return null;
		}

		if (StringUtils.isNotBlank(updateProjectRequest.getDescription())) {
			project.setDescription(updateProjectRequest.getDescription());
			projectDao.saveProject(project);
		}
		UpdateProjectResponse updateProjectResponse = new UpdateProjectResponse();
		updateProjectResponse.setMessage(ResponseConstant.SUCCESS);
		updateProjectResponse.setResponseCreatedAt(new Date().getTime());
		updateProjectResponse.setDescription(project.getDescription());
		updateProjectResponse.setId(project.getId());
		updateProjectResponse.setSuccess(true);
		return updateProjectResponse;
	}

	@Override
	public CommonResponse deleteProject(String projectId) {
		// TODO Auto-generated method stub
		Project project = projectDao.findProjectById(projectId);
		if (project == null) {
			return null;
		}

		// audit project
		AuditProject auditProject = new AuditProject();
		auditProject.setCreatedOn(new Date());
		auditProject.setDescription(project.getDescription());
		auditProject.setMongoProjectId(project.getId());
		auditProject.setName(project.getName());
		auditProject.setUserId(project.getUserId());

		// delete project
		boolean deleted = projectDao.deleteProject(project);

		CommonResponse commonResponse = new CommonResponse();
		if (deleted) {
			projectDao.saveDeletedProject(auditProject);
			commonResponse.setSuccess(true);
			commonResponse.setMessage(ResponseConstant.SUCCESS);
		} else {
			commonResponse.setSuccess(false);
			commonResponse.setMessage(ResponseConstant.SUCCESS);
		}

		commonResponse.setResponseCreatedAt(new Date().getTime());
		return commonResponse;
	}

	@Override
	public UpdateProjectResponse updateProjectName(UpdateProjectRequest updateProjectRequest) {
		// TODO Auto-generated method stub
		Project project = projectDao.findProjectById(updateProjectRequest.getId());
		if (project == null) {
			return null;
		}

		if (StringUtils.isNotBlank(updateProjectRequest.getName())) {
			project.setName(updateProjectRequest.getName());
			projectDao.saveProject(project);
		}
		UpdateProjectResponse updateProjectResponse = new UpdateProjectResponse();
		updateProjectResponse.setMessage(ResponseConstant.SUCCESS);
		updateProjectResponse.setResponseCreatedAt(new Date().getTime());
		updateProjectResponse.setName(project.getName());
		updateProjectResponse.setId(project.getId());
		updateProjectResponse.setSuccess(true);
		return updateProjectResponse;
	}

	@Override
	public CommonResponse saveDocument(String authenticationToken, String projectId, MultipartFile multipartFile) {
		// TODO Auto-generated method stub

		User user = userAuthDao.findUserByAuthenticationToken(authenticationToken);
		if (user == null) {
			ProjectResponse projectResponse = new ProjectResponse();
			projectResponse.setMessage("Invalid user");
			projectResponse.setSuccess(false);
			projectResponse.setResponseCreatedAt(new Date().getTime());
			return projectResponse;
		}

		Project project = projectDao.findProjectById(projectId);
		CommonResponse commonResponse = new CommonResponse();
		if (project == null) {
			commonResponse.setMessage(ResponseConstant.FAILURE);
			commonResponse.setSuccess(true);
			return commonResponse;
		}

		String message = ResponseConstant.SUCCESS;
		boolean fileUpload = true;

		try {
			byte[] bytes = multipartFile.getBytes();

			final String absoultePath = CommonConstant.ATTACHMENT_FILE_PATH + user.getEmail() + File.separator;
			String uniqueFileName = GenerateRandomString.getRendomFourDigitString()
					+ multipartFile.getOriginalFilename();
			createDirectoryIfNotExist(absoultePath);
			Path path = Paths.get(absoultePath + uniqueFileName);
			Files.write(path, bytes);
			commonResponse.setSuccess(true);
			message = "File upload successfully";

			Attachment attachment = new Attachment();
			attachment.setName(multipartFile.getOriginalFilename());
			attachment.setUniqueName(uniqueFileName);

			File file = path.toFile();
			long lengthOfFile = file.length();
			long sizeInKB = lengthOfFile / 1024;
			if (sizeInKB < 1024) {
				attachment.setSize(sizeInKB + " KB");
			} else {

				attachment.setSize(sizeInKB / 1024 + " MB");
			}
			attachment.setId(new ObjectId().toHexString());
			attachment.setCreated(new Date());
			attachment.setLastModified(new Date());
			List<Attachment> attachments = project.getAttachments();
			attachments.add(attachment);
			project.setAttachments(attachments);
			projectDao.saveProject(project);
		} catch (IOException ioException) {
			message = ioException.getMessage();
			fileUpload = false;
		} catch (Exception exception) {
			message = exception.getMessage();
			fileUpload = false;
		}
		commonResponse.setMessage(message);
		commonResponse.setSuccess(fileUpload);
		return commonResponse;

	}

	private void createDirectoryIfNotExist(String filePath) {

		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	@Override
	public GetAttachmentResponse getAttachments(String authToken, String projectId) {
		// TODO Auto-generated method stub
		User user = userAuthDao.findUserByAuthenticationToken(authToken);
		if (user == null) {
			GetAttachmentResponse getAttachmentResponse = new GetAttachmentResponse();
			getAttachmentResponse.setMessage("Invalid user");
			getAttachmentResponse.setSuccess(false);
			getAttachmentResponse.setResponseCreatedAt(new Date().getTime());
			return getAttachmentResponse;
		}

		Project project = projectDao.findProjectById(projectId);
		if (project == null) {
			GetAttachmentResponse getAttachmentResponse = new GetAttachmentResponse();
			getAttachmentResponse.setMessage("Invalid Project id");
			getAttachmentResponse.setSuccess(false);
			getAttachmentResponse.setResponseCreatedAt(new Date().getTime());
			return getAttachmentResponse;
		}

		List<Attachment> attachments = project.getAttachments();
		List<AttachmentResponse> attachmentResponses = new ArrayList<>(attachments.size());
		attachments.forEach(attachment -> {
			AttachmentResponse attachmentResponse = new AttachmentResponse();
			attachmentResponse.setName(attachment.getName());
			attachmentResponse.setURL(docPath + user.getEmail() + File.separator + attachment.getUniqueName());
			attachmentResponse.setAttachmentUploadedDate(DateFormat.getDate(attachment.getCreated(),ResponseConstant.DATE_MINUTE_HOURE_FORMAT));
			attachmentResponse.setAttachmentLastModifiedDate(DateFormat.getDate(attachment.getLastModified(),ResponseConstant.DATE_MINUTE_HOURE_FORMAT));
			attachmentResponse.setSize(attachment.getSize());
			attachmentResponse.setId(attachment.getId());
			attachmentResponses.add(attachmentResponse);
		});

		GetAttachmentResponse getAttachmentResponse = new GetAttachmentResponse();
		getAttachmentResponse.setAttachments(attachmentResponses);
		getAttachmentResponse.setSuccess(true);
		return getAttachmentResponse;

	}

}
