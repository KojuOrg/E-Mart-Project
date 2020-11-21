package services;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.UserViews;

@Service
public class RecommenderService {

	private SessionFactory factory;
	private Session session;
	@Autowired
	private UserViews userViews;
	
	private void init() {
		this.factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UserViews.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	
	public boolean recommender(String userName, String category) {
		init();
		try {
		this.session.beginTransaction();
		String HQL = "update UserViews a set a.numOfViews=a.numOfViews+1 WHERE userName=? AND category=?";
        Query query = this.session.createQuery(HQL);
		query.setString(0, userName);
		query.setString(1, category);
        query.executeUpdate();
		session.getTransaction().commit();
		
		return true;
		}
		catch(Exception ex)
		{
			System.out.println("Internal error: "+ex);
			return false;
		}
	}
	
	public List<String> getCategory(String userName) {
		List<String> categories = new ArrayList<String>();
		init();
		try {
			this.session.beginTransaction();
			String HQL = "SELECT category FROM UserViews WHERE userName=? ORDER BY numOfViews DESC";
			Query query = this.session.createQuery(HQL);
			query.setString(0, userName);
			categories= query.list();
			this.session.getTransaction().commit();
		}
		catch(Exception ex) {
			System.out.println("Error: "+ex);
		}
		return categories;
	}
}
