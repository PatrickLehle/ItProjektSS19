package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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

	final HorizontalPanel innerContent;
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

	public GroupForm(User u, HorizontalPanel _innerContent) {
		this.user = u;
		innerContent = _innerContent;

		groupInfoButton.addClickHandler(infoClickHandler);
		groupInfoButton.addStyleName("button");
		createGroupButton.addClickHandler(createClickHandler);
	}

	public void onLoad() {
		super.onLoad();

		groupNamePanel.setHorizontalAlignment(ALIGN_CENTER);
		// groupNamePanel.addStyleName("");
		groupLabel.setHorizontalAlignment(ALIGN_LEFT);
		groupLabel.addStyleName("h1");
		groupInfoButton.addClickHandler(infoClickHandler);
		groupInfoButton.addStyleName("button");
		createGroupButton.addClickHandler(createClickHandler);
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

				groupNameLabel.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent e) {
						innerContent.clear();
						Window.alert(e.toDebugString());
						innerContent.add(new GroceryListForm(user));
					}
				});

				groupNameLabel.setHorizontalAlignment(ALIGN_LEFT);
				groupNameLabel.setStyleName("text");
				groupNamePanel.add(groupNameLabel);

			}

		}
	}

	ClickHandler infoClickHandler = new ClickHandler() {

		public void onClick(ClickEvent arg0) {
			EditGroup editGroup = new EditGroup(user);
			innerContent.clear();
			innerContent.add(editGroup);

		}

	};

	ClickHandler createClickHandler = new ClickHandler() {

		public void onClick(ClickEvent arg0) {
			CreateGroup createGroup = new CreateGroup(user);
			innerContent.clear();
			innerContent.add(createGroup);

		}

	};

}
