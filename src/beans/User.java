package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Entity
@Table(name="user")
@Component
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private int id;
	@Column(name="full_name")
	private String fullName;
	@Column(name="user_name")
	private String userName;
	@Column(name="address")
	private String address;
	@Column(name="email")
	private String email;
	@Column(name="contact_num")
	private String contactNum;
	@Column(name="password")
	private String pwd;
	@Column(name="reg_date")
	private String regDate;
	@Column(name="del_date")
	private String delDate;
	@Column(name="num_of_uploaded_items")
	private int numOfUploadedItem;
	@Column(name="status")
	private boolean status;
	@Column(name="invalid_count")
	private int invalidCount;
	public User() {
		this.id = 1;
		this.fullName = "";
		this.userName = "";
		this.address = "";
		this.email = "";
		this.contactNum = "";
		this.pwd = "";
		this.regDate = "";
		this.delDate = "";
		this.numOfUploadedItem = 0;
		this.status = false;
	}
	public User(int id, String fullName, String userName, String address, String email, String contactNum, String pwd,
			String regDate, String delDate, int numOfUploadedItem, boolean status) {
		this.id = id;
		this.fullName = fullName;
		this.userName = userName;
		this.address = address;
		this.email = email;
		this.contactNum = contactNum;
		this.pwd = pwd;
		this.regDate = regDate;
		this.delDate = delDate;
		this.numOfUploadedItem = numOfUploadedItem;
		this.status = status;
	}
	public User(User usr) {
		this.id = usr.id;
		this.fullName = usr.fullName;
		this.userName = usr.userName;
		this.address = usr.address;
		this.email = usr.email;
		this.contactNum = usr.contactNum;
		this.pwd = usr.pwd;
		this.regDate = usr.regDate;
		this.delDate = usr.delDate;
		this.numOfUploadedItem = usr.numOfUploadedItem;
		this.status = usr.status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNum() {
		return contactNum;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
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
	public int getNumOfUploadedItem() {
		return numOfUploadedItem;
	}
	public void setNumOfUploadedItem(int numOfUploadedItem) {
		this.numOfUploadedItem = numOfUploadedItem;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getInvalidCount() {
		return invalidCount;
	}
	public void setInvalidCount(int invalidCount) {
		this.invalidCount = invalidCount;
	}
	public int getUserId() {
		return id;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", userName=" + userName + ", address=" + address
				+ ", email=" + email + ", contactNum=" + contactNum + ", pwd=" + pwd + ", regDate=" + regDate
				+ ", delDate=" + delDate + ", numOfUploadedItem=" + numOfUploadedItem + ", status=" + status + "]";
	}
}
