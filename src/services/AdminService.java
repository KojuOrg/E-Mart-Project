package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Admin;
import beans.Message;

@Service
public class AdminService {
	@Autowired
	private Admin admin, tmpAdmin;
	@Autowired
	private Message message;
	private SessionFactory factory;
	private Session session;
	private String email;
	private int code;
	private HttpServletRequest request;

	private void initValues() {
		this.factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Admin.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}

	public AdminService() {
		this.initValues();
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Message adminLogin() {
		this.initValues();
		try {
			this.session.beginTransaction();
			String HQL = "FROM Admin WHERE userName=? AND pwd=?";
			Query query = this.session.createQuery(HQL);
			query.setString(0, this.admin.getUserName());
			query.setString(1, this.admin.getPwd());
			this.tmpAdmin = (Admin) query.uniqueResult();
			this.session.getTransaction().commit();
			if (this.tmpAdmin != null) {
				if (this.tmpAdmin.getInvalidCount() >= 5) {
					this.message.setStatus(true);
					this.message.setMessage(
							"Account Locked Due to Multiple Invalid Access.!!! Go through Forget Password Method.");
				} else {
					this.initValues();
					this.session.beginTransaction();
					HQL = "FROM Admin WHERE userName=? AND pwd=?";
					query = this.session.createQuery(HQL);
					query.setString(0, this.admin.getUserName());
					query.setString(1, this.admin.getPwd());
					this.tmpAdmin = (Admin) query.uniqueResult();
					this.tmpAdmin.setInvalidCount(0);
					this.session.getTransaction().commit();
					this.message.setStatus(false);
					this.message.setMessage("Welcome Admin");
				}
			} else {
				this.initValues();
				this.session.beginTransaction();
				HQL = "FROM Admin WHERE userName=?";
				query = this.session.createQuery(HQL);
				query.setString(0, this.admin.getUserName());
				this.tmpAdmin = (Admin) query.uniqueResult();
				if (this.tmpAdmin != null && this.tmpAdmin.getInvalidCount() < 5) {
					this.tmpAdmin.setInvalidCount(this.tmpAdmin.getInvalidCount() + 1);
				}
				this.session.getTransaction().commit();
				this.message.setStatus(true);
				this.message.setMessage("Invalid User or Password.!!!");
			}
		} catch (Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error.!!!!!");
			System.out.println("Error : " + er.getMessage());
		}
		return this.message;
	}

	private void sendRecoveryCode() {
		String host = "smtp.gmail.com";
		String port = "587";
		String sender = "e42emart@gmail.com";
		String pwd = "Dangerous@123";
		String messageBody = this.code
				+ " is your Account Recovery code . If you have not requested Recovery Code then just ignore this email.";
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, pwd);
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
			HttpSession sess = this.request.getSession();
			sess.setAttribute("recoveryCode", this.code);
			sess.setAttribute("adminRecoveryEmail", email);
			this.message.setStatus(false);
			this.message.setMessage("Check Your Email");
		} catch (Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error Try Again Later.!!!");
		}
	}

	public Message checkEmail(String email, HttpServletRequest request) {
		this.initValues();
		try {
			this.session.beginTransaction();
			String HQL = "FROM Admin WHERE email=?";
			Query query = this.session.createQuery(HQL);
			query.setString(0, email);
			this.admin = (Admin) query.uniqueResult();
			this.session.getTransaction().commit();
			if (this.admin != null) {
				this.email = email;
				int code = (int) (Math.random() * 99999 + 10000);
				this.code = code;
				this.request = request;
				this.sendRecoveryCode();
			} else {
				this.message.setStatus(true);
				this.message.setMessage("Email Not Found....!!!!");
			}
		} catch (Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error...!!!!");
		}
		return this.message;
	}

	public Message setNewPassword(String pass, String email) {
		this.initValues();
		try {
			this.session.beginTransaction();
			String HQL = "From Admin WHERE email=?";
			Query query = this.session.createQuery(HQL);
			query.setString(0, email);
			this.admin = (Admin) query.uniqueResult();
			this.admin.setInvalidCount(0);
			this.admin.setPwd(pass);
			this.session.getTransaction().commit();
			this.message.setStatus(false);
			this.message.setMessage("Password Changed Successfully.!!!");
		} catch (Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error.!!!!");
		}
		return this.message;
	}

	public Admin getAdmin() {
		this.initValues();
		try {
			this.session.beginTransaction();
			String HQL = "FROM Admin WHERE userName=?";
			Query query = this.session.createQuery(HQL);
			query.setString(0, this.admin.getUserName());
			this.admin = (Admin) query.uniqueResult();
			this.session.getTransaction().commit();
		} catch (Exception er) {
			this.admin = null;
		}
		this.admin.setPwd("");
		return this.admin;
	}
	public Message updateAdmin(String userName,String oldPass) {
		this.initValues();
		try {
			this.session.beginTransaction();
			String HQL = "FROM Admin WHERE userName=? AND pwd=?";
			Query query = this.session.createQuery(HQL);
			query.setString(0,userName);
			query.setString(1,oldPass);
			this.tmpAdmin = (Admin)query.uniqueResult();
			if(this.tmpAdmin!=null) {
				this.tmpAdmin.setUserName(this.admin.getUserName());
				this.tmpAdmin.setEmail(this.admin.getEmail());
				this.tmpAdmin.setContactNo(this.admin.getContactNo());
				this.tmpAdmin.setPwd(this.admin.getPwd());
				this.message.setStatus(false);
				this.message.setMessage("Credentials changed successfully.!! Login to Continue.!!!");
			}
			else {
				this.message.setStatus(true);
				this.message.setMessage("Invalid Old Pass.!!!!");
			}
			this.session.getTransaction().commit();
		}catch(Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error...!!!");
		}
		return this.message;
	}
}
