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
@Table(name="comment_details")
public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="comment_id")
	private int id;
	@Column(name="user_name")
	private String userName;
	@Column(name="product_id")
	private int productId;
	@Column(name="comment")
	private String comment;
	@Column(name="reg_date")
	private String regDate;
	@Column(name="del_date")
	private String delDate;
	@Column(name="status")
	private boolean status;
	public Comment() {
		this.id = 0;
		this.userName = "";
		this.productId = 0;
		this.comment = "";
		this.regDate = "";
		this.delDate = "1111-11-11";
		this.status = false;
	}
	public Comment(int id, String userName, int productId, String comment, String regDate, String delDate, boolean status) {
		this.id = id;
		this.userName = userName;
		this.productId = productId;
		this.comment = comment;
		this.regDate = regDate;
		this.delDate = delDate;
		this.status = status;
	}
	public Comment(Comment cmt) {
		this.id = cmt.id;
		this.userName = cmt.userName;
		this.productId = cmt.productId;
		this.comment = cmt.comment;
		this.regDate = cmt.regDate;
		this.delDate = cmt.delDate;
		this.status = cmt.status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
		return "Comment [id=" + id + ", userName=" + userName + ", productId=" + productId + ", comment=" + comment
				+ ", regDate=" + regDate + ", delDate=" + delDate + ", status=" + status + "]";
	}
}
