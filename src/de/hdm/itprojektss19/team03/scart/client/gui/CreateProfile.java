package de.hdm.itprojektss19.team03.scart.client.gui;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;


/**
 * Die CreateProfile erzeugt einen neuen User
 * 
 * @author vanduyho
 *
 */

public class CreateProfile extends VerticalPanel {
	
	//LABELS================================================
	Label newProfile = new Label("Dein Profil");
	Label firstName = new Label("Vorname: ");
	Label lastName = new Label("Nachname: ");
	Label emailAdress = new Label("E-Mail: ");

	
	//BUTTONS===============================================
	Button editButton = new Button ("Bearbeiten");
	Button deleteButton = new Button("Löschen");
	
	
	//PANELS================================================
	
	VerticalPanel contentPanel = new VerticalPanel(); // Panel für Content
	
	HorizontalPanel newProfilePanel = new HorizontalPanel(); //Panel fuer Ueberschrift
	HorizontalPanel firstNamePanel = new HorizontalPanel(); //Panel fuer Vorname
	HorizontalPanel lastNamePanel = new HorizontalPanel(); //Panel fuer Nachname
	HorizontalPanel emailAdressPanel = new HorizontalPanel(); //Panel fuer E-Mail Adresse
	HorizontalPanel buttonPanel = new HorizontalPanel(); //Panel fuer Buttons
	
	/**
	 * 
	 * Konstruktor fuer die CreateProfile Klasse 
	 * 
	 */

	public CreateProfile(User user) {
		
		//Labels in jeweilige Panels hinzufuegen
		
		newProfilePanel.add(newProfile);
		firstNamePanel.add(firstName);
		lastNamePanel.add(lastName);
		emailAdressPanel.add(emailAdress);

			
		//Buttons in buttonPanel hinzugfuegen
		
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		
		//Panels in contentPanels hinzufuegen
				
		contentPanel.add(newProfilePanel);
		contentPanel.add(firstNamePanel);
		contentPanel.add(lastNamePanel);
		contentPanel.add(emailAdressPanel);
		
	}
	
	
	
}
