package services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Message;
import beans.ProductReport;

@Service
public class ProductReportService {
	@Autowired
	private ProductReport preport;
	@Autowired
	private Message message;
	private SessionFactory factory;
	private Session session;
	private void initValues() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(ProductReport.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	public ProductReportService() {
		this.initValues();
	}
	public void setReport(ProductReport preport) {
		this.preport = preport;
	}
	public Message uploadReport() {
		this.initValues();
		try {
			this.session.beginTransaction();
			this.session.save(this.preport);
			this.session.getTransaction().commit();
			this.message.setStatus(false);
			this.message.setMessage("Your Report is recorded.!!");
		}catch(Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error.!!!");
			System.out.println("Error : "+er.getLocalizedMessage());
		}
		return this.message;
	}
	public void checkProduct() {
		this.initValues();
		try {
			this.session.beginTransaction();
			String HQL = "SELECT id,userId,productName,";
			this.session.getTransaction().commit();
		}catch(Exception er) {
			System.out.println("Error : "+er.getMessage());
		}
	}
}
