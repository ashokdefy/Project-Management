package com.management.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.management.project.dto.StatusData;
import com.management.project.dto.TimelineData;
import com.management.project.model.Project;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

	String projectStatusQuery = "SELECT stage AS label, COUNT(stage) AS value FROM project GROUP BY stage";
	String projectTimeLineQuery = "SELECT name AS projectName, start_date AS startDate, end_date AS endDate FROM project";
	String countQuery = "SELECT COUNT(*) FROM project";

	@Override
	List<Project> findAll();
	
	@Query(nativeQuery = true, value = projectStatusQuery)
	List<StatusData> getProjectStatus();

	@Query(nativeQuery = true, countQuery = countQuery, value = projectTimeLineQuery)
	Page<TimelineData> getPaginatedProjectTimelines(Pageable pageable);
}
