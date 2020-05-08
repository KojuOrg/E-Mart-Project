package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Message;
import beans.User;

@Service
public class UserRegister {
	private User user;
	@Autowired
	private Message errorMessage;
	SessionFactory factory;
	Session session;
	
	@Autowired 
	private CrossSiteFilter filter;
	
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
	
	public Message checkXssAttacks(User user) {
		if(this.filter.isHtml(user.getFullName())) {
			this.errorMessage.setStatus(true);
			this.errorMessage.setMessage("Cannot input HTML character in Full Name");
		}
		else {
			if(this.filter.isHtml(user.getUserName())) {
				this.errorMessage.setStatus(true);
				this.errorMessage.setMessage("Cannot input HTML character in Userame");
			}
			else {
				if(this.filter.isHtml(user.getEmail())) {
					this.errorMessage.setStatus(true);
					this.errorMessage.setMessage("Cannot input HTML character in Email");
				}
				else {
					if(this.filter.isHtml(user.getAddress())) {
						this.errorMessage.setStatus(true);
						this.errorMessage.setMessage("Cannot input HTML character in Address");
					}
					else {
						if(this.filter.isHtml(user.getContactNum())) {
							this.errorMessage.setStatus(true);
							this.errorMessage.setMessage("Cannot input HTML character in Contact Number");
						}
						else {
							this.errorMessage.setStatus(false);
							this.errorMessage.setMessage("Valid user");
						}
					}
				}
			}
		}
		return this.errorMessage;
	}
}
