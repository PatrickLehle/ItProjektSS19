package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroupUser;
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

	Vector<Group> GrouptoUpdate = new Vector<Group>();
//	Vector<String> allGroups = new Vector<String>();
//	Vector<Integer> allGroupIds = new Vector<Integer>();
//	Vector<String> choosenGroups = new Vector<String>();
	Vector<User> allUsers = new Vector<User>();
	Vector<String> newUser = new Vector<String>();

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
	Button safeGroupButton = new Button("Änderungen speichern");
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

		groupTextBox.setText(group.getGroupName());
		groupTextBox.addStyleName("textbox-big");
//		groupTextBox.addStyleName("h3");
		groupFormPanel.setHorizontalAlignment(ALIGN_CENTER);
		groupNameHPanel.setHorizontalAlignment(ALIGN_RIGHT);
		groupLabel.setHorizontalAlignment(ALIGN_LEFT);
		groupLabel.addStyleName("h2");
		deleteGroupButton.addClickHandler(new DeleteClickHandler(user, group));
		deleteGroupButton.addStyleName("button");
		safeGroupButton.addClickHandler(new SafeClickHandler());
		safeGroupButton.addStyleName("button");
		backToGroupButton.addClickHandler(new BackToClickHandler());
		backToGroupButton.addStyleName("button");
		// deleteUserButton.addClickHandler(new DeleteUserClickHandler());

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

		// new User Textfield
		TextBox userNameTextBox = new TextBox();
		TextBox userEmailTextBox = new TextBox();

		// Add User Button
		Button addButton = new Button("add");
		addButton.addStyleName("table-button1");
		addButton.addClickHandler(new AddUserClickHandler(userEmailTextBox, userNameTextBox, group));

		userTable.removeAllRows();
		userTable.setStyleName("gwt-flextable");

		userTable.setText(0, 0, "Benutzername");
		userTable.setText(0, 1, "Email");
		userTable.setText(0, 2, "");
		userTable.setText(1, 0, user.getUsername());
		userTable.setText(1, 1, user.getEmail());
		userTable.setWidget(2, 0, userNameTextBox);
		userTable.setWidget(2, 1, userEmailTextBox);
		userTable.setWidget(2, 2, addButton);

		editorVerwaltung.getAllUserByGroupId(group.getId(), new AllUserCallback());

	}

	public void setGroupNameLabel() {
		groupTextBox.setText(group.getGroupName());

	}

//	public void getDeleteUserButton() {
//		DeleteUserClickHandler 
//	}

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
			GWT.log(user.getId() + " delete");
			editorVerwaltung.removeUserFromGroup(user, group, new RemoveUserFromGroupCallback());

		} catch (NullPointerException e) {
			Window.alert(e.toString() + "\n" + "User/Group ist null");
		}
	}
	
	
	public void addUser(TextBox userEmail, TextBox userName, Group group) {
		TextBox userEmailTextBox = new TextBox();
		userEmailTextBox = userEmail;
		String uemail;
		User nuser = new User();
		uemail = userEmailTextBox.getText();
		nuser.setEmail(uemail);
		
		if (nuser.getEmail() == user.getEmail()) {
			Window.alert("Es handelt sich um deine eigene Email Adresse");
		}
		
		//GWT.log(nuser.getEmail() + " cannot find Gmail3");
		editorVerwaltung.getUserByGMail(nuser.getEmail(), new FindUserByGmailCallback());

		
	}
	
	
	// ClickHandler

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
		User user = new User();
		Group group = new Group();

		public DeleteUserClickHandler(User u, Group g) {
			this.user = u;
			this.group = g;
		}

		public void onClick(ClickEvent arg0) {
			removeUserFromGroup(user, group);

		}
	}

	class SafeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesSaveButtonClickHandler(db, group));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
			Label l = new HTML(
					"<h1> Änderungen speichern</h1> <p> Sollen alle Änderungen gespeichert werden? </p> <br>");

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
		Group group = new Group();

		public YesSaveButtonClickHandler(DialogBox db, Group g) {

			this.dbox = db;
			this.group = g;

		}

		public void onClick(ClickEvent event) {

			String newGroupName = groupTextBox.getText();

			group.setGroupName(newGroupName);
			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

			editorVerwaltung.saveGroup(group, new UpdateGroupNameCallback());

		}

	}

	class AddUserClickHandler implements ClickHandler {

		Group groups = new Group();
//		GroupUser gu = new GroupUser();
		User users = new User();
		TextBox userEmailTextBox = new TextBox();
		TextBox userNameTextBox = new TextBox();

//		public AddUserClickHandler(User u, Group g) {
//			this.user = u;
//			this.group = g;
//			
//		}

//		public AddUserClickHandler(String userName, String userEmail, Group group) {
////			this.group = group;
//			
//			users.setUsername(userName);
//			users.setEmail(userEmail);	
//			GWT.log(userEmail +" cannot find Gmail1");
//			
//		}

		public AddUserClickHandler(TextBox userEmail, TextBox userName, Group group) {
			// TODO Auto-generated constructor stub

			userEmailTextBox = userEmail;
			userNameTextBox = userName;
			groups = group;

		}

		public void onClick(ClickEvent arg0) {
			GWT.log(userEmailTextBox.getText() + " passts?");
			
			addUser(userEmailTextBox, userNameTextBox, groups);
			
			//userEmailTextBox.addChangeHandler(new EmailChangeHandler(userEmailTextBox.getText()));

		}
	}
	

//	class EmailChangeHandler implements ChangeHandler {
//		String uemail;
//		User nuser = new User();
//
//		public EmailChangeHandler(String email) {
//			uemail = email;
//
//			nuser.setEmail(uemail);
//			if (nuser.getEmail() == user.getEmail()) {
//				Window.alert("Es handelt sich um deine eigene Email Adresse");
//			}
//
//			//GWT.log(nuser.getEmail() + " cannot find Gmail3");
//			editorVerwaltung.getUserByGMail(nuser.getEmail(), new FindUserByGmailCallback());
//
//		}
//
//		@Override
//		public void onChange(ChangeEvent arg0) {
////			String email = userEmailTextBox.getText();
////			nuser.setEmail(email);
//
//		}
//	}

	class BackToClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			Window.Location.replace("/Scart.html");

		}

	}

	// CALLBACKS

	class AllUserCallback implements AsyncCallback<Vector<User>> {

		public void onFailure(Throwable caught) {
			GWT.log(caught.getLocalizedMessage());
		}

		public void onSuccess(Vector<User> result) {
			allUsers = result;
			//User user = new User();

			for (int userNumber = 0; userNumber < allUsers.size(); userNumber++) {

//				// new User Textfield
//				TextBox userNameTextBox = new TextBox();
//				TextBox userEmailTextBox = new TextBox();

				// UserDelete Button
				Button deleteButton = new Button("x");
				deleteButton.addStyleName("table-button1");
				deleteButton.addClickHandler(new DeleteUserClickHandler(allUsers.get(userNumber), group));

//				// Add User Button
//				Button addButton = new Button("add");
//				addButton.addClickHandler(new AddUserClickHandler(userEmailTextBox, userNameTextBox, group)); 

				if (allUsers.get(userNumber).getId() != user.getId()) {
					userTable.setText(userNumber + 3, 0, allUsers.get(userNumber).getUsername());
					userTable.setText(userNumber + 3, 1, allUsers.get(userNumber).getEmail());
					userTable.setWidget(userNumber + 3, 2, deleteButton);

				}

			}

		}

	}

	class FindUserByGmailCallback implements AsyncCallback<User> {

		public void onFailure(Throwable caught) {
			
			Window.alert("Prüfe ob die Email Adresse richtig geschrieben ist und die Person bereits registriert ist");
			GWT.log(user.getEmail() + " cannot find Gemail");
		}

		public void onSuccess(User u) {
			User newUser = new User();
			newUser = u;

			editorVerwaltung.addUserToGroup(newUser, group, new AddUserCallback());

		}

	}

	class UpdateGroupNameCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Void arg0) {
			GroupForm groupForm = new GroupForm(user);
			groupForm.setStyleName("navigation");
			RootPanel.get("navigation").clear();
			RootPanel.get("navigation").add(groupForm);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(new EditGroup(user, group));

		}

	}

	class AddUserCallback implements AsyncCallback<GroupUser> {

		public void onFailure(Throwable arg0) {
			Window.alert("User konnte nicht zur Gruppe hinzugefügt werden oder ist bereits in der Gruppe!");
		}

		@Override
		public void onSuccess(GroupUser arg0) {
			GWT.log("funzt");
			onLoad();
			Window.alert("Der User wurde erfolgreich zu der Gruppe " + group.getGroupName() + " hinzugefügt!");
			// TODO Auto-generated method stub

		}
	}

	class RemoveUserFromGroupCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable arg0) {
			Window.alert("User konnte nicht aus der Gruppe gelöscht werden");
		}

		@Override
		public void onSuccess(Void arg0) {
			GroupForm groupForm = new GroupForm(user);
			groupForm.setStyleName("navigation");
			RootPanel.get("navigation").clear();
			RootPanel.get("navigation").add(groupForm);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(new EditGroup(user, group));
			Window.alert("User wurde aus der Gruppe gelöscht.");
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