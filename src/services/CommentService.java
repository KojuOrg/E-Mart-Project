package services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import beans.Comment;

@Service
public class CommentService {
	private SessionFactory factory;
	private Session session;
	private int productId;
	private void initValues() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Comment.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	private CommentService() {
		this.initValues();
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public List<Comment> getComments(){
		List<Comment> comments = new ArrayList<Comment>();
		try {
			this.session.beginTransaction();
			String HQL = "FROM Comment WHERE productId=?";
			Query query = this.session.createQuery(HQL);
			query.setInteger(0,this.productId);
			comments = query.list();
			this.session.getTransaction().commit();
		}catch(Exception er) {
			comments = null;
		}
		return comments;
	}
}
