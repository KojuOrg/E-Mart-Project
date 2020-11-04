package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="admin")
public class Admin {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="admin_id")
	private int id;
	
	@Column(name="username")
	@NotEmpty(message="User Name Must Not be Empty..!!!")
	private String userName;
	
	@Column(name="password")
	@NotEmpty(message="Password Must Not be Empty..!!!")
	private String pwd;
	
	@Column(name="email")
	@Email
	@NotEmpty(message="Email Required.!!!")
	private String email;
	
	@Column(name="contact_num")
	@Size(min=10, max=10, message="invalid format")
	@Pattern(regexp="^[0-9]*$")
	@NotEmpty(message="Contact No Required..!!!")
	private String contactNo;
	
	@Column(name="invalid_count")
	private int invalidCount;
	public Admin() {
		this.id = 0;
		this.userName = "";
		this.pwd = "";
		this.email = "";
		this.contactNo = "";
		this.invalidCount = 0;
	}
	public Admin(int id, String userName, String pwd, String email, String contactNo, int invalidCount) {
		this.id = id;
		this.userName = userName;
		this.pwd = pwd;
		this.email = email;
		this.contactNo = contactNo;
		this.invalidCount = invalidCount;
	}
	public Admin(Admin adm) {
		this.id = adm.id;
		this.userName = adm.userName;
		this.pwd = adm.pwd;
		this.email = adm.email;
		this.contactNo = adm.contactNo;
		this.invalidCount = adm.invalidCount;
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public int getInvalidCount() {
		return invalidCount;
	}
	public void setInvalidCount(int invalidCount) {
		this.invalidCount = invalidCount;
	}
	@Override
	public String toString() {
		return "Admin [id=" + id + ", userName=" + userName + ", pwd=" + pwd + ", email=" + email + ", contactNo="
				+ contactNo + ", invalidCount=" + invalidCount + "]";
	}
}
