package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import beans.Comment;
import beans.Email;
import beans.Feedback;
import beans.Message;
import beans.PaidProduct;
import beans.Product;
import beans.ProductReport;
import beans.User;
import beans.UserLogin;
import services.CategoryDisplayService;
import services.CommentService;
import services.EmailValidation;
import services.ExpiryCheck;
import services.FeedbackService;
import services.GetUserService;
import services.IndexPageDisplayService;
import services.MostViewedProductService;
import services.PaidProductService;
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
	private ExpiryCheck expChk;
	@Autowired
	private PaidProductService ppService;
	@Autowired
	private PaidProduct paidProduct;
	
	
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
	@Autowired
	private MostViewedProductService mvpServ;
	
	@Autowired
	CacheManager cacheManager;
	
	@Autowired
	private CommentService cmtserv;
	@Autowired
	private Comment comment;
	// Request Mapping for Main Interface Starts
	/* Koju's Protion Starts */
	@RequestMapping(value = "/")
	public ModelAndView indexPage(Model model,HttpSession session) {
		ServletContext context = session.getServletContext();
		String path = context.getRealPath("uploads");
		System.out.println(path);
		this.expChk.checkExpiryProducts();
		model.addAttribute("mobiles",this.ipds.getMobiles());
		model.addAttribute("mostViewed",this.mvpServ.getMostViewedProducts());
		return new ModelAndView("index", "page", "mainBody");
	}

	@RequestMapping(value = "/index")
	public ModelAndView homePage(Model model) {
		model.addAttribute("mobiles",this.ipds.getMobiles());
		model.addAttribute("mostViewed",this.mvpServ.getMostViewedProducts());
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
			model.addAttribute("mostViewed",this.mvpServ.getMostViewedProducts());
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
	public ModelAndView sellProductProcess(HttpSession sess,@Valid @ModelAttribute("product") Product product,BindingResult result,
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
			model.addAttribute("product",product);
			return new ModelAndView("index","page","sellProduct");
		}
		else {
			product.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
			this.ppService.setProduct(product);
			this.paidProduct = this.ppService.checkNoOfProductUpload();
			if(this.paidProduct!=null && this.paidProduct.getPaidPrice()!=0) {
				if(this.ppService.uploadInPaidTable(sess)) {
					System.out.println("paid product upload success");
					product.setStatus(true);
					product.setDelDate(this.ppService.getProductDelDate());
					this.pservice.setProduct(product);
					this.pservice.registerProduct(photo1, photo2, photo3, session);
					model.addAttribute("paidProduct",this.paidProduct);
					model.addAttribute("percentageMessage",this.ppService.getPercentageMessage());
					this.catDisServ.refreshAllProducts();
					return new ModelAndView("index","page","paidProduct");
				}
				else {
					this.message.setStatus(true);
					this.message.setMessage("Internal Error");
					model.addAttribute("message",this.message);
					model.addAttribute("mobiles",this.ipds.getMobiles());
					model.addAttribute("mostViewed",this.mvpServ.getMostViewedProducts());
					this.catDisServ.refreshAllProducts();
					return new ModelAndView("index","page","mainBody");
				}
			}
			else if(this.paidProduct!=null && this.paidProduct.getPaidPrice()==0){
				System.out.println("paidprice is 0");
				this.pservice.setProduct(product);
				model.addAttribute("message",this.pservice.registerProduct(photo1, photo2, photo3, session));
				model.addAttribute("mobiles",this.ipds.getMobiles());
				model.addAttribute("mostViewed",this.mvpServ.getMostViewedProducts());
				this.catDisServ.refreshAllProducts();
				return new ModelAndView("index","page","mainBody");
			}
			else {
				System.out.println("Total Error");
				this.message.setStatus(true);
				this.message.setMessage("Internal Error");
				model.addAttribute("message",this.message);
				model.addAttribute("mobiles",this.ipds.getMobiles());
				model.addAttribute("mostViewed",this.mvpServ.getMostViewedProducts());
				this.catDisServ.refreshAllProducts();
				return new ModelAndView("index","page","mainBody");
			}
		}
	}
	
	
	@RequestMapping(value="/paymentSuccess",method=RequestMethod.GET)
	public ModelAndView paymentSuccessProcess(@RequestParam("oid") String uniqueCode,@RequestParam("amt") String totalPaymentAmount,@RequestParam("refId") String referenceId,Model model) {
		this.message.setStatus(false);
		this.message.setMessage(uniqueCode+" "+totalPaymentAmount+" "+referenceId);
		model.addAttribute("mobiles",this.ipds.getMobiles());
		model.addAttribute("mostViewed",this.mvpServ.getMostViewedProducts());
		return new ModelAndView("index","page","mainBody");
	}
	@RequestMapping(value="/paymentFailure")
	public ModelAndView paymentSuccessProcess(Model model) {
		this.message.setStatus(true);
		this.message.setMessage("Failed payment");
		model.addAttribute("mobiles",this.ipds.getMobiles());
		model.addAttribute("mostViewed",this.mvpServ.getMostViewedProducts());
		return new ModelAndView("index","page","mainBody");
	}
	
	@RequestMapping(value="/singleProduct",method=RequestMethod.GET)
	public ModelAndView singleProductPage(@RequestParam("product") int id,Model model,Model commentModel) {
		this.product = this.pservice.getSingleProduct(id);
		model.addAttribute("product",this.product);
		model.addAttribute("specification",this.pservice.getProductSpecifications());
		model.addAttribute("seller",this.guserv.getUser(this.product.getUserId()));
		model.addAttribute("comment",this.cmtserv.getComments(id));
		this.comment.setProductId(id);
		commentModel.addAttribute("newComment", this.comment);
		return new ModelAndView("index","page","singleProduct");
	}
	@RequestMapping(value="/logout")
	public ModelAndView logoutProcess(HttpServletRequest request,Model model) {
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("userId");
		model.addAttribute("mobiles",this.ipds.getMobiles());
		model.addAttribute("mostViewed",this.mvpServ.getMostViewedProducts());
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
				this.preport.setReport(report);
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
				
				this.message.setStatus(true);
				this.message.setMessage("Internal Error");
				model.addAttribute("message",this.message);
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
	
	@RequestMapping(value="/confirmYourEmail",method=RequestMethod.POST)
	public ModelAndView enableAccount(@ModelAttribute("email") Email theEmail, Model model) {
		 message = evserv.confirmEmail(theEmail);
		if (message.isStatus()) {
			model.addAttribute("user", this.user);
			
			this.message.setStatus(true);
			this.message.setMessage("Incorrect code");
			model.addAttribute("message",this.message);
			//model.addAttribute("user", new User());
			model.addAttribute("email", theEmail);
			return new ModelAndView("index", "page", "confirmEmail");
		} else {
			this.message.setStatus(false);
			this.message.setMessage("Your account has been registered");
			model.addAttribute("message",this.message);
			model.addAttribute("user", this.userLogin);
			return new ModelAndView("index", "page", "userLogin");
		}
		
	}
	
	@RequestMapping(value = "/addComment")
	public ModelAndView commentPage(@ModelAttribute("comment") Comment comment, Model model, Model commentModel) {
		if(cmtserv.setComment(comment)==false)
		{
			this.message.setStatus(true);
			this.message.setMessage("Your comment contains bad words");
			model.addAttribute("message",this.message);
			return new ModelAndView("index","page","mainBody");
		}
		else {
			this.message.setStatus(false);
			this.message.setMessage("Comment uploaded");
			model.addAttribute("message",this.message);
			return new ModelAndView("index","page","mainBody");
		}
		
	}
	/* Unika's Portion Ends */

	// Request Mapping for Admin Pannel Ends
}
