package services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import beans.Product;
@Service
public class MostViewedProductService {
	private SessionFactory factory;
	private Session session;
	private void initValues() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Product.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	public MostViewedProductService() {
		this.initValues();
	}
	public List<Product> getMostViewedProducts(){
		this.initValues();
		List<Product> mostViewed = new ArrayList<Product>();
		List<Product> tmp = new ArrayList<Product>();
		try {
			this.session.beginTransaction();
			tmp = this.session.createQuery("FROM Product ORDER BY noOfViews DESC WHERE status=0").list();
			this.session.getTransaction().commit();
			int j;
			if(tmp.size()>8) {
				j =8;
			}
			else {
				j=tmp.size();
			}
			for(byte i=0;i<j;i++) {
				tmp.get(i).setPhoto1(Base64.getEncoder().encodeToString(tmp.get(i).getPhoto1File()));
				tmp.get(i).setPhoto2(Base64.getEncoder().encodeToString(tmp.get(i).getPhoto2File()));
				tmp.get(i).setPhoto3(Base64.getEncoder().encodeToString(tmp.get(i).getPhoto3File()));
				mostViewed.add(tmp.get(i));
			}
		}catch(Exception er) {
			mostViewed = null;
			System.out.println("Error Message "+er.getMessage());
		}
		return mostViewed;
	}
}
