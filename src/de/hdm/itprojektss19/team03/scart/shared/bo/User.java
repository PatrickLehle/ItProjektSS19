<<<<<<< HEAD
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
=======
package de.hdm.itprojektss19.team03.scart.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung eines Users. Der User wird hier mit einer Email Adresse
 * hinterlegt, mit welche er sich registriert.
 * 
 * @see <code>BusinessObject</code>
 * 
 * @author PatrickLehle
 */
public class User extends BusinessObject implements IsSerializable {

	/**
	 * Eindeutige Identifikation einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariable von Klasse User
	 */
	private String username = "";

	/*
	 * Auslesen der Email
	 * 
	 * @return email - Email Adresse des Users
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * setzen der Email
	 * 
	 * @param email des Users
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
>>>>>>> dev
