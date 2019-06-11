package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * @author vanduyho
 *
 */

public class ProfileForm {

	User user = null;
	
	Label newProfile = null;
	Label firstName = null;
	Label lastName = null;
	Label emailAdress = null;
	
	Button editButton = null;
	Button deleteButtton = null;
	
	VerticalPanel newProfilePanel = null;
	
	HorizontalPanel firstNamePanel = null;
	HorizontalPanel lastNamePanel = null;
	HorizontalPanel emailAdressPanel = null;
	HorizontalPanel buttonPanel = null;

	/**
	 * Konstruktor fuer die ProfileForm Klasse 
	 * 
	 */
	
	public ProfileForm (User u) {
		
		this.user = u;
		
	}
	
}
