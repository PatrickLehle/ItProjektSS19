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
	Vector<Group> allGroups = new Vector<Group>();
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

	public GroupForm(User u) {
		this.user = u;
		Window.alert(this.user.getEmail() + "  " + this.user.getId());
	}

	public void onLoad() {
		super.onLoad();

		groupNamePanel.setHorizontalAlignment(ALIGN_CENTER);
		// groupNamePanel.addStyleName("");
		groupLabel.setHorizontalAlignment(ALIGN_LEFT);
		groupLabel.addStyleName("h1");
		groupInfoButton.addClickHandler(new InfoClickHandler());
		groupInfoButton.addStyleName("button");
		createGroupButton.addClickHandler(new CreateClickHandler());
		createGroupButton.addStyleName("button");

		groupFormPanel.add(groupLabel);
		groupFormPanel.add(groupNamePanel);
		groupFormPanel.add(groupBtnPanel);
		groupBtnPanel.add(groupInfoButton);
		groupBtnPanel.add(createGroupButton);

		this.add(groupFormPanel);

		editorVerwaltung.findAllGroupsByUserId(user.getId(), new AllGroupsCallback());
		// editorVerwaltung.findAllGroupsByUserId(1, new AllGroupsCallback());

	}

	class AllGroupsCallback implements AsyncCallback<Vector<Group>> {

		public void onFailure(Throwable e) {
			Window.alert("Error getting Groups: " + e);
		}

		public void onSuccess(Vector<Group> result) {
			// Window.alert(result.get(0).getGroupName());
			for (int g = 0; g < result.size(); g++) {

				allGroupsS.add(result.elementAt(g).getGroupName());
				Label groupNameLabel = new Label(allGroupsS.elementAt(g));

				groupNameLabel.setHorizontalAlignment(ALIGN_LEFT);
				groupNameLabel.setStyleName("text");
				groupNamePanel.add(groupNameLabel);

			}

		}
	}

	class InfoClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			EditGroup editGroup = new EditGroup();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(editGroup);

		}

	}

	class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			CreateGroup createGroup = new CreateGroup(user);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(createGroup);

		}

	}

}
