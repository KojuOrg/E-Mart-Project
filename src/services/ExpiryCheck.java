package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import beans.Product;
@Service
public class ExpiryCheck {
	private List<Product> products;
	private Session session;
	private SessionFactory factory;
	public ExpiryCheck() {
		this.products = new ArrayList<Product>();
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Product.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	public void checkExpiryProducts() {
		try {
			this.session.beginTransaction();
			this.products = this.session.createQuery("FROM Product WHERE status=0").list();
			for(Product p : this.products) {
				if(p.getDelDate().equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
					p.setStatus(true);
				}
			}
			this.session.getTransaction().commit();
		}catch(Exception er) {
			System.out.println("Expiry Check Error : "+er.getMessage());
		}
	}
}
