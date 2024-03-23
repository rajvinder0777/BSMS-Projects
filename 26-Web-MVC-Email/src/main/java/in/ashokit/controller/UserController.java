package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.entity.User;
import in.ashokit.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	// login page display

	@GetMapping("/form")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "index";
	}

	// submit/handle/ form request
	@PostMapping("/form")
	public String submitLogin(User user, Model model) {
		User userObj = userService.getUser(user.getEmail(), user.getPwd());

		if (userObj == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			return "index";
		} else {
			model.addAttribute("msg", userObj.getName() + ",Welcome to Ashok IT");
			return "dashboard";
		}

	}

	// Display register page

	@GetMapping("/register")
	public String register(Model model) {

		model.addAttribute("user", new User());
		return "registerView";
	}

	// submit register data
	@PostMapping("/register")
	public String submitRegister(Model model, User user) {

		boolean status = userService.saveUser(user);

		if (status) {
			model.addAttribute("smsg", "User Registered");

		} else {
			model.addAttribute("emsg", "Registration Failed");

		}
		return "registerView";
	}

	// logout - method
	@GetMapping("/logout")
	public String logout(Model model) {
		model.addAttribute("user", new User());
		return "index";
	}

}
