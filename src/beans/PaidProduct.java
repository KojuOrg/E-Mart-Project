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
@Table(name="paid_products")
public class PaidProduct {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="product_id")
	private int productId;
	@Column(name="user_id")
	private int userId;
	@Column(name="product_price")
	private float productPrice;
	@Column(name="paid_price")
	private float paidPrice;
	@Column(name="tax")
	private float tax;
	@Column(name="net_price")
	private float netPrice;
	@Column(name="status")
	private boolean status;
	@Column(name="unique_code")
	private String uniqueCode;
	public PaidProduct() {
		this.id = 0;
		this.productId = 0;
		this.userId = 0;
		this.productPrice = 0.0f;
		this.paidPrice = 0.0f;
		this.tax = 0.0f;
		this.netPrice = 0.0f;
		this.status = false;
		this.uniqueCode = "";
	}
	public PaidProduct(int id, int productId, int userId, float productPrice, float paidPrice, float netPrice, float tax,
			boolean status, String uniqueCode) {
		this.id = id;
		this.productId = productId;
		this.userId = userId;
		this.productPrice = productPrice;
		this.paidPrice = paidPrice;
		this.tax = tax;
		this.netPrice = netPrice;
		this.status = status;
		this.uniqueCode = uniqueCode;
	}
	public PaidProduct(PaidProduct pp) {
		this.id = pp.id;
		this.productId = pp.productId;
		this.userId = pp.userId;
		this.productPrice = pp.productPrice;
		this.paidPrice = pp.paidPrice;
		this.tax = pp.tax;
		this.netPrice = pp.netPrice;
		this.status = pp.status;
		this.uniqueCode = pp.uniqueCode;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public float getPaidPrice() {
		return paidPrice;
	}
	public void setPaidPrice(float paidPrice) {
		this.paidPrice = paidPrice;
	}
	public float getTax() {
		return tax;
	}
	public void setTax(float tax) {
		this.tax = tax;
	}
	public float getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(float netPrice) {
		this.netPrice = netPrice;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	@Override
	public String toString() {
		return "PaidProduct [id=" + id + ", productId=" + productId + ", userId=" + userId + ", productPrice="
				+ productPrice + ", paidPrice=" + paidPrice + ", tax=" + tax + ", netPrice=" + netPrice + ", status="
				+ status + ", uniqueCode=" + uniqueCode + "]";
	}
}
