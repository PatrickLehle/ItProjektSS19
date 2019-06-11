package de.hdm.itprojektss19.team03.scart.client.gui;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;


/**
 * Die ProfileForm zeigt das Profil eines Users
 * 
 * @author vanduyho
 *
 */

public class ProfileForm extends VerticalPanel {
	
	User user = new User();
	
	//LABELS================================================
	Label newProfile = new Label("Dein Profil");
	Label firstName = new Label("Vorname: ");
	Label lastName = new Label("Nachname: ");
	Label emailAdress = new Label("E-Mail: ");

	
	//BUTTONS===============================================
	Button editButton = new Button ("Bearbeiten");
	Button deleteButton = new Button("Löschen");
	
	
	//PANELS================================================
	
	HorizontalPanel newProfilePanel = new HorizontalPanel(); //Panel fuer Ueberschrift
	HorizontalPanel firstNamePanel = new HorizontalPanel(); //Panel fuer Vorname
	HorizontalPanel lastNamePanel = new HorizontalPanel(); //Panel fuer Nachname
	HorizontalPanel emailAdressPanel = new HorizontalPanel(); //Panel fuer E-Mail Adresse
	HorizontalPanel buttonPanel = new HorizontalPanel(); // Panel fuer Buttons
	
	/**
	 * 
	 * Konstruktor fuer die ProfileForm Klasse 
	 * 
	 */

	public ProfileForm(User u) {
		
		this.user = u;
		
	}
	
	
	
}
