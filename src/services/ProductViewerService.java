package services;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Product;
import beans.ProductViewer;

@Service
public class ProductViewerService {
	@Autowired
	private Product product;
	@Autowired
	private ProductViewer productViewer,tmpProductViewer;
	private boolean isUserOld; 
	
	private Session session;
	private SessionFactory factory;
	
	public ProductViewerService() {
		this.isUserOld = false;
	}
	
	public void initProductSession() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Product.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	public void initProductViewerSession() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(ProductViewer.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
	}
	public void manageProductViewNumber(ProductViewer pViewer) {
		this.productViewer = pViewer;
		this.initProductViewerSession();
		try {
			this.session.beginTransaction();
			String SQL = "From ProductViewer WHERE userCookies=? AND productId=?";
			Query query = this.session.createQuery(SQL);
			query.setString(0,pViewer.getUserCookies());
			query.setInteger(1, pViewer.getProductId());
			this.tmpProductViewer = (ProductViewer)query.uniqueResult();
			if(this.tmpProductViewer!=null) {
				this.isUserOld = true;
				System.out.println(this.tmpProductViewer);
			}
			else {
				this.isUserOld = false;
				this.session.save(pViewer);
			}
			this.session.getTransaction().commit();
			if(!this.isUserOld) {
				this.initProductSession();
				this.session.beginTransaction();
				this.product = (Product)this.session.get(Product.class, this.productViewer.getProductId());
				this.product.setNoOfViews(this.product.getNoOfViews()+1);
				this.session.getTransaction().commit();
			}
		}catch(Exception er) {
			this.session.getTransaction().commit();
			System.out.println("Error : "+er.getMessage());
		}
	}
}
