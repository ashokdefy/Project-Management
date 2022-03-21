package com.management.project.repository;

import java.util.List;

import com.management.project.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.management.project.dto.EmployeeProject;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

	String employeeQuery = "SELECT e.first_name AS firstName, e.last_name AS lastName, COUNT(pe.employee_id) AS projectCount "
			+ "from employee e LEFT JOIN project_employee pe on e.employee_id = pe.employee_id "
			+ "GROUP BY e.first_name, e.last_name "
			+ "ORDER BY projectCount DESC";

	String countQuery = "SELECT COUNT(*) FROM employee";

	List<Employee> findAll();
	
	Employee findByEmail(String email);

	@Query(nativeQuery = true, countQuery = countQuery, value = employeeQuery)
	Page<EmployeeProject> employeeProjectsPaginated(Pageable pageable);
}
