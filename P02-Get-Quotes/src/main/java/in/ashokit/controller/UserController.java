package in.ashokit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.ashokit.dto.LoginDto;
import in.ashokit.dto.RegisterDto;
import in.ashokit.dto.ResetPwdDto;
import in.ashokit.dto.UserDto;
import in.ashokit.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String registerPage(Model model) {

		model.addAttribute("regsiterDto", new RegisterDto());
		model.addAttribute("countries", userService.getCountries());
		// Map<Integer, String> countryMap = userService.getCountries();
		return "registerView";
	}
	
	
	
	@PostMapping("/register")
	public String regsiter(RegisterDto regDto, Model model) {

		UserDto user = userService.getUser(regDto.getEmail());
		
		if (user != null) {
			model.addAttribute("emsg", "Duplicate Email");
			return "registerView";
		}

		boolean regsterUser = userService.registerUser(regDto);
		if (regsterUser) {
			model.addAttribute("smsg", "User Registered");
		} else {
			model.addAttribute("emsg", "Registration Failed");
		}
		return "registerView";

		
		
	}
	

	@GetMapping("/states/{cid}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable("cid") Integer cid) {

		return userService.getStates(cid);
	}

	
	
	@GetMapping("cities/{sid}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable("sid") Integer sid) {

		return userService.getStates(sid);
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
	        return "resetPwdView";
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
	    // Check if new password and confirm password match
	    if (!pwdDto.getNewPwd().equals(pwdDto.getConfirmPwd())) {
	        model.addAttribute("emsg", "New Password and Confirm Password should be same");
	        return "resetPwdView";
	    }
	    // Retrieve user by email
	    UserDto user = userService.getUser(pwdDto.getEmail());
	    // Check if old password is correct
	    if (user.getPwd().equals(pwdDto.getOldPwd())) {
	    	boolean resetPwd = userService.resetPwd(pwdDto);
	    	if(resetPwd) {
	    		return "redirect:/dashboard";
	    	}
	    	else {
	    		model.addAttribute("emsg", "Password updated failed");
		        return "resetPwdView";
	    	}
	       // model.addAttribute("emsg", "Given old password is wrong");
	      //  return "resetPwdView";
	    }
	    else {
	    // Update password and set pwdUpdated to "YES"
	    	
	    	model.addAttribute("emsg", "Given old password is wrong");
	        return "resetPwdView";
	        
	  //  userService.resetPwd(pwdDto);
	 //   return "redirect:/dashboard";
	}

	}
	
	

	@GetMapping("/dashboard")
	public String dashboard(Model model) {

		String quote = userService.getQuote();
		model.addAttribute("quote", quote);
		return "dashboardView";
	}
	
	
	

	@GetMapping("/logout")
	public String logout() {
		return "redirect:/";
	}

}
