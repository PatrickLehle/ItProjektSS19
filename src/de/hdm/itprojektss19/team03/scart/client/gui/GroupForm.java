package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
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

	User user = new User();
	Vector<GroceryList> allGrocery = new Vector<GroceryList>();
	Vector<Group> allGroups = new Vector<Group>();

	// PANELS
	private Tree groupTree = new Tree();
	final HorizontalPanel innerContent;
	VerticalPanel groupsPanel = new VerticalPanel();
	VerticalPanel groupBtnPanel = new VerticalPanel();
	HorizontalPanel loadingPanel = new HorizontalPanel();
	ScrollPanel scrollPanel = new ScrollPanel(groupsPanel);
	VerticalPanel navigation = new VerticalPanel();

	// Images
	Image loading1 = new Image();
	Image loading2 = new Image();
	Image loading3 = new Image();

	// Labels
	Label groupLabel = new Label("Gruppen");

	// Buttons
	Button createGroupButton = new Button("Gruppe hinzufügen");

	public GroupForm(User u, HorizontalPanel _innerContent) {

		this.user = u;

		innerContent = _innerContent;
		createGroupButton.addClickHandler(createClickHandler);
		groupTree.addSelectionHandler(selectionHandler);
	}

	public void onLoad() {

		// super.onLoad();
		loading1.setUrl("/images/fruits-banana.gif");
		loading2.setUrl("/images/fruits-grape.gif");
		loading3.setUrl("/images/fruits-melon.gif");
		loadingPanel.add(loading1);
		loadingPanel.add(loading2);
		loadingPanel.add(loading3);
		loadingPanel.setHorizontalAlignment(ALIGN_CENTER);
		loadingPanel.setVerticalAlignment(ALIGN_MIDDLE);

		groupsPanel.setHorizontalAlignment(ALIGN_LEFT);
		groupLabel.addStyleName("h1");
		groupLabel.setHorizontalAlignment(ALIGN_CENTER);
		createGroupButton.addClickHandler(createClickHandler);
		createGroupButton.addStyleName("button");

		navigation.setHorizontalAlignment(ALIGN_LEFT);
		navigation.setVerticalAlignment(ALIGN_TOP);
		navigation.add(groupLabel);
		navigation.add(loadingPanel);
		this.add(navigation);
		// groupFormPanel.add(groupNamePanel);
		// groupFormPanel.add(groupBtnPanel);
		// groupBtnPanel.add(groupInfoButton);
		// groupBtnPanel.add(createGroupButton);

		editorVerwaltung.findAllGroceryListByUserId(user.getId(), allGroupsCallback);
	}

	AsyncCallback<Vector<GroceryList>> allGroupsCallback = new AsyncCallback<Vector<GroceryList>>() {

		public void onFailure(Throwable e) {
			Window.alert("Error getting Groups: " + e);
		}

		public void onSuccess(Vector<GroceryList> result) {

			for (int g = 0; g < result.size(); g++) {
				Group tempGroup = getGroupOfGroceryList(result.get(g));
				if (!allGroups.contains(tempGroup)) {
					allGroups.add(tempGroup);
				}
				allGrocery.add(result.get(g));
			}

			fillTree();
		}
	};

	public void fillTree() {
		groupTree.setAnimationEnabled(true);

		for (int i = 0; i < allGroups.size(); i++) {
			Label groupLabel = new Label(allGroups.get(i).getGroupName());
			groupLabel.addStyleName("tree-group");
			groupLabel.addClickHandler(new GroupClickHandler(allGroups.get(i)));
			groupTree.addItem(groupLabel);

			for (int j = 0; j < allGrocery.size(); j++) {
				if (allGroups.get(i).equals(getGroupOfGroceryList(allGrocery.get(j)))) {
					Label groceryLabel = new Label(allGrocery.get(j).getGroceryListName());
					groceryLabel.addStyleName("tree-grocery");
					groupTree.getItem(i).addItem(groceryLabel);
					groupTree.getItem(i).setState(true);
				}
			}
		}
		loadingPanel.clear();
		navigation.add(groupTree);
	}

	public Group getGroupOfGroceryList(GroceryList gl) {
		Group glGroup = new Group();
		glGroup.setId(gl.getGroupId());
		glGroup.setGroupName(gl.getGroupName());
		return glGroup;
	}

	SelectionHandler<TreeItem> selectionHandler = new SelectionHandler<TreeItem>() {

		public void onSelection(SelectionEvent<TreeItem> event) {

		}
	};

	class GroupClickHandler implements ClickHandler {
		final Group selection;

		public GroupClickHandler(Group g) {
			selection = g;
		}

		public void onClick(ClickEvent arg0) {
			innerContent.clear();
			innerContent.add(new EditGroup(user, selection));
		}
	};

	class GroceryListClickHandler implements ClickHandler {
		final GroceryList selection;

		public GroceryListClickHandler(GroceryList gl) {
			selection = gl;
		}

		public void onClick(ClickEvent arg0) {
			innerContent.clear();
			innerContent.add(new ShoppingListForm(user, selection));
		}

	}

	ClickHandler createClickHandler = new ClickHandler() {

		public void onClick(ClickEvent arg0) {
			CreateGroup createGroup = new CreateGroup(user);
			innerContent.clear();
			innerContent.add(createGroup);

		}

	};

}
