package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * @author Julian Hofer, bastiantilk
 *
 */

public class EditGroup extends VerticalPanel {

	// CONNECTION WITH EDITORSERVICE/MAPPER============================
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditor();

	LoginServiceAsync loginService = ClientsideSettings.getLoginService();

	Group group = new Group();
	User user = new User();
	// Vector<Group> allGroups = new Vector<Group>();
	Vector<String> allGroups = new Vector<String>();
	Vector<Integer> allGroupIds = new Vector<Integer>();
	Vector<String> choosenGroups = new Vector<String>();
	Vector<String> allUsers = new Vector<String>();

	// PANELS
	HorizontalPanel groupUserPanel = new HorizontalPanel();
	VerticalPanel groupFormPanel = new VerticalPanel();
	VerticalPanel checkBoxesGroup = new VerticalPanel();
	VerticalPanel userPanel = new VerticalPanel();
	VerticalPanel groupBtnPanel = new VerticalPanel();

	// Labels
	Label groupLabel = new Label("Gruppenverwaltung");

	// Buttons
	Button deleteGroupButton = new Button("Löschen");
	Button manageGroupButton = new Button("Bearbeiten");
	Button backToGroupButton = new Button("Zurück");
	
	
	Vector<CheckBox> checkBoxVector = new Vector<CheckBox>();
// DEFAULT CONSTRUCTOR=============================================
	/**
	* Default Konstruktor der EditGroup-Seite
	* 
	*/
	public EditGroup() {

	}
	
// CONSTRUCTOR=====================================================
	/**
 	* Konstruktoren der EditGroup-Seite
	* 
	* @param u (User-Objekt des Users der die EditUser-Seite aufrufen will)
	*   
	*/
	public EditGroup(User u, Group g) {
		this.user = u;
		this.group = g;
	}
	
	public EditGroup(User u) {
		/*
		u.setId(Integer.valueOf(Cookies.getCookie("userId")));
		u.setEmail(String.valueOf(Cookies.getCookie("email")));
		*/
		this.user = u;
		
		onLoad();
	}

	/**
	 * Methode wird bei dem Aufrufen der Klasse/des Widgets gestartet
	 */
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

		groupUserPanel.add(groupFormPanel);
		groupUserPanel.add(userPanel);
		groupFormPanel.add(groupLabel);
		groupFormPanel.add(checkBoxesGroup);
		groupFormPanel.add(groupBtnPanel);

		groupBtnPanel.add(deleteGroupButton);
		groupBtnPanel.add(manageGroupButton);
		groupBtnPanel.add(backToGroupButton);

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

				allGroups.add(result.elementAt(g).getGroupName());
				
				//radioButtonVector.add(new RadioButton("Group "+g, allGroups.elementAt(g)));
				checkBoxVector.add(new CheckBox(allGroups.elementAt(g)));

				//RadioButton groupNames = new RadioButton("groupNames", allGroups.elementAt(g));
				checkBoxVector.elementAt(g).addClickHandler(new UserCheckBoxClickHandler(checkBoxVector.elementAt(g)));
				//groupNames.setStyleName("textbox");
				checkBoxesGroup.add(checkBoxVector.elementAt(g));

			}

		}
	}

	class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			editorVerwaltung.removeUserFromGroup(user, group, new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable arg0) {
					// TODO Auto-generated method stub
					//Window.alert("Fehler: Gruppe konnte nicht verlassen werden");
					GWT.log("Fehler: Gruppe konnte nicht verlassen werden!");
				}
				@Override
				public void onSuccess(Void arg0) {
					int trueIndex = 0;
					int trueAnzahl = 0;
					for(int i=0; i < allGroups.size(); i++) {
						if(checkBoxVector.elementAt(i).getValue()==true) {
							trueAnzahl++;
							trueIndex = i;
						}
					}
					if(trueAnzahl > 1) {
						Window.alert("Fehler: Nur eine Gruppe auswählen");
					} else if(trueAnzahl == 1) {
						
					allGroups.clear();
					
					editorVerwaltung.findAllGroupsByUserId(user.getId(), new AllGroupsCallback());  //Aktualisierung Gruppen
					
					}
					
				
				}
				
			});
		}

	}

	class ManageClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			seeUsersfromGroup(group);

		}

	}
	/** Zurueck Button; laed die Seite "Scart.html" neu
	 *
	 */
	class BackToClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			Window.Location.replace("/Scart.html");

		}

	}

	class UserCheckBoxClickHandler implements ClickHandler {
		CheckBox checkBox = null;

		public UserCheckBoxClickHandler(CheckBox cB) {
			this.checkBox = cB;
		}

		@Override
		public void onClick(ClickEvent event) {
			choosenGroups.contains(checkBox.getText());

		}

	}

	/**
	 * Methode um einen User u aus einer Gruppe g zu entfernen. Gruppe und User
	 * bestehen auch nach dem Loeschen in der DB, nur die Verknuepfung in der
	 * GroupUser-Tabelle wurde aufgeloest
	 * 
	 * @param user
	 *            (User der aus Gruppe geloescht werden soll)
	 * @param group
	 *            (Gruppe aus der der User geloescht werden soll)
	 */
	public void removeUserFromGroup(User user, Group group) {

		try {
			if (user == null || group == null) {
				throw new NullPointerException();
			}

			editorVerwaltung.removeUserFromGroup(user, group, new AsyncCallback<Void>() {

				public void onFailure(Throwable arg0) {
					Window.alert("User konnte nicht aus der Gruppe gelöscht werden");
				}

				@Override
				public void onSuccess(Void arg0) {
					Window.alert("User wurde aus der Gruppe gelöscht.");
				}
			});
		} catch (NullPointerException e) {
			Window.alert(e.toString() + "\n" + "User/Group ist null");
		}
	}

	public void seeUsersfromGroup(Group group) {
		try {
			if (group == null) {
				throw new NullPointerException();
			}

			editorVerwaltung.getAllUserByGroupId(group.getId(), new AsyncCallback<Vector<User>>() {

				public void onFailure(Throwable a) {
					Window.alert("Die User dieser Gruppe konnten nicht angezeigt werden." + a);
				}

				@Override
				public void onSuccess(Vector<User> result) {
					Window.alert("Das sind die User der ausgewählten Gruppe.");
					for (int g = 0; g < result.size(); g++) {

						allUsers.add(result.elementAt(g).getUsername());

						RadioButton userNames = new RadioButton("userNames", allUsers.elementAt(g));
						// userNames.addClickHandler(new UserCheckBoxClickHandler(userNames));
						userNames.setStyleName("textbox");
						userPanel.add(userNames);

					}
				}
			});
		} catch (NullPointerException e) {
			Window.alert(e.toString() + "\n" + "User/Group ist null");
		}
	}

}
