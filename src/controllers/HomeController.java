package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import beans.Email;
import beans.Feedback;
import beans.Message;
import beans.Product;
import beans.ProductReport;
import beans.User;
import beans.UserLogin;
import services.CategoryDisplayService;
import services.EmailValidation;
import services.FeedbackService;
import services.GetUserService;
import services.IndexPageDisplayService;
import services.ProductReportService;
import services.ProductService;
import services.SendValidationCode;
import services.UserForgetPassService;
import services.UserLoginService;
import services.UserRegister;

@Controller
public class HomeController {
	@Autowired
	private UserLogin userLogin;
	@Autowired
	private Message message;
	@Autowired 
	private Product product;
	@Autowired
	private ProductService pservice;
	@Autowired
	private CategoryDisplayService catDisServ;
	@Autowired
	private IndexPageDisplayService ipds;
	@Autowired
	private UserLoginService ulserv;
	@Autowired
	private UserForgetPassService ufpserv;
	@Autowired
	private GetUserService guserv;
	@Autowired
	private Feedback feedback;
	@Autowired
	private FeedbackService fbServ;
	@Autowired
	private ProductReport preport;
	@Autowired
	private ProductReportService prServ; 
	
	
	@Autowired
	private User user;
	@Autowired
	private Email email;
	@Autowired
	private UserRegister urserv;
	@Autowired
	private EmailValidation evserv;
	@Autowired
	private SendValidationCode svcserv;
	// Request Mapping for Main Interface Starts
	/* Koju's Protion Starts */
	@RequestMapping(value = "/")
	public ModelAndView indexPage(Model model) {
		model.addAttribute("mobiles",this.ipds.getMobiles());
		return new ModelAndView("index", "page", "mainBody");
	}

	@RequestMapping(value = "/index")
	public ModelAndView homePage(Model model) {
		model.addAttribute("mobiles",this.ipds.getMobiles());
		return new ModelAndView("index", "page", "mainBody");
	}

	@RequestMapping(value = "/contact")
	public ModelAndView contactPage(Model model) {
		model.addAttribute("userMessage",this.feedback);
		return new ModelAndView("index", "page", "contact");
	}

	@RequestMapping(value = "/userLogin")
	public ModelAndView userLoginPage(Model model) {
		//model.addAttribute("user", new UserLogin());
		model.addAttribute("user", this.userLogin);
		return new ModelAndView("index", "page", "userLogin");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView userLoginProcess(HttpServletRequest request,@ModelAttribute("user") UserLogin user, Model model) {
		this.ulserv.setUser(user);
		this.message = this.ulserv.validateUser(request);
		model.addAttribute("message", this.message);
		if (this.message.isStatus()) {
			return new ModelAndView("index", "page", "userLogin");
		} else {
			model.addAttribute("mobiles",this.ipds.getMobiles());
			return new ModelAndView("index", "page", "mainBody");
		}
	}

	@RequestMapping(value = "/forgetPassword")
	public ModelAndView userLoginPage() {
		return new ModelAndView("index", "page", "forgetPass");
	}

	@RequestMapping(value = "/emailValidation")
	public ModelAndView validateCodePage(HttpServletRequest request, @RequestParam("userName") String userName,
			Model model) {
		int code = (int) (Math.random() * 99999 + 10000);
		HttpSession session = request.getSession();
		session.setAttribute("uniqueCode", code);
		this.ufpserv.setUserName(userName);
		this.message = this.ufpserv.validateEmail(code);
		model.addAttribute("message", this.message);
		if (this.message.isStatus()) {
			return new ModelAndView("index", "page", "forgetPass");
		} else {
			session.setAttribute("user_name", userName);
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
			session.removeAttribute("user_name");
			this.message.setStatus(true);
			this.message.setMessage("Invalid Recovery Code.!!!");
			model.addAttribute("message", this.message);
			model.addAttribute("user",this.userLogin);
			return new ModelAndView("index", "page", "userLogin");
		}
	}
	@RequestMapping(value="/recoverAccount",method=RequestMethod.POST)
	public ModelAndView recoverUserAccountProcess(HttpServletRequest request,@RequestParam("recoveryPass") String pwd,Model model) {
		if(pwd.equals("")) {
			this.message.setStatus(true);
			this.message.setMessage("Password Must Not be Empty");
			model.addAttribute("message",this.message);
			return new ModelAndView("index","page","recoverCredentials");
		}
		else {
			HttpSession session = request.getSession();
			this.ufpserv.setUserName(session.getAttribute("user_name").toString());
			model.addAttribute("message",this.ufpserv.recoverUserAccount(pwd));
			model.addAttribute("user",this.userLogin);
			session.removeAttribute("userEmail");
			return new ModelAndView("index","page","userLogin");
		}
	}
	@RequestMapping(value="/electronics")
	public ModelAndView electronicsPage(Model model) {
		model.addAttribute("categoryProducts",this.catDisServ.getProducts("Electronics"));
		return new ModelAndView("index","page","electronics");
	}
	@RequestMapping(value="/computers")
	public ModelAndView computersPage(Model model) {
		model.addAttribute("categoryProducts",this.catDisServ.getProducts("Computers"));
		return new ModelAndView("index","page","computers");
	}
	@RequestMapping(value="/mobiles")
	public ModelAndView mobilesPage(Model model) {
		model.addAttribute("categoryProducts",this.catDisServ.getProducts("Mobiles"));
		return new ModelAndView("index","page","mobiles");
	}
	@RequestMapping(value="/clothes")
	public ModelAndView clothesPage(Model model) {
		model.addAttribute("categoryProducts",this.catDisServ.getProducts("Clothes"));
		return new ModelAndView("index","page","clothes");
	}
	@RequestMapping(value="/cosmetics")
	public ModelAndView cosmeticsPage(Model model) {
		model.addAttribute("categoryProducts",this.catDisServ.getProducts("Cosmetics"));
		return new ModelAndView("index","page","cosmetics");
	}
	@RequestMapping(value="/others")
	public ModelAndView othersPage(Model model) {
		model.addAttribute("categoryProducts",this.catDisServ.getProducts("Others"));
		return new ModelAndView("index","page","others");
	}
	@RequestMapping(value="/singleProduct")
	public ModelAndView singleProductPage() {
		return new ModelAndView("index","page","singleProduct");
	}
	@RequestMapping(value="/sellProduct")
	public ModelAndView productSellingPage(HttpServletRequest request,Model model) {
		if(request.getSession().getAttribute("userName")==null){
			model.addAttribute("user", this.userLogin);
			return new ModelAndView("index","page","sellProductInvalid");
		}
		else {
			model.addAttribute("product",this.product);
			return new ModelAndView("index","page","sellProduct");
		}
	}
	@RequestMapping(value="/sellingProduct",method=RequestMethod.POST)
	public ModelAndView sellProductProcess(@Valid @ModelAttribute("product") Product product,BindingResult result,
			@RequestParam("firstPhoto") CommonsMultipartFile photo1,
			@RequestParam("secondPhoto") CommonsMultipartFile photo2,
			@RequestParam("thirdPhoto") CommonsMultipartFile photo3,HttpSession session,Model model) {
		if(result.hasErrors()) {
			return new ModelAndView("index","page","sellProduct");
		}
		else if(photo1.isEmpty() || photo2.isEmpty() || photo3.isEmpty()) {
			this.message.setStatus(true);
			this.message.setMessage("3 Photos are needed to upload your product.");
			model.addAttribute("message",this.message);
			return new ModelAndView("index","page","sellProduct");
		}
		else {
			this.pservice.setProduct(product);
			model.addAttribute("message",this.pservice.registerProduct(photo1, photo2, photo3, session));
			model.addAttribute("mobiles",this.ipds.getMobiles());
			return new ModelAndView("index","page","mainBody");
		}
	}
	@RequestMapping(value="/singleProduct",method=RequestMethod.GET)
	public ModelAndView singleProductPage(@RequestParam("product") int id,Model model) {
		this.product = pservice.getSingleProduct(id);
		model.addAttribute("product",this.product);
		model.addAttribute("specification",pservice.getProductSpecifications());
		model.addAttribute("seller",this.guserv.getUser(this.product.getUserId()));
		return new ModelAndView("index","page","singleProduct");
	}
	@RequestMapping(value="/logout")
	public ModelAndView logoutProcess(HttpServletRequest request,Model model) {
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("userId");
		model.addAttribute("mobiles",this.ipds.getMobiles());
		return new ModelAndView("index","page","mainBody");
	}
	@RequestMapping(value="/userFeedback",method=RequestMethod.POST)
	public ModelAndView userMessageProcess(@Valid @ModelAttribute("userMessage") Feedback feedback,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return new ModelAndView("index", "page", "contact");
		}
		else {
			this.fbServ.setFeedbackObj(feedback);
			model.addAttribute("message",this.fbServ.uploadFeedback());
			model.addAttribute("userMessage",new Feedback());
			return new ModelAndView("index", "page", "contact");
		}
	}
	@RequestMapping(value="/productReport",method=RequestMethod.POST)
	public ModelAndView productReportProcess(HttpServletRequest request,HttpSession session,@RequestParam("productId") String productId,@RequestParam("report") String report,Model model) {
		this.product = this.pservice.getSingleProduct(Integer.parseInt(productId));
		model.addAttribute("product",this.product);
		model.addAttribute("specification",this.pservice.getProductSpecifications());
		model.addAttribute("seller",this.guserv.getUser(this.product.getUserId()));
		if(session.getAttribute("userName")==null) {
			this.message.setStatus(true);
			this.message.setMessage("You must login to Report product.!!!");
			model.addAttribute("message",this.message);
		}
		else {
			if(report==null || report.equals("")) {
				this.message.setStatus(true);
				this.message.setMessage("Please fill the report Box.!!");
				model.addAttribute("message",this.message);
			}
			else {
				this.preport.setProductId(Integer.parseInt(productId));
				this.preport.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
				this.preport.setRegDate(new SimpleDateFormat("yy/MM/dd").format(new Date()));
				this.prServ.setReport(this.preport);
				model.addAttribute("message",this.prServ.uploadReport());
			}
		}
		return new ModelAndView("index","page","singleProduct");
	}
	/* Koju's Portion Ends */

	/* Unika's Portion Ends */
	@RequestMapping(value = "/register")
	public ModelAndView registerPage(Model userModel) {
		//userModel.addAttribute("user", new User());
		userModel.addAttribute("user", this.user);
		return new ModelAndView("index", "page", "register");
	}

	@RequestMapping(value = "/processRegister")
	public ModelAndView processForm(@Valid @ModelAttribute("user") User theUser, BindingResult thebindingresult, Model model) throws AddressException, MessagingException {
		if (thebindingresult.hasErrors()) {
			// Redirect to form
			return new ModelAndView("index", "page", "register");
		} else {
			 message = urserv.register(theUser);
			//message=new UserRegister(theUser).register();
			//model.addAttribute("message", message);
			if (message.isStatus()) {
				return new ModelAndView("index", "page", "register");
			} else {
				
				//Email email=new Email();
				email.setUserId(theUser.getId());
				email.setEmail(theUser.getEmail());
				email.setCode(UUID.randomUUID().toString());
				svcserv.sendEmail1(email);
				model.addAttribute("email", email);
				return new ModelAndView("index", "page", "confirmEmail");
			}
		}
	}
	
	@RequestMapping("/confirmYourEmail")
	public ModelAndView enableAccount(@ModelAttribute("email") Email theEmail, Model model) {
		 message = evserv.confirmEmail(theEmail);
		if (message.isStatus()) {
			model.addAttribute("user", this.user);
			//model.addAttribute("user", new User());
			return new ModelAndView("index", "page", "register");
		} else {
			return new ModelAndView("index", "page", "mainBody");
		}
		
	}
	/* Unika's Portion Ends */

	// Request Mapping for Admin Pannel Ends
}
