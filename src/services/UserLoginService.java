package services;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import beans.Message;
import beans.User;
import beans.UserLogin;

public class UserLoginService {
	@Autowired
	private UserLogin user;
	private User tmpUser;
	@Autowired
	private Message message;
	private SessionFactory factory;
	private Session session;

	private void sessionInit() {
		this.factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}

	public UserLoginService(UserLogin user) {
		this.user = user;
		this.message = new Message();
		this.sessionInit();
	}

	public Message validateUser() {
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
				this.message.setStatus(false);
				this.message.setMessage(this.tmpUser.getUserName());
			} else {
				this.sessionInit();
				this.session.beginTransaction();
				HQL = "FROM User WHERE email=? AND pwd=?";
				query = this.session.createQuery(HQL);
				query.setString(0, this.user.getUserName());
				query.setString(1, this.user.getUserPass());
				this.tmpUser = new User();
				this.tmpUser = (User) query.uniqueResult();
				this.session.getTransaction().commit();
				if (this.tmpUser != null) {
					this.message.setStatus(false);
					this.message.setMessage(this.tmpUser.getUserName());
				} else {
					this.message.setStatus(true);
					this.message.setMessage("Invalid User or Password.!!!!");
				}
			}
		} catch (Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error ......!!!!");
		}
		return this.message;
	}
}
