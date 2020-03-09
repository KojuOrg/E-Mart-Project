package services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import beans.Message;
import beans.Product;
import beans.User;
@Service
public class ProductService {
	@Autowired 
	private Product product;
	@Autowired
	private Message message;
	@Autowired
	private User user;
	private SessionFactory factory;
	private Session session;
	private List<String> titles;
	private List<String> descriptions;
	private void initValues() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Product.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
		//this.message = new Message();
	}
	public void setProduct(Product product) {
		this.initValues();
		this.product = product;
	}
	public Message registerProduct(CommonsMultipartFile photo1,CommonsMultipartFile photo2,CommonsMultipartFile photo3,HttpSession session) {
		try {
			ServletContext context = session.getServletContext();
			String path = context.getRealPath("uploads");
			File folder = new File(path);
			if(!folder.exists()) {
				folder.mkdir();
			}
			String photo1Name = photo1.getOriginalFilename();
			String photo2Name = photo2.getOriginalFilename();
			String photo3Name = photo3.getOriginalFilename();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
			String date = formatter.format(new Date());
			photo1Name = date+"1"+(int)(Math.random()*9999999+1)+"@"+photo1Name;
			photo2Name = date+"2"+(int)(Math.random()*9999999+1)+"@"+photo2Name;
			photo3Name = date+"3"+(int)(Math.random()*9999999+1)+"@"+photo3Name;
			
			byte[] bytes1 = photo1.getBytes();
			byte[] bytes2 = photo2.getBytes();
			byte[] bytes3 = photo3.getBytes();
			
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path+File.separator+photo1Name)));
			stream.write(bytes1);
			stream.flush();
			stream = new BufferedOutputStream(new FileOutputStream(new File(path+File.separator+photo2Name)));
			stream.write(bytes2);
			stream.flush();
			stream = new BufferedOutputStream(new FileOutputStream(new File(path+File.separator+photo3Name)));
			stream.flush();
			stream.close();
			
			this.product.setPhoto1File(bytes1);
			this.product.setPhoto2File(bytes2);
			this.product.setPhoto3File(bytes3);
			this.product.setPhoto1(photo1Name);
			this.product.setPhoto2(photo2Name);
			this.product.setPhoto3(photo3Name);
			
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			this.product.setRegDate(formatter.format(new Date()));
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH,1);
			Date delDate = cal.getTime();
			this.product.setDelDate(formatter.format(delDate));
			
			this.product.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
			this.product.setNoOfViews(0);
			
			this.session.beginTransaction();
			this.session.save(this.product);
			this.session.getTransaction().commit();
			
			this.factory = new Configuration().configure("hibernate.cfg.xml")
					.addAnnotatedClass(User.class)
					.buildSessionFactory();
			this.session = this.factory.getCurrentSession();
			this.session.beginTransaction();
			this.user = (User)(this.session.get(User.class,Integer.parseInt(session.getAttribute("userId").toString())));
			this.user.setNumOfUploadedItem(this.user.getNumOfUploadedItem()+1);
			this.session.getTransaction().commit();
			this.message.setStatus(false);
			this.message.setMessage("Your Product is now available in selling list.");
		}catch(Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error ..!!!");
		}
		return this.message;
	}
	public Product getSingleProduct(int id) {
		try {
			this.initValues();
			this.session.beginTransaction();
			this.product = new Product();
			this.product = (Product)(this.session.get(Product.class,id));
			this.product.setNoOfViews(this.product.getNoOfViews()+1);
			this.session.getTransaction().commit();
			this.product.setPhoto1(Base64.getEncoder().encodeToString(this.product.getPhoto1File()));
			this.product.setPhoto2(Base64.getEncoder().encodeToString(this.product.getPhoto2File()));
			this.product.setPhoto3(Base64.getEncoder().encodeToString(this.product.getPhoto3File()));
		}catch(Exception er) {
			System.out.println("Error : "+er.getMessage());
			this.product = null;
		}
		return this.product;
	}
	public Map getProductSpecifications() {
		String[] specification = this.product.getProductSpecification().split("%main%");
		String[] titles = specification[0].split("%sub%");
		String[] desc = specification[1].split("%sub%");
		Map<String,String> spec = new HashMap<String,String>();
		for(int i=0;i<titles.length;i++) {
			spec.put(titles[i],desc[i]);
		}
		return spec;
	}
	public Message deleteProduct(int id) {
		this.initValues();
		try {
			this.session.beginTransaction();
			this.product = (Product)this.session.get(Product.class, id);
			this.product.setStatus(true);
			this.product.setDelDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			this.session.getTransaction().commit();
			this.message.setStatus(false);
			this.message.setMessage("Product and its report deleted successfully.");
		}catch(Exception er) {
			System.out.println("Error Detected : "+er.getMessage());
			this.message.setStatus(true);
			this.message.setMessage("Internal errorr.!!!");
		}
		return this.message;
	}
}
