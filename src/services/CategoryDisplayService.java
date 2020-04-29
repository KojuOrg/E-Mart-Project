package services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import beans.Product;
@Service
public class CategoryDisplayService {
	@Autowired
	private Product product;
	private SessionFactory factory;
	private Session session;
	private List<Product> specificProducts;
	private List<Product> allProducts;
	private void initValues() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Product.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
		this.allProducts = new ArrayList<Product>();
		this.specificProducts = new ArrayList<Product>();
	}
	public CategoryDisplayService() {
		this.initValues();
	}
	private void getAllProducts(){
		try {
			this.session.beginTransaction();
			this.allProducts = this.session.createQuery("FROM Product WHERE status=0").list();
			this.session.getTransaction().commit();
		}catch(Exception er) {
			System.out.println("Error : "+er.getMessage());
		}
	}
	@Cacheable("products")
	private void getSpecificProducts(String category) {
		for(int i=0;i<this.allProducts.size();i++) {
			if(this.allProducts.get(i).getCategory().equals(category)) {
				this.allProducts.get(i).setPhoto1(Base64.getEncoder().encodeToString(this.allProducts.get(i).getPhoto1File()));
				this.allProducts.get(i).setPhoto2(Base64.getEncoder().encodeToString(this.allProducts.get(i).getPhoto2File()));
				this.allProducts.get(i).setPhoto3(Base64.getEncoder().encodeToString(this.allProducts.get(i).getPhoto3File()));
				this.specificProducts.add(this.allProducts.get(i));
			}
		}
		Collections.reverse(this.specificProducts);
	}
	
	@Cacheable("products")
	public List<Product> getProducts(String category) {
		this.initValues();
		this.getAllProducts();
		this.getSpecificProducts(category);
		return this.specificProducts;
	}
	
	@CacheEvict(value = "products", allEntries = true)
	public void refreshAllProducts()
	{
		/*
		 * This method will remove all 'products' from cache, say as a
		 * result of flush API.
		 */
	}
	
}
