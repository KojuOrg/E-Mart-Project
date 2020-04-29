package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="product_report")
public class ProductReport {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="report_id")
	private int id;
	@Column(name="user_id")
	private int userId;
	@Column(name="product_id")
	private int productId;
	@Column(name="report")
	private String report;
	@Column(name="reg_date")
	private String regDate;
	@Column(name="del_date")
	private String delDate;
	@Column(name="status")
	private boolean status;
	public ProductReport() {
		this.id = 0;
		this.userId = 0;
		this.productId = 0;
		this.report = "";
		this.regDate = "";
		this.delDate = "1111/11/11";
		this.status = false;
	}
	public ProductReport(int id, int userId, int productId, String report, String regDate, String delDate,
			boolean status) {
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.report = report;
		this.regDate = regDate;
		this.delDate = delDate;
		this.status = status;
	}
	public ProductReport(ProductReport rep) {
		this.id = rep.id;
		this.userId = rep.userId;
		this.productId = rep.productId;
		this.report = rep.report;
		this.regDate = rep.regDate;
		this.delDate = rep.delDate;
		this.status = rep.status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getDelDate() {
		return delDate;
	}
	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ProductReport [id=" + id + ", userId=" + userId + ", productId=" + productId + ", report=" + report
				+ ", regDate=" + regDate + ", delDate=" + delDate + ", status=" + status + "]";
	}
}
