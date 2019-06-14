package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

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
import de.hdm.itprojektss19.team03.scart.server.db.GroupMapper;

/**
 * 
 * @author Julian Hofer
 *
 *	ToDo CSS Style Panels, Labels, Buttons, GroupUser Mapper
 */

public class GroupForm extends VerticalPanel {
	
	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	
	Group group = null;
	
	// PANELS
	VerticalPanel groupFormPanel = new VerticalPanel();
	VerticalPanel groupNamePanel = null;
	
	// Labels
	Label groupLabel = new Label("Gruppenname");
	
	//Buttons
	Button groupInfoButton = new Button("Gruppen verwalten");
	Button createGroupButton = new Button("Gruppe hinzufügen");
	
	
	
	public GroupForm() {
		
		groupNamePanel.setHorizontalAlignment(ALIGN_CENTER);
		// GroupNamePanel.setStyleName(CSS); TO DO
		groupLabel.setHorizontalAlignment(ALIGN_CENTER);
		
		groupFormPanel.add(groupLabel);
		groupFormPanel.add(groupNamePanel);
		
		groupFormPanel.add(groupInfoButton);
		groupInfoButton.addClickHandler(new InfoClickHandler());
		groupInfoButton.setEnabled(true);
		
		groupFormPanel.add(createGroupButton);
		createGroupButton.addClickHandler(new CreateClickHandler());
		createGroupButton.setEnabled(true);
		
		// ToDo: durch Group User Mapper ersetzen
		Vector<Group> gV = new Vector<Group>();
		gV = GroupMapper.groupMapper().findAll();
		
		for (int i = 0; i < gV.size(); i++ ) {
			Label groupNameLabel = new Label(gV.elementAt(i).getGroupName());
			groupNameLabel.setHorizontalAlignment(ALIGN_CENTER);
			groupNamePanel.add(groupNameLabel);
		}
		
		
		
	}
	
	public void onLoad() {
		super.onLoad();
		
	
		
		
	}
	
	
	class InfoClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			// TODO Auto-generated method stub
			
			RootPanel.get("content").clear();
			// RootPanel.get("Navigator").add("EditGroup"); 
			
			
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
