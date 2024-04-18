package in.ashokit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.ashokit.dto.LoginDto;
import in.ashokit.dto.RegisterDto;
import in.ashokit.dto.ResetPwdDto;
import in.ashokit.dto.UserDto;
import in.ashokit.entity.CountryEntity;
import in.ashokit.entity.StateEntity;
import in.ashokit.service.UserService;
import in.ashokit.util.AppContents;
import in.ashokit.util.AppProperties;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AppProperties props;

	@GetMapping("/register")
	public String registerPage(Model model) {

		model.addAttribute("regsiterDto", new RegisterDto());
		model.addAttribute("countries", userService.getCountries());
		// Map<Integer, String> countryMap = userService.getCountries();
		return AppContents.REG_VIEW;
	}

	@PostMapping("/register")
	public String regsiter(RegisterDto regDto, Model model) {

		Map<String, String> messages = props.getMessages();

		UserDto user = userService.getUser(regDto.getEmail());

		if (user != null) {
			model.addAttribute(AppContents.ERROR_MSG, messages.get("dupEmail"));
			return AppContents.REG_VIEW;
		}

		boolean regsterUser = userService.registerUser(regDto);
		if (regsterUser) {
			model.addAttribute(AppContents.SUCC_MSG, messages.get("regSucc"));
		} else {
			model.addAttribute(AppContents.ERROR_MSG, messages.get("regFail"));
		}
		return AppContents.REG_VIEW;

	}

	@GetMapping("/states")
	@ResponseBody
	public Map<Integer, String> getStates(@RequestParam("countryId") CountryEntity countryId) {

		return userService.getStates(countryId);
	}

	@GetMapping("/cities")
	@ResponseBody
	public Map<Integer, String> getCities(@RequestParam("stateId") StateEntity stateId) {

		return userService.getCities(stateId);
	}

	@GetMapping("/")
	public String loginPage(Model model) {
		model.addAttribute("loginDto", new LoginDto());
		return "login"; // Return "login" instead of "index"
	}

	/*
	 * @PostMapping("/login") public String login(LoginDto loginDto, Model model) {
	 * 
	 * UserDto user = userService.getUser(loginDto); if (user == null) { // Handle
	 * null user case, such as displaying an error message
	 * model.addAttribute("emsg", "Invalid Credentials"); return "login"; }
	 * 
	 * if ("YES".equals(user.getPwUpdated())) { // pwd already updated => Dashboard
	 * return "redirect:dashboard"; } else { // pwd already updated => Dashboard
	 * ResetPwdDto resetPwdDto = new ResetPwdDto();
	 * resetPwdDto.setEmail(user.getEmail());
	 * 
	 * model.addAttribute("resetPwdDto", new ResetPwdDto()); return "resetPwdView";
	 * }
	 * 
	 * 
	 * 
	 * }
	 * 
	 * @GetMapping("/resetPwd") public String resetPwdView(Model model) {
	 * 
	 * model.addAttribute("resetPwdDto", new ResetPwdDto());
	 * 
	 * return "resetPwdView"; }
	 */

	/*
	 * @PostMapping("/resetPwd") public String resetPwd(ResetPwdDto pwdDto, Model
	 * model) {
	 * 
	 * // Checking passwords if (!pwdDto.getNewPwd().equals(pwdDto.getConfirmPwd()))
	 * { model.addAttribute("emsg",
	 * "New Password and Confirm Password should be same"); return "resetPwdView"; }
	 * 
	 * // Retrieving user UserDto user = userService.getUser(pwdDto.getEmail());
	 * 
	 * // Handling null user case if (user == null) { model.addAttribute("emsg",
	 * "User not found"); return "resetPwdView"; }
	 * 
	 * // Validating old password if (!user.getPwd().equals(pwdDto.getOldPwd())) {
	 * model.addAttribute("emsg", "Given old password is wrong"); return
	 * "resetPwdView"; }
	 * 
	 * // Resetting password boolean resetPwd = userService.resetPwd(pwdDto);
	 * 
	 * // Handling password reset result if (resetPwd) { return
	 * "redirect:dashboard"; } else { model.addAttribute("emsg",
	 * "Password update failed"); return "resetPwdView"; } }
	 */

	@PostMapping("/login")
	public String login(LoginDto loginDto, Model model) {

		Map<String, String> messages = props.getMessages();

		UserDto user = userService.getUser(loginDto);
		if (user == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			return "login";
		}

		if ("YES".equals(user.getPwUpdated())) {
			// Password already updated, redirect to dashboard
			return "redirect:/dashboard";

		} else {

			ResetPwdDto resetPwdDto = new ResetPwdDto();
			resetPwdDto.setEmail(user.getEmail());
			model.addAttribute("resetPwdDto", resetPwdDto);
			return AppContents.RESET_PWD_VIEW;
		}
	}

	/*
	 * @GetMapping("/resetPwd") public String resetPwdView(@RequestParam String
	 * email, Model model) { // Add email to model model.addAttribute("email",
	 * email); // Add resetPwdDto to model model.addAttribute("resetPwdDto", new
	 * ResetPwdDto()); return "resetPwdView"; }
	 */

	@PostMapping("/resetPwd")
	public String resetPwd(ResetPwdDto pwdDto, Model model) {

		Map<String, String> messages = props.getMessages();

		// Check if new password and confirm password match
		if (!pwdDto.getNewPwd().equals(pwdDto.getConfirmPwd())) {
			model.addAttribute(AppContents.ERROR_MSG, messages.get("pwdMatchErr"));
			return AppContents.RESET_PWD_VIEW;
		}
		// Retrieve user by email
		UserDto user = userService.getUser(pwdDto.getEmail());
		// Check if old password is correct
		if (user.getPwd().equals(pwdDto.getOldPwd())) {
			boolean resetPwd = userService.resetPwd(pwdDto);
			if (resetPwd) {
				return "redirect:/dashboard";
			} else {
				model.addAttribute(AppContents.ERROR_MSG, messages.get("pwdUpdateErr"));
				return AppContents.RESET_PWD_VIEW;
			}
			// model.addAttribute("emsg", "Given old password is wrong");
			// return "resetPwdView";
		} else {
			// Update password and set pwdUpdated to "YES"

			model.addAttribute(AppContents.ERROR_MSG, messages.get("oldPwdErro"));
			return AppContents.RESET_PWD_VIEW;

			// userService.resetPwd(pwdDto);
			// return "redirect:/dashboard";
		}

	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {

		String quote = userService.getQuote();
		model.addAttribute("quote", quote);
		return AppContents.DASHBOARD_VIEW;
	}

	@GetMapping("/logout")
	public String logout() {
		return "redirect:/";
	}

}
