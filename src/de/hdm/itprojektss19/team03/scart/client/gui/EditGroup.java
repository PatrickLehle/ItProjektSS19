package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
 * @author Julian Hofer, bastiantilk
 *
 */

public class EditGroup extends VerticalPanel{

//CONNECTION WITH EDITORSERVICE/MAPPER============================
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditor();

	
	LoginServiceAsync loginService = ClientsideSettings.getLoginService();
	

	Group group = new Group();
	User user = new User();
	Vector<Group> allGroups = new Vector<Group>();
	Vector<String> allGroupsS = new Vector<String>();

	// PANELS
	VerticalPanel groupFormPanel = new VerticalPanel();
	VerticalPanel checkBoxesGroup = new VerticalPanel();
	VerticalPanel groupBtnPanel = new VerticalPanel();

	// Labels
	Label groupLabel = new Label("zu verwaltende Gruppen");

	// Buttons
	Button deleteGroupButton = new Button("Löschen");
	Button manageGroupButton = new Button("Bearbeiten");
	Button backToGroupButton = new Button("Zurück");

	

	public EditGroup() {

	}

	public EditGroup(User u) {
		this.user = u;

	}

	public void onLoad() {
		super.onLoad();

		checkBoxesGroup.setHorizontalAlignment(ALIGN_CENTER);
		// groupNamePanel.addStyleName("");
		groupLabel.setHorizontalAlignment(ALIGN_LEFT);
		groupLabel.addStyleName("h1");
		deleteGroupButton.addClickHandler(new DeleteClickHandler());
		deleteGroupButton.addStyleName("button");
		manageGroupButton.addClickHandler(new ManageClickHandler());
		manageGroupButton.addStyleName("button");
		backToGroupButton.addClickHandler(new BackToClickHandler());
		backToGroupButton.addStyleName("button");

		groupFormPanel.add(groupLabel);
		groupFormPanel.add(checkBoxesGroup);
		groupFormPanel.add(groupBtnPanel);
		groupBtnPanel.add(deleteGroupButton);
		groupBtnPanel.add(manageGroupButton);
		groupBtnPanel.add(backToGroupButton);

		this.add(groupFormPanel);

		//editorVerwaltung.findAllGroupsByUserId(user.getId(), new AllGroupsCallback());
		editorVerwaltung.findAllGroupsByUserId(1, new AllGroupsCallback());

	}

	class AllGroupsCallback implements AsyncCallback<Vector<Group>> {

		public void onFailure(Throwable e) {
			Window.alert("Error getting Groups: " + e);
		}

		public void onSuccess(Vector<Group> result) {
			// Window.alert(result.get(0).getGroupName());
			for (int g = 0; g < result.size(); g++) {

				allGroupsS.add(result.elementAt(g).getGroupName());
				
				
				CheckBox groupNames = new CheckBox(allGroupsS.elementAt(g));
				groupNames.addClickHandler(new GroupCheckBoxClickHandler(groupNames));
				groupNames.setStyleName("textbox");
				checkBoxesGroup.add(groupNames);

			}

		}
	}

	class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			

		}

	}

	class ManageClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			

		}

	}
	
	class BackToClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			GroupForm groupForm = new GroupForm(user);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(groupForm);

		}

	}
	
	class GroupCheckBoxClickHandler implements ClickHandler {
		CheckBox checkBox = null;

		public GroupCheckBoxClickHandler(CheckBox cB) {
			this.checkBox = cB;
		}

		@Override
		public void onClick(ClickEvent arg0) {

		}

	}
	
	/** Methode um einen User u aus einer Gruppe g zu entfernen.
	 * 	Gruppe und User bestehen auch nach dem Loeschen in der DB, nur die
	 * 	Verknuepfung in der GroupUser-Tabelle wurde aufgeloest
	 * 
	 * @param user (User der aus Gruppe geloescht werden soll)
	 * @param group (Gruppe aus der der User geloescht werden soll)
	 */
	public void removeUserFromGroup(User user, Group group) {
		
		try {
			if(user == null || group == null) {
				throw new NullPointerException();
			}
		
		editorVerwaltung.removeUserFromGroup(user, group,  new AsyncCallback<Void>() {
			
			public void onFailure(Throwable arg0) { 
				Window.alert("User konnte nicht aus der Gruppe gelöscht werden");
			}

			@Override
			public void onSuccess(Void arg0) {
				Window.alert("User wurde aus der Gruppe gelöscht.");
			}
		});
		} catch(NullPointerException e) {
			Window.alert(e.toString()+"\n"+"User/Group ist null");
		}
	}
}
