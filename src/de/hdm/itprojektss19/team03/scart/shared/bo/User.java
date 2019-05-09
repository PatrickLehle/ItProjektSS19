package de.hdm.itprojektss19.team03.scart.shared.bo;


import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung eines Users. Der User wird hier mit einer Email Adresse hinterlegt, mit
 * welche er sich registriert.
 * 
 * @see <code>BusinessObject</code>
 * 
 * 
 * 
 * 
 * @author PatrickLehle
 */
public class User extends BusinessObject implements IsSerializable{
	
	/**
	 *  Eindeutige Identifikation einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instanzvariable von Klasse User
	 */
	private String email="";
	
	/*
	 * Auslesen der Email
	 * @return email - Email Adresse des Users
	 */
	public String getEmailAdress() {
		return email;
	}
	
	/**
	 * setzen der Email
	 * @param email des Users
	 */
	public void setEmailAdress(String email) {
		this.email = email;
	}
}
