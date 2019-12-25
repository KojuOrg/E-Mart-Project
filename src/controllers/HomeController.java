package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import beans.Message;
import beans.UserLogin;
import services.UserForgetPassService;
import services.UserLoginService;

@Controller
public class HomeController {
	@Autowired
	private UserLogin user;
	@Autowired
	private Message message;

	// Request Mapping for Main Interface Starts
	/* Koju's Protion Starts */
	@RequestMapping(value = "/")
	public ModelAndView indexPage() {
		return new ModelAndView("index", "page", "mainBody");
	}

	@RequestMapping(value = "/index")
	public ModelAndView homePage() {
		return new ModelAndView("index", "page", "mainBody");
	}

	@RequestMapping(value = "/contact")
	public ModelAndView contactPage() {
		return new ModelAndView("index", "page", "contact");
	}

	@RequestMapping(value = "/userLogin")
	public ModelAndView userLoginPage(Model model) {
		model.addAttribute("user", new UserLogin());
		return new ModelAndView("index", "page", "userLogin");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView userLoginProcess(@ModelAttribute("user") UserLogin user, Model model) {
		this.message = new Message();
		this.message = new UserLoginService(user).validateUser();
		model.addAttribute("message", this.message);
		if (this.message.isStatus()) {
			return new ModelAndView("index", "page", "userLogin");
		} else {
			return new ModelAndView("index", "page", "mainBody");
		}
	}

	@RequestMapping(value = "/forgetPassword")
	public ModelAndView userLoginPage() {
		return new ModelAndView("index", "page", "forgetPass");
	}

	@RequestMapping(value = "/emailValidation")
	public ModelAndView validateCodePage(HttpServletRequest request, @RequestParam("userEmail") String email,
			Model model) {
		int code = (int) (Math.random() * 99999 + 10000);
		HttpSession session = request.getSession();
		session.setAttribute("uniqueCode", code);
		this.message = new Message();
		this.message = new UserForgetPassService(email).validateEmail(code);
		model.addAttribute("message", this.message);
		if (this.message.isStatus()) {
			return new ModelAndView("index", "page", "forgetPass");
		} else {
			session.setAttribute("userEmail", email);
			return new ModelAndView("index", "page", "validationCode");
		}
	}

	@RequestMapping(value = "/codeValidation")
	public ModelAndView recoverUserCredentialsPage(HttpServletRequest request, @RequestParam("uniqueCode") String code,
			Model model) {
		HttpSession session = request.getSession();
		if (session.getAttribute("uniqueCode").toString().equals(code)) {
			session.removeAttribute("uniqueCode");
			return new ModelAndView("index", "page", "recoverCredentials");
		} else {
			session.removeAttribute("uniqueCode");
			session.removeAttribute("userEmail");
			this.message = new Message();
			this.message.setStatus(true);
			this.message.setMessage("Invalid Recovery Code.!!!");
			model.addAttribute("message", this.message);
			model.addAttribute("user", new UserLogin());
			return new ModelAndView("index", "page", "userLogin");
		}
	}
	@RequestMapping(value="/recoverAccount",method=RequestMethod.POST)
	public ModelAndView recoverUserAccountProcess(HttpServletRequest request,@RequestParam("recoveryPass") String pwd,Model model) {
		if(pwd.equals("")) {
			this.message = new Message();
			this.message.setStatus(true);
			this.message.setMessage("Password Must Not be Empty");
			model.addAttribute("message",this.message);
			return new ModelAndView("index","page","recoverCredentials");
		}
		else {
			HttpSession session = request.getSession();
			model.addAttribute("message",new UserForgetPassService(session.getAttribute("userEmail").toString()).recoverUserAccount(pwd));
			model.addAttribute("user",new UserLogin());
			session.removeAttribute("userEmail");
			return new ModelAndView("index","page","userLogin");
		}
	}
	@RequestMapping(value="/electronics")
	public ModelAndView electronicsPage() {
		return new ModelAndView("index","page","electronics");
	}
	@RequestMapping(value="/computers")
	public ModelAndView computersPage() {
		return new ModelAndView("index","page","computers");
	}
	@RequestMapping(value="/mobiles")
	public ModelAndView mobilesPage() {
		return new ModelAndView("index","page","mobiles");
	}
	@RequestMapping(value="/clothes")
	public ModelAndView clothesPage() {
		return new ModelAndView("index","page","clothes");
	}
	@RequestMapping(value="/cosmetics")
	public ModelAndView cosmeticsPage() {
		return new ModelAndView("index","page","cosmetics");
	}
	@RequestMapping(value="/others")
	public ModelAndView othersPage() {
		return new ModelAndView("index","page","others");
	}
	/* Koju's Portion Ends */

	/* Unika's Portion Ends */
	/* Unika's Portion Ends */

	/* Sandesh's Portion Ends */
	/* Sandesh's Portion Ends */

	// Request Mapping for Main Interface Ends

	// Request Mapping for User Interface Starts

	/* Koju's Portion Ends */
	/* Koju's Portion Ends */

	/* Unika's Portion Ends */
	/* Unika's Portion Ends */

	/* Sandesh's Portion Ends */
	/* Sandesh's Portion Ends */

	// Request Mapping for User Interface Ends

	// Request Mapping for Admin Pannel Starts

	/* Koju's Portion Ends */
	/* Koju's Portion Ends */

	/* Unika's Portion Ends */
	/* Unika's Portion Ends */

	/* Sandesh's Portion Ends */
	/* Sandesh's Portion Ends */

	// Request Mapping for Admin Pannel Ends
}
