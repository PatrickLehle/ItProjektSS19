package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;

/**
 * 
 * @author Julian Hofer
 *
 */

public class GroupForm extends VerticalPanel {
	
	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	
	// PANELS
	VerticalPanel GroupFormPanel = new VerticalPanel();
	HorizontalPanel GroupNamePanel = null;
	
	// Labels
	Label GroupLabel = new Label("Gruppenname");
	Label GroupNameLabel = null;
	
	//Buttons
	Button GroupInfoButton = new Button("Gruppen verwalten");
	Button createGroupButton = new Button("Gruppe hinzufügen");
	
	/**
	 * default Konstruktor
	 */
	public GroupForm() {
		
	}
	
	public GroupForm(User user) {
		
		GroupNamePanel.setHorizontalAlignment(ALIGN_CENTER);
		// GroupNamePanel.setStyleName(CSS); TO DO
		GroupNameLabel.setHorizontalAlignment(ALIGN_CENTER);
		GroupLabel.setHorizontalAlignment(ALIGN_CENTER);
		
		GroupFormPanel.add(GroupLabel);
		GroupNamePanel.add(GroupNameLabel);
		GroupFormPanel.add(GroupNamePanel);
		
		RootPanel.get("content").clear();
		RootPanel.get("content").add(GroupFormPanel);
		
		
		
		
	}
	
	

}
