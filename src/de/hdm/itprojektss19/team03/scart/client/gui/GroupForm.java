package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
//"Familien"/Add Group Mockup in Balsamic

/**
 * Die Group-Form wird aufgerufen wenn auf der Homepage
 * eine Gruppe ausgewaehlt wird
 * 
 * @author bastiantilk
 *
 */
public class GroupForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	
	
	//TEXTBOXEN=======================================================
		TextBox groupTextbox = new TextBox(); 
	
	//LABELS==========================================================
	Label createGroup = new Label("Gruppe erstellen");
	Label name = new Label("Gruppenname: ");
	
	/**
	Label groups = new Label("Gruppe");
	Label SLName = new Label("Einkaufsliste: "); //Muss spaeter "Einkaufsliste: (Gruppenname)" anzeigen
	Label users = new Label("Benutzer");
	*/
	
	//BUTTONS=========================================================
	Button back = new Button("Zurück");
	Button removeGroup = new Button("Erstellen");
	
	/**
		Button back = new Button("Zurück");
		Button removeGroup = new Button("Gruppe entfernen");
		Button addSL = new Button("EL hinzufügen");
		Button removeSL = new Button("EL entfernen");
		Button mySL = new Button("Meine EL");
		Button userAdd = new Button("Hinzufügen");
		Button userRemove = new Button("entfernen");
	*/
	//PANELS==========================================================
	
	
	HorizontalPanel header = new HorizontalPanel();
	
	VerticalPanel hauptPanel = new VerticalPanel();
	
	HorizontalPanel createGroupPanel = new HorizontalPanel(); //Panel fuer Ueberschrift
	HorizontalPanel inputPanel = new HorizontalPanel(); //Label und Textbox
	HorizontalPanel buttonPanel = new HorizontalPanel(); //Buttons


	HorizontalPanel footer = new HorizontalPanel();

	public GroupForm(User user) {
		// TODO Auto-generated constructor stub
		
		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		RootPanel.get("footer").clear();
		
		RootPanel.get("contentHeader").add(new HTML("Scart"));
		RootPanel.get("content").add(createGroupPanel);
		RootPanel.get("content").add(inputPanel);
		RootPanel.get("content").add(buttonPanel);
		RootPanel.get("footer").add(footer);
		
		createGroupPanel.add(createGroup);
		
		inputPanel.add(name);
		inputPanel.add(groupTextbox);
		
		buttonPanel.add(back);
		buttonPanel.add(buttonPanel);
	}

	public GroupForm(Group selection) {
		// TODO Auto-generated constructor stub
	}

	public void setSelectedGroup(Group selectedGroup) {
		// TODO Auto-generated method stub
		
	}

}
