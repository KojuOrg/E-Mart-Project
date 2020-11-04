package services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.PaidProduct;
import beans.Product;
import beans.User;

@Service
public class PaidProductService {
	private float paidPrice;
	private float netPrice;
	@Autowired
	private Product product;
	@Autowired
	private PaidProduct paidProduct, tmpPaidProduct;
	@Autowired
	private User user;
	private Session session;
	private SessionFactory factory;
	private String percentMessage;

	private void initPaidProductSession() {
		this.factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(PaidProduct.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}

	private void initUserSession() {
		this.factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	private void initProductSession() {
		this.factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Product.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}

	public PaidProductService() {
		this.initUserSession();
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public PaidProduct checkNoOfProductUpload() {
		this.initUserSession();
		try {
			System.out.println("Check function enter");
			this.session.beginTransaction();
			this.user = (User) this.session.get(User.class, this.product.getUserId());
			this.session.getTransaction().commit();
			this.paidPrice = Float.parseFloat(this.product.getPrice().toString());
			this.paidProduct.setUserId(this.product.getUserId());
			this.paidProduct.setProductId(this.product.getId());
			this.paidProduct.setProductPrice(Float.parseFloat(this.product.getPrice().toString()));
			if (this.user.getNumOfUploadedItem() >=15 && this.user.getNumOfUploadedItem() <= 50) {
				//this.paidPrice = Float.parseFloat(String.format("%.2f", this.paidPrice * 0.03));
				this.paidPrice = 30;
				this.paidProduct.setPaidPrice(this.paidPrice);
				//this.paidProduct.setTax(Float.parseFloat(String.format("%.2f", this.paidPrice * 0.01)));
				this.paidProduct.setTax(0);
				this.netPrice = this.paidProduct.getPaidPrice() - this.paidProduct.getTax();
				this.paidProduct.setNetPrice(Float.parseFloat(String.format("%.2f", this.netPrice)));
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.MONTH, 1);
				Date delDate = cal.getTime();
				this.product.setDelDate(new SimpleDateFormat("yyyy-MM-dd").format(delDate));
				this.percentMessage = "You have already uploaded " + this.user.getNumOfUploadedItem()
						+ " items. So as per the Terms you have to pay Rs 30 to upload new product.";
			} else if (this.user.getNumOfUploadedItem() > 50 && this.user.getNumOfUploadedItem() <= 100) {
				//this.paidPrice = Float.parseFloat(String.format("%.2f", this.paidPrice * 0.05));
				this.paidPrice = 50;
				this.paidProduct.setPaidPrice(this.paidPrice);
				//this.paidProduct.setTax(Float.parseFloat(String.format("%.2f", this.paidPrice * 0.015)));
				this.paidProduct.setTax(0);
				this.netPrice = this.paidProduct.getPaidPrice() - this.paidProduct.getTax();
				this.paidProduct.setNetPrice(Float.parseFloat(String.format("%.2f", this.netPrice)));
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.MONTH, 2);
				Date delDate = cal.getTime();
				this.product.setDelDate(new SimpleDateFormat("yyyy-MM-dd").format(delDate));
				this.percentMessage = "You have already uploaded " + this.user.getNumOfUploadedItem()
						+ " items. So as per the Terms you have to pay Rs 50 to upload new product.";
			} else if (this.user.getNumOfUploadedItem() > 100) {
				//this.paidPrice = Float.parseFloat(String.format("%.2f", this.paidPrice * 0.07));
				this.paidPrice = 75;
				this.paidProduct.setPaidPrice(this.paidPrice);
				this.paidProduct.setTax(0);
				//this.paidProduct.setTax(Float.parseFloat(String.format("%.2f", this.paidPrice * 0.02)));
				this.netPrice = this.paidProduct.getPaidPrice() - this.paidProduct.getTax();
				this.paidProduct.setNetPrice(Float.parseFloat(String.format("%.2f", this.netPrice)));
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.MONTH, 4);
				Date delDate = cal.getTime();
				this.product.setDelDate(new SimpleDateFormat("yyyy-MM-dd").format(delDate));
				this.percentMessage = "You have already uploaded " + this.user.getNumOfUploadedItem()
						+ " items. So as per the Terms you have to pay 75 to upload new product.";
			} else {
				System.out.println("Less than 15");
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.MONTH, 1);
				Date delDate = cal.getTime();
				this.product.setDelDate(new SimpleDateFormat("yyyy-MM-dd").format(delDate));
				this.paidProduct.setPaidPrice(0);
			}
		} catch (Exception er) {
			this.paidProduct = null;
			System.out.println("Error message : " + er.getMessage());
		}
		return this.paidProduct;
	}

	public boolean uploadInPaidTable(HttpSession sess) {
		boolean status = true;
		try {
			int productId;
			this.initProductSession();
			this.session.beginTransaction();
			Query query = this.session.createQuery("SELECT MAX(p.id) FROM Product p");
			productId = Integer.parseInt(query.list().get(0).toString());
			this.session.getTransaction().commit();
			this.initPaidProductSession();
			this.session.beginTransaction();
			this.paidProduct.setProductId(productId+1);
			int random = (int) (Math.random() * 99999999 + 1);
			this.paidProduct.setUniqueCode(this.user.getUserName() + "-"
					+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "-" + random);
			this.session.save(this.paidProduct);
			this.session.getTransaction().commit();
			status = true;
		} catch (Exception er) {
			status = false;
			System.out.println("Error Occured.!!! : "+er.getMessage());
		}
		return status;
	}

	public String getPercentageMessage() {
		return this.percentMessage;
	}

	public String getProductDelDate() {
		return this.product.getDelDate();
	}
}
