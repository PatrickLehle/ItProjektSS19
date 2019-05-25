package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;


/**
 * Die RegistryForm wird zum Login aufgerufen, wenn der User
 * die Applikation zum ersten mal verwendet.
 * 
 * @author PatrickLehle
 */
public class RegistryForm extends VerticalPanel {
	
	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	
	
//TEXTBOXEN=======================================================
	
//LABELS==========================================================
	
//BUTTONS=========================================================
	
//PANELS==========================================================
	
	
	public RegistryForm(User u) {
		
		onLoad(u);
	}
	
	protected void onLoad(User u) {
		//Hier passiert etwas... wird spaeter implementiert <3
	}

}
