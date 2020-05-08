package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Feedback;
import beans.Message;

@Service
public class FeedbackService {
	@Autowired 
	private Feedback feedback;
	@Autowired
	private Message message;
	@Autowired
	private CrossSiteFilter filter;
	
	private SessionFactory factory;
	private Session session;
	private void initValues() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Feedback.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	public FeedbackService() {
		this.initValues();
	}
	public void setFeedbackObj(Feedback fb) {
		this.feedback = fb;
		SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd");
		this.feedback.setRegDate(formatter.format(new Date()));
	}
	public Message uploadFeedback() {
		this.initValues();
		try {
			this.session.beginTransaction();
			this.session.save(this.feedback);
			this.session.getTransaction().commit();
			this.message.setStatus(false);
			this.message.setMessage("Your Message posted Successfully.");
		}catch(Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error.!!!!");
			System.out.println(this.feedback);
			System.out.println("Error : "+er.getMessage());
		}
		return this.message;
	}
	public List<Feedback> getAllFeedbacks(){
		this.initValues();
		List<Feedback> feedback = new ArrayList<Feedback>();
		try {
			this.session.beginTransaction();
			feedback = this.session.createQuery("FROM Feedback WHERE status=0").list();
			this.session.getTransaction().commit();
		}catch(Exception er) {
			feedback = null;
		}
		return feedback;
	}
	public Message deleteFeedback(int id) {
		this.initValues();
		try {
			this.session.beginTransaction();
			this.feedback = (Feedback)this.session.get(Feedback.class,id);
			this.feedback.setStatus(true);
			this.feedback.setDelDate(new SimpleDateFormat("yy/MM/dd").format(new Date()));
			this.session.getTransaction().commit();
			this.message.setStatus(false);
			this.message.setMessage("Message Deleted.!!!");
		}catch(Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error.!!!!!");
		}
		return this.message;
	}
	
	public Message checkXssAttacks(Feedback userMessage) {
		if(this.filter.isHtml(userMessage.getFullName())){
			this.message.setStatus(true);
			this.message.setMessage("Cannot insert HTML character in Full Name");
		}
		else {
			if(this.filter.isHtml(userMessage.getSubject())) {
				this.message.setStatus(true);
				this.message.setMessage("Cannot insert HTML character in Subject");
			}
			else {
				if(this.filter.isHtml(userMessage.getFeedback())) {
					this.message.setStatus(true);
					this.message.setMessage("Cannot insert HTML character in your message");
				}
				else {
					if(this.filter.isHtml(userMessage.getEmail())) {
						this.message.setStatus(true);
						this.message.setMessage("Cannot insert HTML character in Email");
					}
					else {
						this.message.setStatus(false);
						this.message.setMessage("Valid Info");
					}
				}
			}
		}
		return this.message;
	}
}
