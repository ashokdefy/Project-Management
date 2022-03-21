package com.management.project.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private long id;
	
	@NotBlank(message = "Project name can't be empty")
	@Size(min = 5, max = 30, message = "Project name must lie between {min} to {max} characters")
	private String name;
	
	@NotNull
	private String stage;
	
	@NotBlank(message = "Project description can't be empty")
	@Size(min = 10, max = 200, message = "Project description must lie between {min} to {max} characters")
	private String description;
	
	@NotNull(message = "Start date can't be empty")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@NotNull(message = "End date can't be empty")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
			   fetch = FetchType.LAZY)
	@JoinTable(name = "project_employee",
			   joinColumns = @JoinColumn(name = "project_id"),
			   inverseJoinColumns = @JoinColumn(name = "employee_id"))
	@JsonIgnore
	@NotEmpty(message = "Must assign at least one employee to a project")
	private List<Employee> employees;

	//Convenience method
	public void addEmployee(Employee emp) {
		if(employees == null) {
			employees = new ArrayList<>();
		}
		employees.add(emp);
	}

}
