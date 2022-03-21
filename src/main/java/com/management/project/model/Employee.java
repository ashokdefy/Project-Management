package com.management.project.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private long id;
	
	@NotBlank(message = "Must enter the employee's first name")
	@Size(min = 2, max = 50, message = "Employee first name must lie between {min} to {max} characters")
	private String firstName;
	
	@NotBlank(message = "Must enter the employee's last name")
	@Size(min = 1, max = 50, message = "Employee last name must lie between {min} to {max} characters")
	private String lastName;

	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Must be a valid email address")
	private String email;

	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
			   fetch = FetchType.LAZY)
	@JoinTable(name = "project_employee",
			   joinColumns = @JoinColumn(name = "employee_id"),
			   inverseJoinColumns = @JoinColumn(name = "project_id"))
	@JsonIgnore
	private List<Project> projects;    //Many employees can be assigned to many projects.


	
}
