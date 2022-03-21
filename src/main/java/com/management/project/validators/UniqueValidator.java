package com.management.project.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.management.project.repository.EmployeeRepository;
import com.management.project.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueValidator implements ConstraintValidator<UniqueValue, String> {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		Employee emp = employeeRepository.findByEmail(value);
		return emp == null;
	}

}
