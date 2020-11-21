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
import beans.User1;
import beans.UserViews;

@Service
public class UserRegister {
	@Autowired
	private User1 user1;

	@Autowired
	private Message errorMessage;
	SessionFactory factory;
	Session session;
	
	@Autowired 
	private CrossSiteFilter filter;
	@Autowired
	private UserViews userviews;
	
	public Message register(User1 user1) {
		this.user1 = user1;
		DateFormat df = new SimpleDateFormat("yy/MM/dd");
		Date dateobj = new Date();
		String regDate=df.format(dateobj);
		this.user1.setRegDate(regDate);
		factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(User1.class)
				.buildSessionFactory();
		session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			session.save(this.user1);
			session.getTransaction().commit();
			this.errorMessage= new Message(false,"Registration Succeed.");
			this.createViewTables();
		}
		catch(Exception er) {
			this.errorMessage = new Message(true,"Internal Error Please Try Again Later.!!");
			System.out.println("Reach this.");
			System.out.println("Error : "+er.getMessage());
		}
		return this.errorMessage;
	}
	
	public Message checkXssAttacks(User1 user) {
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
private void createTable() {
		
		factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserViews.class)
				.buildSessionFactory();
		session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			session.save(this.userviews);
			session.getTransaction().commit();
		}
		catch(Exception er) {
			System.out.println("Error : "+er.getMessage());
		}
	}
	
	private void createViewTables() {
		userviews.setUserName(this.user1.getUserName());
		userviews.setNumOfViews(0);
		userviews.setCategory("Electronics");
		this.createTable();
		userviews.setCategory("Mobiles");
		this.createTable();
		userviews.setCategory("Clothes");
		this.createTable();
		userviews.setCategory("Computers");
		this.createTable();
		userviews.setCategory("Cosmetics");
		this.createTable();
		userviews.setCategory("Others");
		this.createTable();
	}
}
