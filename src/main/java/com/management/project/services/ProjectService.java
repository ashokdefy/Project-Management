package com.management.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.management.project.repository.ProjectRepository;
import com.management.project.dto.StatusData;
import com.management.project.dto.TimelineData;
import com.management.project.model.Project;

@Service
public class ProjectService {
	
	@Autowired
    ProjectRepository projectRepository;
	
	public Project save(Project project) {
		return projectRepository.save(project);
	}

	public List<StatusData> getProjectStatus() {
		return projectRepository.getProjectStatus();
	}
	
	public Project getProjectById(long id) {
		return projectRepository.findById(id).get();
	}
	
	public void deleteProject(Project project) {
		projectRepository.delete(project);
	}
	
	public Page<Project> getPaginatedProjects(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return projectRepository.findAll(pageable);
	}
	
	public Page<TimelineData> getPaginatedTimelinesData(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return projectRepository.getPaginatedProjectTimelines(pageable);
	}
}
