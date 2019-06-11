package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * 
 * @author vanduyho
 *
 */

public class ProfileForm extends VerticalPanel {
	
	Label newProfile = new Label("Dein Profil: ");
	Label firstName = new Label("Vorname: ");
	Label lastName = new Label("Nachname :");

	Button editButton = new Button ("Bearbeiten");
	Button deleteButton = new Button("Löschen");
	
	HorizontalPanel newProfilePanel = new HorizontalPanel(); //Panel fuer Ueberschrift
	HorizontalPanel buttonPanel = new HorizontalPanel(); // Panel fuer Buttons
	
	

}
