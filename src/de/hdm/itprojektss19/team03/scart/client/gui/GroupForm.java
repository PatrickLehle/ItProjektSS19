package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
 *         ToDo CSS Style Panels, Labels, Buttons, GroupUser Mapper
 */

public class GroupForm extends VerticalPanel {

	EditorServiceAsync editorService = ClientsideSettings.getEditor();

	Group group = null;

	// PANELS
	VerticalPanel groupFormPanel = new VerticalPanel();
	VerticalPanel groupNamePanel = new VerticalPanel();

	// Labels
	Label groupLabel = new Label("Gruppenname");

	// Buttons
	Button groupInfoButton = new Button("Gruppen verwalten");
	Button createGroupButton = new Button("Gruppe hinzufügen");

	public GroupForm() {

	}

	public GroupForm(User u) {

		groupNamePanel.setHorizontalAlignment(ALIGN_CENTER);
		groupLabel.setHorizontalAlignment(ALIGN_CENTER);
		groupInfoButton.addClickHandler(new InfoClickHandler());
		createGroupButton.addClickHandler(new CreateClickHandler());
		
		groupFormPanel.add(groupLabel);		
		groupFormPanel.add(groupNamePanel);
		groupFormPanel.add(groupInfoButton);
		groupFormPanel.add(createGroupButton);
		this.add(groupFormPanel);
		
		// ToDo: durch Group User Mapper ersetzen
		editorService.getAllGroupsByUser(u, new AsyncCallback<Vector<Group>>() {

			public void onFailure(Throwable e) {
				Window.alert("Error getting Groups: " + e);
			}

			public void onSuccess(Vector<Group> groups) {
				createGroupPanels(groups);
				
			}
		});

	
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
	
	public void createGroupPanels(Vector<Group> groups) {
		
		Vector<Group> gV = new Vector<Group>();
		
		for (int i = 0; i < gV.size(); i++) {
			Label groupNameLabel = new Label(groups.elementAt(i).getGroupName());
			groupNameLabel.setHorizontalAlignment(ALIGN_CENTER);
			groupNamePanel.add(groupNameLabel);
		}

	}

}
