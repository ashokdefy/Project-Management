package com.management.project.controllers;

import java.util.List;

import com.management.project.dto.EmployeeProject;
import com.management.project.dto.StatusData;
import com.management.project.model.Project;
import com.management.project.services.EmployeeService;
import com.management.project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {

    final ProjectService projectService;

    final EmployeeService employeeService;

    @Value("${homeProjectPageSize}")
    int homeProjectPageSize;

    @Value("${homeEmployeesProjectsCntPageSize}")
    int homeEmployeesProjectsCntPageSize;

    public HomeController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String displayHome(Model model) throws JsonProcessingException {
        return displayPaginatedHome(1, 1, model);
    }

    @GetMapping("/page")
    public String displayPaginatedHome(@RequestParam("homeEmployeesProjectsCntPageNo") int homeEmployeesProjectsCntPageNo, @RequestParam("homeProjectPageNo") int homeProjectPageNo, Model model) throws JsonProcessingException {

        Page<Project> homeProjectPage = projectService.getPaginatedProjects(homeProjectPageNo, homeProjectPageSize);
        List<Project> projects = homeProjectPage.getContent();

        List<StatusData> projectData = projectService.getProjectStatus();
        ObjectMapper objectMapper = new ObjectMapper();
        String projectDatajsonString = objectMapper.writeValueAsString(projectData);

        Page<EmployeeProject> homeEmployeesProjectsCntPage = employeeService.getEmployeeProjectsPaginated(homeEmployeesProjectsCntPageNo, homeEmployeesProjectsCntPageSize);
        List<EmployeeProject> employeesProjectsCnt = homeEmployeesProjectsCntPage.getContent();

        model.addAttribute("projects", projects);
        model.addAttribute("projectStatusCnt", projectDatajsonString);
        model.addAttribute("employeesListProjectsCnt", employeesProjectsCnt);

        model.addAttribute("currentHomeProjectPage", homeProjectPageNo);
        model.addAttribute("totalHomeProjectPages", homeProjectPage.getTotalPages());
        model.addAttribute("totalHomeProjectItems", homeProjectPage.getTotalElements());
        model.addAttribute("totalHomeProjectItemsInPage", homeProjectPage.getSize());

        model.addAttribute("currentHomeEmployeesProjectsCntPage", homeEmployeesProjectsCntPageNo);
        model.addAttribute("totalHomeEmployeesProjectsCntPages", homeEmployeesProjectsCntPage.getTotalPages());
        model.addAttribute("totalHomeEmployeesProjectsCntItems", homeEmployeesProjectsCntPage.getTotalElements());
        model.addAttribute("totalHomeEmployeesProjectsCntItemsInPage", homeEmployeesProjectsCntPage.getSize());

        return "main/home";
    }
}
