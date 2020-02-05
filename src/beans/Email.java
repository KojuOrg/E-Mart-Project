package beans;

import org.springframework.stereotype.Component;

@Component
public class Email {

	String email;
	String code;
	String verifyCode;
	int userId;
	
	public Email(String email, String code, String verifyCode, int userId) {
		this.email = email;
		this.code = code;
		this.verifyCode = verifyCode;
		this.userId = userId;
	}
	
	public Email() {
		this.email = "";
		this.code = "";
		this.verifyCode="";
		this.userId=0;
	}
	
	public Email(Email email) {
		this.email = email.email;
		this.code = email.code;
		this.verifyCode= email.verifyCode;
		this.userId= email.userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Email [email=" + email + ", code=" + code + ", verifyCode=" + verifyCode + ", userId=" + userId + "]";
	}

	

	
	
}
