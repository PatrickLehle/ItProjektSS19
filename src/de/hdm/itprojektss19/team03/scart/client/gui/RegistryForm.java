package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
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
	TextBox FnameTxtBox = new TextBox();
	TextBox LnameTxtBox = new TextBox();
	TextBox gmailTb = new TextBox();
	TextBox email = new TextBox();

//LABELS==========================================================
	Label welcome = new Label("Please fill in the required fields");
	Label gmail = new Label("Google-Mail");
	Label fname = new Label("Lastname");
	Label lname = new Label("Firstname");
	Label emaillb = new Label("Email");

//BUTTONS=========================================================
	Button save = new Button("Save");
	
//PANELS==========================================================


	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel hp2 = new HorizontalPanel();

	VerticalPanel hauptPanel = new VerticalPanel();
	VerticalPanel hauptPanel2 = new VerticalPanel();

	/**
	 * Konstruktor fuer die RegistryForm-Klasse.
	 * 
	 * @param u user der eigenloggt ist
	 */
	public RegistryForm(User u) {

		
		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		RootPanel.get("footer").clear();
		
		RootPanel.get("contentHeader").add(new HTML("Welcome to Scart"));
		RootPanel.get("content").add(welcome);
		
		welcome.setStylePrimaryName("labelReg");
		
		this.add(save);

		onLoad(u);

	}
	
	protected void onLoad(User u) {
		//Hier passiert etwas... wird spaeter implementiert <3
	}

}
