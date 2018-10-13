package com.shadow.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.shadow.dao.ProjectDao;
import com.shadow.domain.AuditProject;
import com.shadow.domain.Project;
import com.shadow.service.response.ProjectResponse;

@Repository
public class ProjectDaoImpl implements ProjectDao {
	
	@Autowired private MongoTemplate mongoTemplate;

	@Override
	public Project saveProject(Project project) {
		// TODO Auto-generated method stub
		mongoTemplate.save(project);
		return project;
	}

	@Override
	public List<ProjectResponse> getProjects(ObjectId userId) {
		// TODO Auto-generated method stub
		Query query = Query.query(Criteria.where("userId").is(userId));
		return mongoTemplate.find(query, ProjectResponse.class, "project");
	}

	@Override
	public Project findProjectById(String projectId) {
		// TODO Auto-generated method stub
		Query query = Query.query(Criteria.where("id").is(projectId));
		return mongoTemplate.findOne(query, Project.class);
	}

	@Override
	public Project updateProject(Project project) {
		// TODO Auto-generated method stub
		mongoTemplate.save(project);
		return project;
	}

	@Override
	public AuditProject saveDeletedProject(AuditProject auditProject) {
		// TODO Auto-generated method stub
		mongoTemplate.save(auditProject);
		return auditProject;
	}

	@Override
	public boolean deleteProject(Project project) {
		// TODO Auto-generated method stub
		DeleteResult deleteResult= mongoTemplate.remove(project);
		return deleteResult.wasAcknowledged();
	}
	
	

}
