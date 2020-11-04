package services;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import beans.Admin;
import beans.AdminDetail;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private Admin admin;
	
	@Autowired
	private AdminService adminService;
	
	private SessionFactory factory;
	private Session session;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Admin.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
		this.session.beginTransaction();
		String HQL = "FROM Admin WHERE userName=?";
		Query query = this.session.createQuery(HQL);
		query.setParameter(0, username);
		this.admin = (Admin)query.uniqueResult();
		this.session.getTransaction().commit();
		
		if(this.admin==null) {
			throw new UsernameNotFoundException("Invalid User / Password");
		}
		else {
			if(this.adminService.isAdminLocked(username)) {
				AdminDetail adminDetail = new AdminDetail(this.admin);
				adminDetail.setAccountLockStatus(false);
				return adminDetail;
			}
		}
		return new AdminDetail(this.admin);
	}

}
