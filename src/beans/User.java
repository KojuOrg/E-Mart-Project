package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="user")
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
	@Column(name ="user_id")
	private int id;
	
	@Column(name="full_name")
	@NotEmpty(message="is required")
	private String fullName;
	
	@Column(name="user_name")
	@NotEmpty(message="is required")
	//@UniqueUsername
	private String userName;
	
	@Column(name="address")
	@NotEmpty(message="is required")
	private String address;
	
	@Column(name="email")
	@NotEmpty(message="is required")
	@Email
	private String email;
	
	@Column(name="contact_num")
	@NotEmpty(message="is required")
	@Size(min=10, max=10, message="invalid format")
	@Pattern(regexp="^[0-9]*$")
	private String contactNum;
	
	@Column(name="password")
	@NotEmpty(message="is required")
	@Size(min=7, message="length must be atleast 7 characters")
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
	
	public int getInvalidCount() {
		return invalidCount;
	}

	public void setInvalidCount(int invalidCount) {
		this.invalidCount = invalidCount;
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
	
	public User() {
		this.id = 0;
		this.fullName = "";
		this.userName = "";
		this.address = "";
		this.email = "";
		this.contactNum = "";
		this.pwd = "";
		this.regDate = "1111-11-11";
		this.delDate = "1111-11-11";
		this.numOfUploadedItem = 0;
		this.status = false;
	}
	
	public User(User user) {
		this.id = user.id;
		this.fullName = user.fullName;
		this.userName = user.userName;
		this.address = user.address;
		this.email = user.email;
		this.contactNum = user.contactNum;
		this.pwd = user.pwd;
		this.regDate = user.regDate;
		this.delDate = user.delDate;
		this.numOfUploadedItem = user.numOfUploadedItem;
		this.status = user.status;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", userName=" + userName + ", address=" + address
				+ ", email=" + email + ", contactNum=" + contactNum + ", pwd=" + pwd + ", regDate=" + regDate
				+ ", delDate=" + delDate + ", numOfUploadedItem=" + numOfUploadedItem + ", status=" + status + "]";
	}
	
	
}
