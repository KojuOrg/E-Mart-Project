package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import beans.Message;
import beans.User;

public class UserForgetPassService {
	private String email;
	@Autowired
	private Message message;
	private User user;
	private SessionFactory factory;
	private Session session;
	private int code;

	public UserForgetPassService(String email) {
		this.email = email;
		this.message = new Message();
		this.factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}

	private void sendRecoveryCode() {
		String host="smtp.gmail.com";
		String port="587";
		String sender="e42emart@gmail.com";
		String pwd="Dangerous@123";
		String messageBody = this.code+" is your Account Recovery code . If you have not requested Recovery Code then just ignore this email.";
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender,pwd);
			}
		};

		javax.mail.Session session = javax.mail.Session.getInstance(properties, auth);
		try {
			javax.mail.Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(sender));
			InternetAddress[] toAddresses = { new InternetAddress(this.email) };
			msg.setRecipients(javax.mail.Message.RecipientType.TO, toAddresses);
			msg.setSubject("Emart Account Recovery Code");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			msg.setSentDate(new Date());
			msg.setText(messageBody);
			Transport.send(msg);
			this.message.setStatus(false);
			this.message.setMessage("Check Your Email");
		} catch (Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error Try Again Later.!!!");
		}
	}

	public Message validateEmail(int code) {
		this.code = code;
		try {
			this.session.beginTransaction();
			String HQL = "FROM User WHERE email=?";
			Query query = this.session.createQuery(HQL);
			query.setString(0, this.email);
			this.user = (User) query.uniqueResult();
			this.session.getTransaction().commit();
			if (this.user != null) {
				this.sendRecoveryCode();
			} else {
				this.message.setStatus(true);
				this.message.setMessage("Unrecognized Email Address.!!!!");
			}
		} catch (Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error .!!!!");
		}
		return this.message;
	}
	public Message recoverUserAccount(String pwd) {
		try {
			this.session.beginTransaction();
			String HQL = "FROM User WHERE email=?";
			Query query = this.session.createQuery(HQL);
			query.setString(0,this.email);
			this.user = (User)query.uniqueResult();
			this.user.setPwd(pwd);
			this.user.setInvalidCount(0);
			this.session.getTransaction().commit();
			this.message.setStatus(false);
			this.message.setMessage("Password Recovered Successfully.!!");
		}catch(Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error .!!!");
		}
		return this.message;
	}
}
