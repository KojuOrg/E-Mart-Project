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
@Table(name="user_views")
public class UserViews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
	@Column(name ="view_id")
	private int view_id;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="num_of_views")
	private int numOfViews;
	
	@Column(name="category")
	private String category;
	
	public UserViews(String userName, int numOfViews, String category) {
		this.userName = userName;
		this.numOfViews = numOfViews;
		this.category = category;
	}
	
	public UserViews() {
		this.userName = "";
		this.numOfViews = 0;
		this.category = "";
	}
	
	public UserViews(UserViews userViews) {
		this.userName = userViews.userName;
		this.numOfViews = userViews.numOfViews;
		this.category = userViews.category;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getNumOfViews() {
		return numOfViews;
	}

	public void setNumOfViews(int numOfViews) {
		this.numOfViews = numOfViews;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "UserViews [userName=" + userName + ", numOfViews=" + numOfViews + ", category=" + category + "]";
	}
	
	
}
