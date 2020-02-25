package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import beans.Admin;
import beans.Message;
import services.AdminService;
import services.FeedbackService;

@Controller
public class AdminController {
	@Autowired
	private Admin admin;
	@Autowired
	private AdminService adminService;
	@Autowired
	private Message message;
	@Autowired
	private FeedbackService fbServ;

	@RequestMapping(value = "/admin")
	public ModelAndView adminLoginPage() {
		return new ModelAndView("adminLogin", "admin", admin);
	}

	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public ModelAndView adminLoginProcess(HttpSession session, @Valid @ModelAttribute("admin") Admin admin,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return new ModelAndView("adminLogin");
		} else {
			this.adminService.setAdmin(admin);
			this.message = this.adminService.adminLogin();
			if (this.message.isStatus()) {
				model.addAttribute("message", this.message);
				return new ModelAndView("adminLogin");			
			} else {
				session.setAttribute("adminUserName", admin.getUserName());
				model.addAttribute("adminUpdate", this.adminService.getAdmin());
				model.addAttribute("adminInfo", this.adminService.getAdmin());
				return new ModelAndView("adminHome", "page", "dashboard");
			}
		}
	}

	@RequestMapping(value = "/adminDashboard")
	public ModelAndView adminDashboardPage(HttpSession session, Model model) {
		this.admin.setUserName(session.getAttribute("adminUserName").toString());
		this.adminService.setAdmin(this.admin);
		model.addAttribute("adminUpdate", this.adminService.getAdmin());
		model.addAttribute("adminInfo", this.adminService.getAdmin());
		return new ModelAndView("adminHome", "page", "dashboard");
	}

	// Forget Password Process Starts
	@RequestMapping(value = "/recoveryEmail", method = RequestMethod.POST)
	public ModelAndView checkRecoveryEmail(HttpServletRequest request, @RequestParam("email") String email,
			@ModelAttribute("admin") Admin admin, Model model) {
		if (email.equals("") || email == null) {
			this.message.setStatus(true);
			this.message.setMessage("Email Required ...!!!!!");
			model.addAttribute("message", this.message);
			return new ModelAndView("adminLogin");
		} else {
			this.message = this.adminService.checkEmail(email, request);
			if (this.message.isStatus()) {
				model.addAttribute("message", this.message);
				return new ModelAndView("adminLogin");
			} else {
				model.addAttribute("message", this.message);
				return new ModelAndView("recoveryCode", "message", this.message);
			}
		}
	}

	@RequestMapping(value = "/checkRecoveryCode", method = RequestMethod.POST)
	public ModelAndView checkRecoveryCodeProcess(HttpServletRequest request, @RequestParam("code") String code,
			Model model) {
		model.addAttribute("admin", this.admin);
		if (code.equals(request.getSession().getAttribute("recoveryCode").toString())) {
			return new ModelAndView("resetPass");
		} else {
			this.message.setStatus(true);
			this.message.setMessage("Invalid Recovery Code..!!!");
			request.getSession().removeAttribute("recoveryCode");
			return new ModelAndView("adminLogin", "message", this.message);
		}
	}

	@RequestMapping(value = "/setNewPass", method = RequestMethod.POST)
	public ModelAndView setNewPassProcess(HttpServletRequest request, @RequestParam("newPass") String newPass,
			Model model) {
		model.addAttribute("message", this.adminService.setNewPassword(newPass,
				request.getSession().getAttribute("adminRecoveryEmail").toString()));
		request.getSession().removeAttribute("adminRecoveryEmail");
		return new ModelAndView("adminLogin", "admin", this.admin);
	}

	// Forget Password Process Ends
	@RequestMapping(value = "/adminLogout")
	public ModelAndView adminLogoutProcess(HttpSession session) {
		session.removeAttribute("adminUserName");
		return new ModelAndView("adminLogin", "admin", this.admin);
	}

	@RequestMapping(value = "/adminUpdateCredentials", method = RequestMethod.POST)
	public ModelAndView adminUpdateCredentialsProcess(HttpSession session,
			@Valid @ModelAttribute("adminUpdate") Admin admin, BindingResult result,
			@RequestParam("oldPass") String oldPass, Model model) {
		this.admin.setUserName(session.getAttribute("adminUserName").toString());
		this.adminService.setAdmin(this.admin);
		if (result.hasErrors()) {
			model.addAttribute("adminInfo", this.adminService.getAdmin());
			return new ModelAndView("adminHome", "page", "dashboard");
		} else {
			this.adminService.setAdmin(admin);
			this.message = this.adminService.updateAdmin(session.getAttribute("adminUserName").toString(), oldPass);
			model.addAttribute("message", this.message);
			if (this.message.isStatus()) {
				model.addAttribute("adminInfo", this.adminService.getAdmin());
				model.addAttribute("adminUpdate",this.adminService.getAdmin());
				return new ModelAndView("adminHome", "page", "dashboard");
			} else {
				session.removeAttribute("adminUserName");
				return new ModelAndView("adminLogin", "admin", this.admin);
			}
		}
	}
	@RequestMapping(value="/userFeedback")
	public ModelAndView userFeedbackPage(Model model) {
		model.addAttribute("userFeedback",this.fbServ.getAllFeedbacks());
		return new ModelAndView("adminHome","page","feedback");
	}
	@RequestMapping(value="/deleteUserFeedback",method=RequestMethod.POST)
	public ModelAndView deleteUserFeedbackProcess(@RequestParam("id") String id,Model model) {
		model.addAttribute("message",this.fbServ.deleteFeedback(Integer.parseInt(id)));
		model.addAttribute("userFeedback",this.fbServ.getAllFeedbacks());
		return new ModelAndView("adminHome","page","feedback");
	}
}
