package in.sr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String showAboutPage(Model model) {
        model.addAttribute("ab", new AboutController());
    	return "about"; // This will map to about.html
    }
}