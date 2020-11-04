package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="product_view")
public class ProductViewer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="product_id")
	private int productId;
	@Column(name="user_cookies")
	private String userCookies;
	public ProductViewer() {
		this.id = 0;
		this.productId = 0;
		this.userCookies = "";
	}
	public ProductViewer(int id, int productId, String userCookies) {
		this.id = id;
		this.productId = productId;
		this.userCookies = userCookies;
	}
	public ProductViewer(ProductViewer pv) {
		this.id = pv.id;
		this.productId = pv.productId;
		this.userCookies = pv.userCookies;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getUserCookies() {
		return userCookies;
	}
	public void setUserCookies(String userCookies) {
		this.userCookies = userCookies;
	}
	@Override
	public String toString() {
		return "ProductViewer [id=" + id + ", productId=" + productId + ", userCookies=" + userCookies + "]";
	}
}
