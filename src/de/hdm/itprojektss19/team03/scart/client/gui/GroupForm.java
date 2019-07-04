package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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
 * Die GroupForm-Seite stellt die Navigation der Gruppen und Einkaufslisten dar,
 * die in einer Tree-Navigation geordnet werden
 * 
 * @author Julian Hofer, Marco Dell'Oso, bastiantilk
 *
 *         ToDo CSS Style Panels, Labels, Buttons, GroupUser Mapper
 */
public class GroupForm extends VerticalPanel {

	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditor();
	LoginServiceAsync loginService = ClientsideSettings.getLoginService();

	// VARIABLES
	User user = new User();
	Vector<GroceryList> allGrocery = new Vector<GroceryList>();
	Vector<Group> allGroups = new Vector<Group>();

	// PANELS
	private Tree groupTree = new Tree();
	VerticalPanel groupsPanel = new VerticalPanel();
	VerticalPanel groupBtnPanel = new VerticalPanel();
	HorizontalPanel loadingPanel = new HorizontalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	VerticalPanel navigation = new VerticalPanel();
	HorizontalPanel outer = new HorizontalPanel();

	// Labels
	Label groupLabel = new Label("Gruppen");

	/**
	 * Konstructor der GroupForm-Seite
	 * 
	 * @param u (User-Objekt des aktuellen Users)
	 */
	public GroupForm(User u) {

		this.user = u;

		groupTree.addSelectionHandler(selectionHandler);
	}

	/**
	 * Methode wird bei dem Aufrufen der Klasse/des Widgets gestartet
	 * 
	 */
	public void onLoad() {
		// super.onLoad();
		loadingPanel.add(new LoadingForm());
		outer.addStyleName("inner-content");

		groupsPanel.setHorizontalAlignment(ALIGN_LEFT);
		groupLabel.addStyleName("h1");
		groupLabel.setHorizontalAlignment(ALIGN_CENTER);

		navigation.setHorizontalAlignment(ALIGN_LEFT);
		navigation.setVerticalAlignment(ALIGN_TOP);
		scrollPanel.add(groupLabel);
		navigation.add(scrollPanel);
		navigation.add(loadingPanel);

		this.add(navigation);

		// editorVerwaltung.findAllGroceryListByUserId(user.getId(), allGroupsCallback);
		// //Old Method

		findAllGroceryListFromUser(user);
	}

	/**
	 * Methode um alle GroceryLists des User zu finden
	 * 
	 * @param u (User Objekt des aktuellen Users)
	 * @return Vector mit den Einkaufslisten des Users
	 */
	public Vector<GroceryList> findAllGroceryListFromUser(User u) {

		GWT.log(u.getId() + " " + u.getUsername() + " " + u.getEmail()); // @bastiantilk-Fehlercheck

		/**
		 * Sucht alle Gruppen des Users ueber die GroupUser Tabelle und fuegt diese dem
		 * allGroups-Vektor hinzu
		 */
		editorVerwaltung.findAllGroupsByUserId(u.getId(), new AsyncCallback<Vector<Group>>() {

			public void onFailure(Throwable t) {
				Window.alert("Fehler: Gruppen von dem User konnten nicht gefunden werden: " + t);
			}

			public void onSuccess(Vector<Group> g) {
				allGroups.clear();
				allGroups = g;

				GWT.log(allGroups.toString()); // @bastiantilk-Fehlercheck

				/**
				 * Sucht alle GroceryListen aus allen Gruppen des Users und fuegt diese dem
				 * allGrocery-Vector hinzu
				 */
				editorVerwaltung.getAllGroceryListsByGroupVector(allGroups, new AsyncCallback<Vector<GroceryList>>() {

					public void onFailure(Throwable t) {
						Window.alert("Fehler: Einkaufslisten von dem User konnten nicht gefunden werden: " + t);
					}

					public void onSuccess(Vector<GroceryList> gl) {
						allGrocery.clear();
						allGrocery = gl;

						fillTree();
					}
				});

			}
		});
		return allGrocery;
	}

	// Alt
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

	/**
	 * Fuellt das Tree-Widget mit den Gruppen(allGroups-Vektor) und
	 * Einkaufslisten(allGrocery-Vektor) bei Seitenaufruf/Aktualiserung
	 * 
	 */
	public void fillTree() {
		groupTree.setAnimationEnabled(true);
		Label newGroup = new Label("+ Gruppe hinzufügen");
		newGroup.addStyleName("tree-new-group");
		newGroup.addClickHandler(createClickHandler);

		for (int i = 0; i < allGroups.size(); i++) {
			Label groupLabel = new Label(allGroups.get(i).getGroupName());
			groupLabel.addStyleName("tree-group");
			groupLabel.addClickHandler(new GroupClickHandler(allGroups.get(i)));
			groupTree.addItem(groupLabel);

			for (int j = 0; j < allGrocery.size(); j++) {
				if (allGroups.get(i).equals(getGroupOfGroceryList(allGrocery.get(j)))) {
					Label groceryLabel = new Label(allGrocery.get(j).getGroceryListName());
					groceryLabel.addClickHandler(new GroceryListClickHandler(allGrocery.get(j), allGroups.get(i)));
					groceryLabel.addStyleName("tree-grocery");
					groupTree.getItem(i).addItem(groceryLabel);
					groupTree.getItem(i).setState(true);
				}
			}
			Label newGroceryList = new Label("+ Einkaufsliste hinzufügen");
			newGroceryList.addStyleName("tree-new-gl");
			newGroceryList.addClickHandler(new NewGroceryListClickHandler(allGroups.get(i)));
			groupTree.getItem(i).insertItem(0, newGroceryList);

		}

		groupTree.insertItem(0, newGroup);
		loadingPanel.clear();
		groupsPanel.add(groupTree);
		navigation.add(groupsPanel);
		RootPanel.get("content").getElement().getStyle().setProperty("margin",
				"0px 0px 0px " + (navigation.getOffsetWidth() + 30) + "px");
	}

	/**
	 * Methode um die Gruppe in der sich eine Einkaufsliste befindet herauszufinden
	 * 
	 * @param gl (GroceryList-Objekt)
	 * @return Group-Objekt (Gruppe in der die Einkaufsliste(Parameter) ist)
	 */
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

	/**
	 * ClickHandler wenn auf eine Gruppe geklickt wird um diese zu editieren. Ruft
	 * entsprechende Seite EditGroup auf
	 */
	class GroupClickHandler implements ClickHandler {
		final Group selection;

		public GroupClickHandler(Group g) {
			selection = g;
		}

		public void onClick(ClickEvent arg0) {
			outer.clear();
			outer.add(new EditGroupForm(user, selection));
			RootPanel.get("content").clear();
			RootPanel.get("content").add(outer);
		}
	};

	/**
	 * ClickHandler wenn auf eine Einkaufsliste geklickt wird. Es wird die
	 * entsprechende Seite ShoppingListForm geoeffnet
	 */
	class GroceryListClickHandler implements ClickHandler {
		final GroceryList selection;
		final Group group;

		public GroceryListClickHandler(GroceryList gl, Group g) {
			selection = gl;
			group = g;
		}

		public void onClick(ClickEvent arg0) {
			outer.clear();
			outer.add(new ShoppingListForm(user, selection, group));
			RootPanel.get("content").clear();
			RootPanel.get("content").add(outer);
		}

	}

	/**
	 * ClickHandler wenn auf "+ Einkaufsliste hinzufügen" geklickt wird
	 */
	class NewGroceryListClickHandler implements ClickHandler {
		final Group group;

		public NewGroceryListClickHandler(Group g) {
			group = g;
		}

		public void onClick(ClickEvent arg0) {
			GroceryList groceryList = new GroceryList();
			groceryList.setGroceryListName("Neue Einkaufsliste");
			groceryList.setOwnerId(user.getId());
			groceryList.setGroupId(group.getId());
			groceryList.setGroupName(group.getGroupName());
			editorVerwaltung.createGroceryList(groceryList, new AsyncCallback<GroceryList>() {

				public void onFailure(Throwable arg0) {

					Window.alert("Fehler: Neue Groceryliste konnte nicht erstellt werden");
				}

				public void onSuccess(GroceryList arg0) {
					onLoad();
				}

			});
			/*
			 * outer.clear(); outer.add(new ShoppingListForm(user, selection, group));
			 * RootPanel.get("content").clear(); RootPanel.get("content").add(outer);
			 */
		}

	}

	/**
	 * ClickHandler wenn auf "+ Gruppe hinzufügen" geklickt wird
	 * 
	 */
	ClickHandler createClickHandler = new ClickHandler() {

		public void onClick(ClickEvent arg0) {
			outer.clear();
			CreateGroup createGroup = new CreateGroup(user);
			outer.add(createGroup);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(outer);

		}

	};

}
