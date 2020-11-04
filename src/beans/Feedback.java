package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;
import javax.validation.constraints.Email;

@Component
@Entity
@Table(name="feedback_details")
public class Feedback {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="feedback_id")
	private int id;
	
	@NotEmpty(message="Name Must Not be Empty..!!!")
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="email")
	@Email
	@NotEmpty(message="Email Required.!!!")
	private String email;
	
	@NotEmpty(message="Subject Required..!!!")
	@Column(name="subject")
	private String subject;
	
	@Column(name="feedback")
	@NotEmpty(message="Message Must Not be Empty..!!!")
	private String feedback;
	
	@Column(name="reg_date")
	private String regDate;
	
	@Column(name="del_date")
	private String delDate;
	
	@Column(name="status")
	private boolean status;
	public Feedback() {
		this.id = 0;
		this.fullName = "";
		this.email = "";
		this.subject = "";
		this.feedback = "";
		this.regDate = "";
		this.delDate = "1111-11-11";
		this.status = false;
	}
	public Feedback(int id, String fullName, String email,String subject, String feedback, String regDate, String delDate,
			boolean status) {
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.subject = subject;
		this.feedback = feedback;
		this.regDate = regDate;
		this.delDate = delDate;
		this.status = status;
	}
	public Feedback(Feedback fb) {
		this.id = fb.id;
		this.fullName = fb.fullName;
		this.email = fb.email;
		this.subject = fb.subject;
		this.feedback = fb.feedback;
		this.regDate = fb.regDate;
		this.delDate = fb.delDate;
		this.status = fb.status;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "Feedback [id=" + id + ", fullName=" + fullName + ", email=" + email + ", subject=" + subject
				+ ", feedback=" + feedback + ", regDate=" + regDate + ", delDate=" + delDate + ", status=" + status
				+ "]";
	}
}
