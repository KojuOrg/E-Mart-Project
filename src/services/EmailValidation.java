package services;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Email;
import beans.Message;
import beans.User;

@Service
public class EmailValidation {
	@Autowired
	Message message;
	//Message message= new Message();
	SessionFactory factory;
	Session session;
	
	
	public Message confirmEmail(Email email) {
		System.out.println("i am in");
		String code=email.getCode();
		String verifyCode= email.getVerifyCode();
		int id=email.getUserId();
		System.out.println("code="+code);
		System.out.println("verifyCode="+verifyCode);
		if(code.equals(verifyCode))
		{
			factory = new Configuration().configure("hibernate.cfg.xml")
					.addAnnotatedClass(User.class)
					.buildSessionFactory();
			session = factory.getCurrentSession();
			try {
			session.beginTransaction();
			String qryString = "update User u set u.status=true where u.id=?";
	        Query query = session.createQuery(qryString);
	        query.setParameter(0, id);
	        query.executeUpdate();
	        session.getTransaction().commit();
			message.setStatus(false);
			}
			catch(Exception ex) {
				System.out.println("Error : "+ex.getMessage());
				message.setStatus(true);
			}
		}
		else {
			message.setStatus(true);
		}
		return this.message;
	}
}
