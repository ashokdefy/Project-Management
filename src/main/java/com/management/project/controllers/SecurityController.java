package com.management.project.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.management.project.repository.UserAccountsRepository;
import com.management.project.model.UserAccount;
import com.management.project.helper.Message;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {
	
	final UserAccountsRepository userAccountsRepository;
	
	final BCryptPasswordEncoder bCryptPasswordEncoder;

	public SecurityController(UserAccountsRepository userAccountsRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userAccountsRepository = userAccountsRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@GetMapping("/login")
	public String login(HttpSession httpSession, Model model, String error){
		httpSession.invalidate();
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");
		return "security/login-page";
	}

	@PostMapping("/login")
	public String processLogin() { return "redirect:/index";    }

	@GetMapping("/register")
	public String register(Model model) {
		UserAccount userAccount = new UserAccount();
		model.addAttribute("userAccount", userAccount);
		return "security/registration-page";
	}
	
	@PostMapping("/register/save")
	public String saveUser(@Valid UserAccount user, BindingResult result, Model model, HttpSession session) {
		
		
		if(user.getId() == 0 && !result.hasErrors()) {
			UserAccount userFromRepoForEmailVerification = userAccountsRepository.getUserByEmail(user.getEmail());
			UserAccount userFromRepoForUserNameVerification = userAccountsRepository.getUserByuserName(user.getUserName());
			if(userFromRepoForEmailVerification != null && userFromRepoForEmailVerification.getEmail().equals(user.getEmail())) {
				model.addAttribute("errorMessage", "User registration failed. This email address already belongs to an other user.");
				return "security/user-creation-error";
			}
			//Check if entered username already exists in DB.
			else if(userFromRepoForUserNameVerification != null && userFromRepoForUserNameVerification.getUserName().equals(user.getUserName())) {
				model.addAttribute("errorMessage", "User registration failed. This username already belongs to an other user.");
				return "security/user-creation-error";
			}
			else if(!user.getPassword().equals(user.getPasswordConfirm())){
				model.addAttribute("errorMessage", "Password doesn't match");
				return "security/user-creation-error";
			}
		}
		else if(result.hasErrors()) {
			return "security/user-creation-error";
		}
		if(user.getRole()!=null){
			if(user.getRole().equals("emp"))
				user.setRole("ROLE_EMPLOYEE");
			else if(user.getRole().equals("admin"))
				user.setRole("ROLE_ADMIN");
		}

		if(user.getRole().equals("ROLE_EMPLOYEE")){
			user.setEnabled(true);
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userAccountsRepository.save(user);
		session.setAttribute("message", new Message("success", "Registration successful"));
		return "redirect:/";
	}
}
