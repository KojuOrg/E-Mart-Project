package services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.User;
@Service
public class UserRetriveService {
	@Autowired
	private User user;
	private Session session;
	private SessionFactory factory;
	private void initValues() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	public UserRetriveService() {
		this.initValues();
	}
	public User getUser(int id) {
		this.initValues();
		try {
			this.session.beginTransaction();
			this.user=(User)this.session.get(User.class, id);
			this.session.getTransaction().commit();
		}catch(Exception er) {
			this.user = null;
		}
		return this.user;
	}
}
