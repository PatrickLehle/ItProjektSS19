package de.hdm.itprojektss19.team03.scart.shared.bo;
import java.util.ArrayList;

public class User {
	public static long serialVersionUID;

	private String name;
	private String email;
	private long userID;
	
	public User getUser() {
		
	}
	
	public User getUserByID(long UserID) {
		
	}
	
	public User getCurrentUser() {
		
	}
	
	public void setUserEmail(User user, String email) {
		
	}
	
	public void setUserName(String name) {
		
	}
	
	
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public static void setSerialVersionUID(long serialVersionUID) {
		User.serialVersionUID = serialVersionUID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
