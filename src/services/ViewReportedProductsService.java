package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public class ViewReportedProductsService {
	private Connection con;
	private String driver ;
	private String url ;
	private String user ;
	private String pass ;
	private PreparedStatement pst;
	private String sql;
	private ResultSet rs;

	public ViewReportedProductsService() {
		this.driver = "com.mysql.jdbc.Driver";
		this.url = "jdbc:mysql://localhost:3306/emart";
		this.user = "admin";
		this.pass = "admin";
	}
	public void DBConnect() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception er) {
			System.out.println("Connection Error : "+er.getMessage());
		}
	}
	public List<Object> getReportedProducts(){
		List<Object> reportedProducts = new ArrayList<Object>(); 
		List<Object> product;
		try {
			this.sql = "SELECT p.product_id,p.product_name,p.category,u.full_name,pr.reg_date,pr.report_id FROM product p JOIN product_report pr JOIN user u ON p.product_id=pr.product_id AND u.user_id=pr.user_id WHERE p.status=0 ORDER BY pr.report_id DESC";
			this.pst = this.con.prepareStatement(this.sql);
			this.rs = this.pst.executeQuery();
			while(rs.next()) {
				product = new ArrayList<Object>();
				product.add(rs.getLong(1));
				product.add(rs.getString(2));
				product.add(rs.getString(3));
				product.add(rs.getString(4));
				product.add(rs.getDate(5));
				product.add(rs.getLong(6));
				reportedProducts.add(product);
			}
			
		}catch(Exception er) {
			System.out.println("Sql Error : "+er.getMessage());
			reportedProducts = null;
		}
		return reportedProducts;
	}
	private Map setProductSpecifications(String ps) {
		String[] specification = ps.split("%main%");
		String[] titles = specification[0].split("%sub%");
		String[] desc = specification[1].split("%sub%");
		Map<String,String> spec = new HashMap<String,String>();
		for(int i=0;i<titles.length;i++) {
			spec.put(titles[i],desc[i]);
		}
		return spec;
	}
	public List<Object> getSingleReport(Long reportId){
		List<Object> report = new ArrayList<Object>();
		try {
			this.sql = "SELECT * FROM product p JOIN product_report pr JOIN user u ON p.product_id=pr.product_id AND u.user_id=pr.user_id WHERE pr.report_id=? AND p.status=0";
			this.pst = this.con.prepareStatement(this.sql);
			this.pst.setLong(1,reportId);
			this.rs = this.pst.executeQuery();
			while(rs.next()) {
				report = new ArrayList<Object>();
				report.add(rs.getString(3));
				report.add(rs.getString(4));
				report.add(rs.getString(5));
				if(rs.getBoolean(6)) {
					report.add("Yes");
				}
				else {
					report.add("No");
				}
				report.add(rs.getByte(7));
				report.add(rs.getString(8));
				
				report.add(this.setProductSpecifications(rs.getString(9)));
				
				report.add(rs.getString(10));
				report.add(rs.getString(11));
				report.add(rs.getBigDecimal(12));
				report.add(rs.getDate(13));
				
				report.add(Base64.getEncoder().encodeToString(rs.getBytes(16)));
				report.add(Base64.getEncoder().encodeToString(rs.getBytes(17)));
				report.add(Base64.getEncoder().encodeToString(rs.getBytes(18)));
				
				
				report.add(rs.getString(26));
				report.add(rs.getDate(27));
				
				report.add(rs.getString(31));
				report.add(rs.getString(33));
				report.add(rs.getString(34));
				report.add(rs.getString(35));
				report.add(rs.getDate(37));
				report.add(rs.getInt(2));
			}
			
		}catch(Exception er) {
			System.out.println("Sql Error : "+er.getMessage());
			report = null;
		}
		return report;
	}
	public void DBClose() {
		try {
			this.con.close();
		}catch(Exception er) {
			System.out.println("Error : "+er.getMessage());
		}
	}
}
