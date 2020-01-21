package beans;

import org.springframework.stereotype.Component;

@Component
public class UserLogin {
	private String userName;
	private String userPass;
	public UserLogin() {
		this.userName = "";
		this.userPass = "";
	}
	public UserLogin(String userName, String userPass) {
		this.userName = userName;
		this.userPass = userPass;
	}
	public UserLogin(UserLogin usr) {
		this.userName = usr.userName;
		this.userPass = usr.userPass;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	@Override
	public String toString() {
		return "AccountLogin [userName=" + userName + ", userPass=" + userPass + "]";
	}
}
