package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.client.gui.EditGroup;

/**
 * 
 * @author Julian Hofer
 *
 *	ToDo CSS Style Panels, Labels, Buttons
 */

public class GroupForm extends VerticalPanel {
	
	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	
	Group group = null;
	
	// PANELS
	VerticalPanel GroupFormPanel = new VerticalPanel();
	HorizontalPanel GroupNamePanel = null;
	
	// Labels
	Label GroupLabel = new Label("Gruppenname");
	Label GroupNameLabel = null;
	
	//Buttons
	Button GroupInfoButton = new Button("Gruppen verwalten");
	Button CreateGroupButton = new Button("Gruppe hinzufügen");
	
	/**
	 * default Konstruktor
	 */
	public GroupForm() {
		
	}
	
	public GroupForm(Group g) {
		
		this.group = g;
		
		GroupNamePanel.setHorizontalAlignment(ALIGN_CENTER);
		// GroupNamePanel.setStyleName(CSS); TO DO
		GroupNameLabel.setHorizontalAlignment(ALIGN_CENTER);
		GroupLabel.setHorizontalAlignment(ALIGN_CENTER);
		
		GroupFormPanel.add(GroupLabel);
		GroupNamePanel.add(GroupNameLabel);
		GroupFormPanel.add(GroupNamePanel);
		
		GroupFormPanel.add(GroupInfoButton);
		GroupInfoButton.addClickHandler(new InfoClickHandler());
		GroupInfoButton.setEnabled(true);
		
		GroupFormPanel.add(CreateGroupButton);
		CreateGroupButton.addClickHandler(new CreateClickHandler());
		CreateGroupButton.setEnabled(true);
		
		
		
	}
	
	public void onLoad() {
		super.onLoad();
		
		RootPanel.get("contentHeader").clear();
		RootPanel.get("content").clear();
		RootPanel.get("footer").clear();
		RootPanel.get("content").add(GroupFormPanel);
		
		for (int i = 0; i < group.getId(); i++ ) {
			String g = group.getGroupName(null);
			GroupNameLabel.add(g);
			
		}
		
		
	}
	
	
	class InfoClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			// TODO Auto-generated method stub
			
			RootPanel.get("content").clear();
			//RootPanel.get("Navigator").add(EditGroup); 
			
			
		}
		
		
	}
	
	class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			// TODO Auto-generated method stub
			
			RootPanel.get("content").clear();
			RootPanel.get("createGroup");
		}
		
	}

}
