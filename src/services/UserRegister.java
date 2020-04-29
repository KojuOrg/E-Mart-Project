package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import beans.Message;
import beans.User;

@Service
public class UserRegister {
	private User user;
	private Message errorMessage;
	SessionFactory factory;
	Session session;
	
	public Message register(User user) {
		
		this.user = user;
		DateFormat df = new SimpleDateFormat("yy/MM/dd");
		Date dateobj = new Date();
		String regDate=df.format(dateobj);
		user.setRegDate(regDate);
		factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			session.save(this.user);
			session.getTransaction().commit();
			this.errorMessage= new Message(false,"Registration Succeed.");
		}
		catch(Exception er) {
			this.errorMessage = new Message(true,"Internal Error Please Try Again Later.!!");
			System.out.println("Error : "+er.getMessage());
		}
		return this.errorMessage;
	}
}
