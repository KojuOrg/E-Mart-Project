package services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import beans.Product;

public class IndexPageDisplayService {
	@Autowired
	private Product product;
	private SessionFactory factory;
	private Session session;
	private void init() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Product.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	public IndexPageDisplayService() {
		this.init();
	}
	public IndexPageDisplayService(Product product) {
		this.product = product;
		this.init();
	}
	public List<Product> getMobiles(){
		List<Product> products = new ArrayList<Product>();
		try {
			this.session.beginTransaction();
			products = this.session.createQuery("FROM Product WHERE category='Mobiles'").list();
			this.session.getTransaction().commit();
			Collections.shuffle(products);
			for(int i=0;i<products.size();i++) {
				products.get(i).setPhoto1(Base64.getEncoder().encodeToString(products.get(i).getPhoto1File()));
			}
		}catch(Exception er) {
			products=null;
		}
		return products;
	}
}
