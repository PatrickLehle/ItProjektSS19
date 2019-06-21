package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * @author Julian Hofer
 *
 *         ToDo CSS Style Panels, Labels, Buttons, GroupUser Mapper
 */

public class GroupForm extends VerticalPanel {

	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditor();
	LoginServiceAsync loginService = ClientsideSettings.getLoginService();

	Group group = new Group();
	User user = new User();
	Vector<Group> allGroups = null;
	Vector<String> allGroupsS = new Vector<String>();
	

	// PANELS
	VerticalPanel groupFormPanel = new VerticalPanel();
	VerticalPanel groupNamePanel = new VerticalPanel();
	VerticalPanel groupBtnPanel = new VerticalPanel();

	// Labels
	Label groupLabel = new Label("Gruppenname");

	// Buttons
	Button groupInfoButton = new Button("Gruppen verwalten");
	Button createGroupButton = new Button("Gruppe hinzufügen");
	
	EditGroup editGroup = new EditGroup();
	CreateGroup createGroup = new CreateGroup(user);
	

	public GroupForm() {

	}

	public GroupForm (User u) {
		this.user = u;
	
	}
	


	public void onLoad() {
		super.onLoad();
		
		groupNamePanel.setHorizontalAlignment(ALIGN_CENTER);
		//groupNamePanel.addStyleName("");
		groupLabel.setHorizontalAlignment(ALIGN_LEFT);
		groupLabel.addStyleName("h2");
		groupInfoButton.addClickHandler(new InfoClickHandler());
		createGroupButton.addClickHandler(new CreateClickHandler());

		groupFormPanel.add(groupLabel);
		groupFormPanel.add(groupNamePanel);
		groupFormPanel.add(groupBtnPanel);
		groupBtnPanel.add(groupInfoButton);
		groupBtnPanel.add(createGroupButton);
		
		this.add(groupFormPanel);

		// ToDo: durch Group User Mapper ersetzen
		editorService.getAllGroupsByUser(u, new AsyncCallback<Vector<Group>>() {

			public void onFailure(Throwable e) {
				Window.alert("Error getting Groups: " + e);
			}
		};
		// refresh.scheduleRepeating(10000);

			public void onSuccess(Vector<Group> groups) {
				createGroupPanels(groups);

			}
		});

	}

			if (allGroupsS.size() != result.size()) {
				allGroupsS.clear();

				for (int g = 0; g < result.size(); g++) {
					allGroupsS.add(result.elementAt(g).getGroupName());
					Label groupNameLabel = new Label(allGroupsS.elementAt(g));
					groupNameLabel.setHorizontalAlignment(ALIGN_LEFT);
					groupNameLabel.setStyleName("textbox");
					groupNamePanel.add(groupNameLabel);
				}
			}
		}
	}
	

	

	class InfoClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			RootPanel.get("content").clear();
			RootPanel.get("content").add(editGroup);
			


		}

	}

	class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			RootPanel.get("content").clear();
			RootPanel.get("content").add(createGroup);

			
		}

	}

	public void createGroupPanels(Vector<Group> groups) {

		Vector<Group> gV = new Vector<Group>();
		Window.alert(gV.get(0).getGroupName());
		for (int i = 0; i < gV.size(); i++) {
			Label groupNameLabel = new Label(groups.elementAt(i).getGroupName());
			groupNameLabel.setHorizontalAlignment(ALIGN_CENTER);
			groupNamePanel.add(groupNameLabel);
		}


}
