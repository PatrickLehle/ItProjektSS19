package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroupUser;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die EditGroup-Form wird aufgerufen wenn etwas an der Gruppe geaendert werden
 * soll oder User der Gruppe hinzugefuegt werden sollen. Zudem erlaubt es einem
 * User selbst aus der jeweiligen Gruppe auszutreten
 * 
 * @author Julian Hofer, bastiantilk
 *
 */

public class EditGroupForm extends VerticalPanel {

	// CONNECTION WITH EDITORSERVICE/MAPPER============================
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditor();
	LoginServiceAsync loginService = ClientsideSettings.getLoginService();

	Group group = new Group();
	User user = new User();

	Vector<Group> GrouptoUpdate = new Vector<Group>();
	Vector<Group> allGroups = new Vector<Group>();
	// Vector<Integer> allGroupIds = new Vector<Integer>();
	// Vector<String> choosenGroups = new Vector<String>();
	Vector<User> allUsers = new Vector<User>();
	Vector<String> newUser = new Vector<String>();

	// PANELS
	VerticalPanel groupFormPanel = new VerticalPanel();
	ScrollPanel userPanel = new ScrollPanel();
	HorizontalPanel btnPanel = new HorizontalPanel();
	HorizontalPanel groupNameHPanel = new HorizontalPanel();

	// Labels
	Label groupLabel = new Label("Gruppenname:");

	// TextBox
	TextBox groupTextBox = new TextBox();

	// Buttons
	Button deleteGroupButton = new Button("Aus Gruppe austreten");
	Button saveGroupButton = new Button("Änderungen speichern");
	Button deleteUserButton = new Button("Entfernen");

	/**
	 * Konstruktor der EditGroup-Seite
	 * 
	 * @param  u (User-Objekt des Users der die EditUser-Seite aufrufen will)
	 * @param  g (Gruppen-Objekt der Gruppe in der etwas gaendert werden soll)
	 */
	public EditGroupForm(User u, Group g) {
		this.user = u;
		this.group = g;
		// group.setId(1);
	}

	/**
	 * Methode wird automatisch bei Seitenaufruf gestartet
	 */
	public void onLoad() {
		// FlexTable
		FlexTable userTable = new FlexTable();
		groupTextBox.setText(group.getGroupName());
		groupTextBox.addStyleName("textbox-big");
		// groupTextBox.addStyleName("h3");
		groupFormPanel.setHorizontalAlignment(ALIGN_CENTER);
		groupNameHPanel.setHorizontalAlignment(ALIGN_RIGHT);
		groupLabel.setHorizontalAlignment(ALIGN_LEFT);
		groupLabel.addStyleName("h2");
		deleteGroupButton.addClickHandler(new DeleteClickHandler(user, group));
		deleteGroupButton.addStyleName("button");
		saveGroupButton.addClickHandler(new YesSaveButtonClickHandler(group));
		saveGroupButton.addStyleName("button");
		// backToGroupButton.addClickHandler(new BackToClickHandler());
		// backToGroupButton.addStyleName("button");
		// deleteUserButton.addClickHandler(new DeleteUserClickHandler());

		groupNameHPanel.add(groupLabel);
		groupNameHPanel.add(groupTextBox);

		groupFormPanel.add(groupNameHPanel);
		groupFormPanel.add(userPanel);
		groupFormPanel.add(btnPanel);
		userPanel.setWidget(userTable);

		btnPanel.add(deleteGroupButton);
		btnPanel.add(saveGroupButton);
		// btnPanel.add(backToGroupButton);

		this.add(groupFormPanel);

		loadTable(userTable);

	}

	/**
	 * Methode um die Tabelle bei dem Aufrufen der GUI-Seite zu fuellen oder die
	 * Tabelle neu zu laden nachdem Aenderungen durchgefuehrt wurden
	 */
	public void loadTable(FlexTable userTable) {

		// // new User Textfield
		// TextBox userNameTextBox = new TextBox();
		// TextBox userEmailTextBox = new TextBox();
		//
		// // Add User Button
		// Button addButton = new Button("add");
		// addButton.addStyleName("table-button1");
		// addButton.addClickHandler(new AddUserClickHandler(userEmailTextBox,
		// userNameTextBox, group));

		userTable.removeAllRows();
		userTable.setStyleName("gwt-flextable");

		userTable.setText(0, 0, "Benutzername");
		userTable.setText(0, 1, "Email");
		userTable.setText(0, 2, "");
		userTable.setText(1, 0, user.getUsername());
		userTable.setText(1, 1, user.getEmail());
		// userTable.setWidget(2, 0, userNameTextBox);
		// userTable.setWidget(2, 1, userEmailTextBox);
		// userTable.setWidget(2, 2, addButton);

		editorVerwaltung.getAllUserByGroupId(group.getId(), new AllUserCallback(userTable));

	}

	/**
	 * Methode setzt den Group-Name in die groupTextBox
	 */
	public void setGroupNameLabel() {
		groupTextBox.setText(group.getGroupName());

	}

	/**
	 * Methode um einen User u aus einer Gruppe g zu entfernen. Gruppe und User
	 * bestehen auch nach dem Loeschen in der DB, nur die Verknuepfung in der
	 * GroupUser-Tabelle wurde aufgeloest
	 * 
	 * @param user (User der aus Gruppe geloescht werden soll)
	 * @param group (Gruppe aus der der User geloescht werden soll)
	 */
	public void removeUserFromGroup(User user, Group group) {

		editorVerwaltung.findAllGroupsByUserId(user.getId(), new AllGroupsByUserCallback(user, group));

	}

	/**
	 * Methode um einen neuen User der Gruppe hinzuzufuegen
	 * 
	 * @param userEmail (TextBox-Objekt der E-Mail des Users der eingeladen wird)
	 * @param userName (TextBox-Objekt der Username des Users der eingeladen wird)
	 * @param group (Gruppen-Objekt der Gruppe zu der der User eingeladen wird))
	 */
	public void addUser(TextBox userEmail, Group group) {
		TextBox userEmailTextBox = new TextBox();
		userEmailTextBox = userEmail;
		String uemail;
		User nuser = new User();
		uemail = userEmailTextBox.getText();
		nuser.setEmail(uemail);

		if (nuser.getEmail() == user.getEmail()) {
			Window.alert("Es handelt sich um deine eigene Email Adresse");
		}

		// GWT.log(nuser.getEmail() + " cannot find Gmail3");
		editorVerwaltung.getUserByGMail(nuser.getEmail(), new FindUserByGmailCallback());

	}

	/**
	 * ClickHandler um einen User aus einer Gruppe zu entfernen
	 * 
	 */
	class DeleteClickHandler implements ClickHandler {
		Group group = new Group();
		User user = new User();

		public DeleteClickHandler(User u, Group g) {
			this.group = g;
			// group.setId(1);
			this.user = u;
			// user.setId(1);
		}

		public void onClick(ClickEvent arg0) {
			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesLeaveButtonClickHandler(db));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
			Label l = new HTML("<p> Möchten Sie die Gruppe wirklich verlassen? </p> <br>");
			db.setText("Gruppe verlassen");
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

	/**
	 * ClickHandler um einen User aus einer Gruppe zu entfernen
	 * 
	 */
	class DeleteUserClickHandler implements ClickHandler {
		User user = new User();
		Group group = new Group();

		public DeleteUserClickHandler(User u, Group g) {
			this.user = u;
			this.group = g;
		}

		public void onClick(ClickEvent arg0) {
			// removeUserFromGroup(user, group);
			editorVerwaltung.findAllGroupsByUserId(user.getId(), new AllGroupsByUserCallback(user, group));
			GWT.log("clickhandler2");

		}
	}

	/**
	 * ClickHandler um Aenderungen zu speichern und die Gruppe in der DB updaten
	 * 
	 *
	 */
	class YesSaveButtonClickHandler implements ClickHandler {

		Group group = new Group();

		public YesSaveButtonClickHandler(Group g) {

			this.group = g;

		}

		public void onClick(ClickEvent event) {

			String newGroupName = groupTextBox.getText();

			group.setGroupName(newGroupName);

			editorVerwaltung.saveGroup(group, new UpdateGroupNameCallback());

		}
	}

	/**
	 * ClickHandler um einen User der Gruppe hinzuzufuegen
	 * 
	 *
	 */
	class AddUserClickHandler implements ClickHandler {

		Group groups = new Group();
		User users = new User();
		TextBox userEmailTextBox;

		public AddUserClickHandler(TextBox userEmail, Group group) {

			userEmailTextBox = userEmail;
			groups = group;

		}

		public void onClick(ClickEvent arg0) {
			GWT.log(userEmailTextBox.getText() + " passts?");

			addUser(userEmailTextBox, groups);

		}
	}

	// class BackToClickHandler implements ClickHandler {
	//
	//
	// public void onClick(ClickEvent arg0) {
	// Window.Location.replace("/Scart.html");
	//
	// }
	//
	// }

	/**
	 * Callback-Methode um alle User der Gruppe zu finden und die Tabelle/Buttons
	 * mit den Usern zu fuellen
	 *
	 */
	class AllUserCallback implements AsyncCallback<Vector<User>> {
		FlexTable userTable;

		public AllUserCallback(FlexTable uT) {
			userTable = uT;
		}

		public void onFailure(Throwable caught) {
			GWT.log(caught.getLocalizedMessage());
		}

		public void onSuccess(Vector<User> result) {
			allUsers = result;
			// User user = new User();

			for (int userNumber = 0; userNumber < allUsers.size(); userNumber++) {

				// UserDelete Button
				Button deleteButton = new Button("x");
				deleteButton.addStyleName("table-button1");
				deleteButton.addClickHandler(new DeleteUserClickHandler(allUsers.get(userNumber), group));

				if (allUsers.get(userNumber).getId() != user.getId()) {
					userTable.setText(userNumber + 3, 0, allUsers.get(userNumber).getUsername());
					userTable.setText(userNumber + 3, 1, allUsers.get(userNumber).getEmail());
					userTable.setWidget(userNumber + 3, 2, deleteButton);
				}

			}

			// // new User Textfield
			TextBox userEmailTextBox = new TextBox();
			// // Add User Button
			Button addButton = new Button(
					"<image src='/images/plusButton.png' width='16px' height='16px' align='center'/>");
			addButton.setStyleName("icon-button");
			addButton.addClickHandler(new AddUserClickHandler(userEmailTextBox, group));
			int rowCount = userTable.getRowCount();
			userTable.setWidget(rowCount, 1, userEmailTextBox);
			userTable.setWidget(rowCount, 2, addButton);
		}

	}

	/**
	 * Callback-Methode um einen User anhand der E-Mail-Adresse in der Db zu finden
	 * 
	 */
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

	/**
	 * Callback-Methode um den Namen der Gruppe bei Aenderung zu aktualisieren
	 * 
	 */
	class UpdateGroupNameCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Void arg0) {
			GroupForm groupForm = new GroupForm(user);

			RootPanel.get("navigation").clear();
			groupForm.addStyleName("navigation");
			groupForm.setHeight("100%");
			RootPanel.get("navigation").add(groupForm);
			onLoad();

		}

	}

	/**
	 * Callback-Methode um einen User einer Gruppe hinzuzufuegen. Der User wird
	 * hierbei mit dem GroupUser-BO mit der Gruppe verknuepft
	 */
	class AddUserCallback implements AsyncCallback<GroupUser> {

		public void onFailure(Throwable arg0) {
			Window.alert("User konnte nicht zur Gruppe hinzugefügt werden oder ist bereits in der Gruppe!");
		}

		public void onSuccess(GroupUser arg0) {
			onLoad();
			// Window.alert("Der User wurde erfolgreich zu der Gruppe " +
			// group.getGroupName() + " hinzugefügt!");

		}
	}

	/**
	 * Callback-Methode um einen User aus der Gruppe zu entfernen
	 * 
	 */
	class RemoveUserFromGroupCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable arg0) {
			Window.alert("User konnte nicht aus der Gruppe gelöscht werden");
		}

		public void onSuccess(Void arg0) {
			Window.Location.reload();
		}
	}

	class AllGroupsByUserCallback implements AsyncCallback<Vector<Group>> {
		User user = new User();
		Group group = new Group();

		public AllGroupsByUserCallback(User u, Group g) {
			this.user = u;
			this.group = g;

		}

		public void onFailure(Throwable t) {
			Window.alert("Gruppen des User konnten nicht gefunden werden");

		}

		public void onSuccess(Vector<Group> groups) {
			allGroups = groups;

			try {

				// if (allGroups.size() > 1) {
				editorVerwaltung.removeUserFromGroup(user, group, new RemoveUserFromGroupCallback());

				// }
				// else {
				// String lastGroup = allGroups.get(1).getGroupName();
				// Window.alert("Die Gruppe " + lastGroup
				// + " ist deine/seine letzte Gruppe und kann nicht gelöscht werden.");
				// }
			} catch (NullPointerException e) {
				Window.alert(e.toString() + "\n" + "User konnte nicht gelöscht werden");
			}

		}
	}

	/**
	 * ClickHandler um aus der Gruppe auszutreten
	 */
	class YesLeaveButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public YesLeaveButtonClickHandler(DialogBox db) {
			this.dbox = db;
		}

		public void onClick(ClickEvent event) {
			editorVerwaltung.findAllGroupsByUserId(user.getId(), new AllGroupsByUserCallback(user, group));
			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

		}

	}

	/**
	 * ClickHandler, nicht aus der Gruppe auszutreten
	 */
	class NoButtonClickHandler implements ClickHandler {

		DialogBox dbox;

		public NoButtonClickHandler(DialogBox db) {
			this.dbox = db;
		}

		public NoButtonClickHandler() {
		}

		public void onClick(ClickEvent event) {

			if (dbox != null) {
				dbox.hide();
				dbox.clear();
				dbox.removeFromParent();
				dbox.setAnimationEnabled(false);
				dbox.setGlassEnabled(false);
			}
			onLoad();

		}

	}
}
