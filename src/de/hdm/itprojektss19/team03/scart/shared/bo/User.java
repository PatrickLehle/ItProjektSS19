package de.hdm.itprojektss19.team03.scart.shared.bo;


import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung eines Users. Der User wird hier mit einer Email Adresse
 * hinterlegt, mit welche er sich registriert.
 * 
 * @see <code>BusinessObject</code>
 * 
 * @author PatrickLehle, JulianHofer
 * 
 */
public class User extends BusinessObject implements IsSerializable {

	/**
	 * Eindeutige Identifikation einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;

	
	private String username = "";
	private String email = "";
	
//CONSTRUCTORS================================================
	public User() {
		
	}
	
	public User (String emailAdress) {
		this.email = emailAdress;
	}
	
	public User (String username, String emailAdress) {
			this.username = username;
			this.email = emailAdress;
	}

//METHODS======================================================
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsername() {
		return username;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getId() {
		return this.id;
	}
}
