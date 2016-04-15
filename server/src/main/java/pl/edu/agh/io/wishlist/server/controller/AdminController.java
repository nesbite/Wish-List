package pl.edu.agh.io.wishlist.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping("/dashboard")
	public ModelAndView getDashboardPage(){
		return new ModelAndView("dashboard");
	}
}
