package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author vanduyho
 *
 */

public class EditProfile extends VerticalPanel {
	
	//LABELS================================================
	Label newProfile = new Label("Dein Profil");
	Label firstName = new Label("Vorname: ");
	Label lastName = new Label("Nachname: ");
	Label emailAdress = new Label("E-Mail: ");

	
	//BUTTONS===============================================
	Button editButton = new Button ("Profil bearbeiten");
	
	
	//PANELS================================================
	
	VerticalPanel contentPanel = new VerticalPanel(); // Panel für Content
	
	HorizontalPanel newProfilePanel = new HorizontalPanel(); //Panel fuer Ueberschrift
	HorizontalPanel firstNamePanel = new HorizontalPanel(); //Panel fuer Vorname
	HorizontalPanel lastNamePanel = new HorizontalPanel(); //Panel fuer Nachname
	HorizontalPanel emailAdressPanel = new HorizontalPanel(); //Panel fuer E-Mail Adresse
	HorizontalPanel buttonPanel = new HorizontalPanel(); //Panel fuer Buttons

	
	
	
}
