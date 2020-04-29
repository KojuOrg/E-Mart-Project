package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
	public List<Comment> getComments(int id){
		this.initValues();
		//List<Comment> comments = new ArrayList<Comment>();
		List<Comment> comments = new LinkedList<>();
		try {
			this.session.beginTransaction();
			String HQL = "FROM Comment WHERE productId="+id;
			Query query = this.session.createQuery(HQL);
			 
			 for(Object o : query.list()) {
			     comments.add((Comment)o);
			 }
			//query.setInteger(0,this.productId);
			//query.setInteger(0,1);
			//List list = query.list();
			//List<Comment> comments = query.list();
			this.session.getTransaction().commit();
		}catch(Exception er) {
			System.out.println(er);
			comments = null;
		}
		return comments;
	}
	
	public boolean setComment(Comment comment) {
		
		if(ProfanityFilter.getCensoredText(comment.getComment()) == null)
			return false;
		
		this.initValues();
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dateobj = new Date();
			String regDate=df.format(dateobj);
			comment.setRegDate(regDate);
			session.beginTransaction();
			session.save(comment);
			session.getTransaction().commit();
		}
		catch(Exception er) {
			System.out.println(er);
		}
		return true;
	}
}
