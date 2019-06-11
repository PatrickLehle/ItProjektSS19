package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * Die ProfileForm zeigt das Profil eines Users
 * 
 * @author vanduyho
 *
 */

public class ProfileForm extends VerticalPanel {
	
	
	//LABELS================================================
	Label newProfile = new Label("Dein Profil: ");
	Label firstName = new Label("Vorname: ");
	Label lastName = new Label("Nachname :");

	
	//BUTTONS===============================================
	Button editButton = new Button ("Bearbeiten");
	Button deleteButton = new Button("Löschen");
	
	
	//PANELS================================================
	HorizontalPanel newProfilePanel = new HorizontalPanel(); //Panel fuer Ueberschrift
	HorizontalPanel buttonPanel = new HorizontalPanel(); // Panel fuer Buttons
	
	/**
	 * 
	 * Konstruktor fuer die ProfileForm Klasse 
	 * 
	 */

	//public ProfileForm(Profile p) {
		
	//}
	
}
