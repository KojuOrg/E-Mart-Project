package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	//Request Mapping for Main Interface Starts
	/* Koju's Protion Starts */
	@RequestMapping(value="/")
	public ModelAndView indexPage() {
		return new ModelAndView("index","page","mainBody");
	}
	@RequestMapping(value="/index")
	public ModelAndView homePage() {
		return new ModelAndView("index","page","mainBody");
	}
	@RequestMapping(value="/contact")
	public ModelAndView contactPage() {
		return new ModelAndView("index","page","contact");
	}
	@RequestMapping(value="/userLogin")
	public ModelAndView userLoginPage() {
		return new ModelAndView("index","page","userLogin");
	}
	/* Koju's Portion Ends */
	
	/* Unika's Portion Ends */
	/* Unika's Portion Ends */
	
	/* Sandesh's Portion Ends */
	/* Sandesh's Portion Ends */
	
	//Request Mapping for Main Interface Ends
	
	//Request Mapping for User Interface Starts
	
	/* Koju's Portion Ends */
	/* Koju's Portion Ends */
	
	/* Unika's Portion Ends */
	/* Unika's Portion Ends */
	
	/* Sandesh's Portion Ends */
	/* Sandesh's Portion Ends */
	
	//Request Mapping for User Interface Ends
	
	//Request Mapping for Admin Pannel Starts
	
	/* Koju's Portion Ends */
	/* Koju's Portion Ends */
	
	/* Unika's Portion Ends */
	/* Unika's Portion Ends */
	
	/* Sandesh's Portion Ends */
	/* Sandesh's Portion Ends */
	
	//Request Mapping for Admin Pannel Ends
}
