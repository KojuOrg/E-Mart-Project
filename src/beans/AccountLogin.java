package beans;

public class AccountLogin {
	private String userName;
	private String userPass;
	public AccountLogin() {
		this.userName = "";
		this.userPass = "";
	}
	public AccountLogin(String userName, String userPass) {
		this.userName = userName;
		this.userPass = userPass;
	}
	public AccountLogin(AccountLogin usr) {
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
