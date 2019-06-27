package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.client.gui.ProfileForm.FindUserByGMailCallback;
import de.hdm.itprojektss19.team03.scart.client.gui.ProfileForm.NoButtonClickHandler;
import de.hdm.itprojektss19.team03.scart.client.gui.ProfileForm.YesSaveButtonClickHandler;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
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
	EditorServiceAsync editorService = ClientsideSettings.getEditor();
	LoginServiceAsync loginService = ClientsideSettings.getLoginService();

	Group group = new Group();
	User user = new User();

	Vector<Group> GrouptoUpdate = new Vector<Group>();
//	Vector<String> allGroups = new Vector<String>();
//	Vector<Integer> allGroupIds = new Vector<Integer>();
//	Vector<String> choosenGroups = new Vector<String>();
	Vector<User> allUsers = new Vector<User>();

	// PANELS
	VerticalPanel groupFormPanel = new VerticalPanel();
	VerticalPanel userPanel = new VerticalPanel();
	HorizontalPanel btnPanel = new HorizontalPanel();
	HorizontalPanel groupNameHPanel = new HorizontalPanel();

	// Labels
	Label groupLabel = new Label("Gruppenname:");

	// TextBox
	TextBox groupTextBox = new TextBox();

	// FlexTable
	FlexTable userTable = new FlexTable();

	// Buttons
	Button deleteGroupButton = new Button("Aus Gruppe austreten");
	Button safeGroupButton = new Button("Alle Änderungen speichern");
	Button backToGroupButton = new Button("Zurück");
	Button deleteUserButton = new Button("Entfernen");

	public EditGroup() {

	}

	public EditGroup(User u, Group g) {
		this.user = u;
		this.group = g;
		// group.setId(1);
	}

	public void onLoad() {
		super.onLoad();

		groupTextBox.setText(group.getGroupName());
		groupTextBox.addStyleName("textbox");
		groupNameHPanel.setHorizontalAlignment(ALIGN_RIGHT);
		groupLabel.setHorizontalAlignment(ALIGN_LEFT);
		groupLabel.addStyleName("h2");
		deleteGroupButton.addClickHandler(new DeleteClickHandler(user, group));
		deleteGroupButton.addStyleName("button");
		safeGroupButton.addClickHandler(new SafeClickHandler());
		safeGroupButton.addStyleName("button");
		backToGroupButton.addClickHandler(new BackToClickHandler());
		backToGroupButton.addStyleName("button");
		deleteUserButton.addClickHandler(new DeleteUserClickHandler());
		// groupTextBox.setStyleName("textbox");

		groupNameHPanel.add(groupLabel);
		groupNameHPanel.add(groupTextBox);

		groupFormPanel.add(groupNameHPanel);
		groupFormPanel.add(userPanel);
		groupFormPanel.add(btnPanel);
		userPanel.add(userTable);

		btnPanel.add(deleteGroupButton);
		btnPanel.add(safeGroupButton);
		btnPanel.add(backToGroupButton);

		this.add(groupFormPanel);

		loadTable();

		// editorVerwaltung.findAllGroupsByUserId(user.getId(), new
		// AllGroupsCallback());
		// editorVerwaltung.findAllGroupsByUserId(1, new AllGroupsCallback());

	}

	public void loadTable() {
		
		userTable.removeAllRows();

		userTable.setText(0, 1, "Benutzername");
		userTable.setText(0, 2, "Email");
		userTable.setText(0, 3, "");
		
		for (int userNumber = 0; userNumber < allUsers.size(); userNumber++) {
			editorVerwaltung.getAllUserByGroupId(group.getId(),new AllUserCallback());
			
		}
		
		
	}

	public void setGroupNameLabel() {
		groupTextBox.setText(group.getGroupName());

	}

	/**
	 * Methode um einen User u aus einer Gruppe g zu entfernen. Gruppe und User
	 * bestehen auch nach dem Loeschen in der DB, nur die Verknuepfung in der
	 * GroupUser-Tabelle wurde aufgeloest
	 * 
	 * @param user  (User der aus Gruppe geloescht werden soll)
	 * @param group (Gruppe aus der der User geloescht werden soll)
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



	class DeleteClickHandler implements ClickHandler {
		Group group = new Group();
		User user = new User();

		public DeleteClickHandler(User u, Group g) {
			this.group = g;
			// group.setId(1);
			this.user = u;
			// user.setId(1);
		}

		@Override
		public void onClick(ClickEvent arg0) {
			removeUserFromGroup(user, group);

		}

	}

	class DeleteUserClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {

		}
	}

	class SafeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesSaveButtonClickHandler(db));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
			Label l = new HTML("<h1> Änderungen speichern</h1> <p> Sollen alle Änderungen gespeichert werden? </p> <br>");

			vp.add(l);
			hp.add(yB);
			hp.add(nB);
			vp.add(hp);

			db.setGlassEnabled(true);
			db.setAnimationEnabled(true);
			db.center();
			db.show();

			db.add(vp);

		}

	}
	
	class NoButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public NoButtonClickHandler(DialogBox db) {

			this.dbox = db;

		}

		public void onClick(ClickEvent event) {

			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

		}

	}
	
	class YesSaveButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public YesSaveButtonClickHandler(DialogBox db) {

			this.dbox = db;

		}

		public void onClick(ClickEvent event) {
			
			String newGroupName = groupLabel.getText();
			group.setGroupName(newGroupName);
			
			// Evt mit eigenem UpdateGroupName Mapper ????
			
			//editorVerwaltung.saveGroup(group, new UpdateGroupNameCallback);

			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

		}

	}

	class BackToClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			Window.Location.replace("/Scart.html");

		}

	}

	// CALLBACKS

	class AllUserCallback implements AsyncCallback<Vector<User>> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Vector<User> result) {
			allUsers = result;
			onLoad();

			}
			
		}
	
	class UpdateGroupNameCallback implements AsyncCallback<Group> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Group result) {
			onLoad();

			}
			
		}
	
	
	}
	
//	public void seeUsersfromGroup(User user, Group group) {
//	try {
//		if (user == null || group == null) {
//			throw new NullPointerException();
//		}
//
//		editorVerwaltung.getAllUserByGroupId(group.getId(), new AsyncCallback<Vector<User>>() {
//
//			public void onFailure(Throwable a) {
//				Window.alert("Die User dieser Gruppe konnten nicht angezeigt werden." + a);
//			}
//
//			@Override
//			public void onSuccess(Vector<User> result) {
//				Window.alert("Das sind die User der ausgewählten Gruppe.");
//				for (int g = 0; g < result.size(); g++) {
//
//					allUsers.add(result.elementAt(g).getUsername());
//
//					RadioButton userNames = new RadioButton("userNames", allUsers.elementAt(g));
//					// userNames.addClickHandler(new UserCheckBoxClickHandler(userNames));
//					userNames.setStyleName("textbox");
//					userPanel.add(userNames);
//
//				}
//			}
//		});
//	} catch (NullPointerException e) {
//		Window.alert(e.toString() + "\n" + "User/Group ist null");
//	}
//}
	

//	class AllGroupsCallback implements AsyncCallback<Vector<Group>> {
//
//		public void onFailure(Throwable e) {
//			Window.alert("Error getting Groups: " + e);
//		}
//
//		public void onSuccess(Vector<Group> result) {
//			// Window.alert(result.get(0).getGroupName());
//			for (int g = 0; g < result.size(); g++) {
//
//				allGroups.add(result.elementAt(g).getGroupName());
//				RadioButton groupNames = new RadioButton("groupNames", allGroups.elementAt(g));
//				groupNames.addClickHandler(new GroupCheckBoxClickHandler(groupNames));
//				groupNames.setStyleName("textbox");
//				checkBoxesGroup.add(groupNames);
//
//			}
//
//		}
//	}

//	class GroupCheckBoxClickHandler implements ClickHandler {
//		CheckBox checkBox = null;
//
//		public GroupCheckBoxClickHandler(CheckBox cB) {
//			this.checkBox = cB;
//		}
//
//		@Override
//		public void onClick(ClickEvent event) {
//			choosenGroups.contains(checkBox.getText());
//
//		}
//
//	}


