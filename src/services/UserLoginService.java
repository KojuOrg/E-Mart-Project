package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Message;
import beans.User;
import beans.UserLogin;

@Service
public class UserLoginService {
	@Autowired
	private UserLogin user;
	@Autowired
	private User tmpUser;
	@Autowired
	private Message message;
	private SessionFactory factory;
	private Session session;

	private void sessionInit() {
		this.factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
		this.message = new Message();
	}

	public UserLoginService() {
		this.sessionInit();
	}

	public void setUser(UserLogin user) {
		this.user = user;
		this.sessionInit();
	}

	public Message validateUser(HttpServletRequest request) {
		try {
			this.session.beginTransaction();
			String HQL = "FROM User WHERE userName=? AND pwd=?";
			Query query = this.session.createQuery(HQL);
			query.setString(0, this.user.getUserName());
			query.setString(1, this.user.getUserPass());
			this.tmpUser = new User();
			this.tmpUser = (User) query.uniqueResult();
			this.session.getTransaction().commit();
			if (this.tmpUser != null) {
				if (this.tmpUser.getInvalidCount() >= 5) {
					this.message.setStatus(true);
					this.message.setMessage(
							"Sorry Due to multiple invalid access your account has been temporarily locked.!! Please proceed through forget password method to recover your account.");
				} else {
					this.sessionInit();
					this.session.beginTransaction();
					HQL = "FROM User WHERE userName=? AND pwd=?";
					query = this.session.createQuery(HQL);
					query.setString(0, this.user.getUserName());
					query.setString(1, this.user.getUserPass());
					this.tmpUser = new User();
					this.tmpUser = (User) query.uniqueResult();
					this.tmpUser.setInvalidCount(0);
					this.session.getTransaction().commit();
					HttpSession sess = request.getSession();
					sess.setAttribute("userName", tmpUser.getUserName());
					sess.setAttribute("userId", tmpUser.getId());
					this.message.setStatus(false);
					this.message.setMessage(this.tmpUser.getUserName());
				}
			} else {
				this.sessionInit();
				this.session.beginTransaction();
				HQL = "FROM User WHERE userName=?";
				query = this.session.createQuery(HQL);
				query.setString(0, this.user.getUserName());
				this.tmpUser = new User();
				this.tmpUser = (User) query.uniqueResult();
				if (this.tmpUser != null && this.tmpUser.getInvalidCount() < 5) {
					this.tmpUser.setInvalidCount(this.tmpUser.getInvalidCount() + 1);
				}
				this.session.getTransaction().commit();
				this.message.setStatus(true);
				this.message.setMessage("Invalid User or Password.!!!!");
			}
		} catch (Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error ......!!!!");
			System.out.println("Error : "+er.getLocalizedMessage());
		}
		return this.message;
	}
}
