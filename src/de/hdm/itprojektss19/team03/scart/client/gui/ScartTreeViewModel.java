package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.BusinessObject;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

	/**
	 * Darstellung der Navigation durch Groups und deren GroceryLists.
	 * 
	 * @author PatrickLehle, TomHager, MarcoDell'Oso
	 */
	public class ScartTreeViewModel implements TreeViewModel {

		/**
		 * EditorService wird auf null gesetzt und stellt die Verbindung zum Async
		 * Interface dar.
		 */
		private EditorServiceAsync ev = null;

		/**
		 * Nutzer wird auf null gesetzt.
		 */
		private User u = null;

		/**
		 * ListDataProvider-Objekte verwalten das Datenmodell auf Client-Seite.
		 */
		private ListDataProvider<GroceryList> groceryListDataProvider = null;

		/**
		 * Diese Map speichert die ListDataProviders fuer die GroceryLists.
		 */
		private Map<GroceryList, ListDataProvider<Group>> groupDataProvider = null;

		/**
		 * Selektierung einer GroceryList / einer Grupee moeglich.
		 */
		private GroceryList selectedGroceryList = null;
		private Group selectedGroup = null;

		/**
		 * Formulare zur Anpassung von GroceryLists/ Groups im TreeViewModel.
		 */
		private GroceryListForm groceryListForm = null;
		private GroupForm groupForm = null;

		/**
		 * Bildet BusinessObjects auf eindeutige Stringobjekte ab, die als Schluessel fuer
		 * Baumknoten dienen. Dadurch werden im Selektionsmodell alle Objekte mit
		 * derselben string selektiert, wenn eines davon selektiert wird.
		 */
		private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {

			@Override
			public String getKey(BusinessObject bo) {
				if (bo == null) {
					return null;
				} else if (bo instanceof GroceryList) {
					return "GL-" + bo.getId();
				} else if (bo instanceof Group) {
					return "G-" + bo.getId();
				}
				return null;

			}
		}

		/**
		 * Die lokal definierte Klasse BusinessObjectKeyProvider wird nun als Variable
		 * der Klasse ScartTreeViewModel hinzugefuegt.
		 */
		private BusinessObjectKeyProvider boKeyProvider = null;

		/**
		 * Das <code>SingleSelectionObject</code> wird von GWT vordefiniert Diese
		 * beschreibt die Auswahl von Objekten im Baum.
		 */
		private SingleSelectionModel<BusinessObject> selectionModel = null;

		/**
		 * Nested Class fuer die Reaktion auf Selektionsereignisse. Als Folge einer
		 * Baumknotenauswahl wird je nach Typ des Business-Objekts die
		 * "selectedGroceryList" bzw. der "selectedGroup" gesetzt.
		 * 
		 * @author Rathke
		 */
		private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {

				// Abfrage des ausgewaehlten Objekts
				BusinessObject selection = selectionModel.getSelectedObject();

				if (selection instanceof GroceryList) {
						RootPanel.get("content").add(new GroceryListForm((GroceryList) selection));
				} else if (selection instanceof Group) {
					RootPanel.get("content").add(new GroupForm((Group) selection));

				}
			}
		}

		/**
		 * Konstruktur der Klasse ScartTreeViewModel.
		 * 
		 * @param u der aktuell eingeloggte Nutzer
		 */
		public ScartTreeViewModel(final User u) {
			this.u = u;
			this.ev = ClientsideSettings.getEditorVerwaltung();
			boKeyProvider = new BusinessObjectKeyProvider();
			selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
			selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
			groupDataProvider = new HashMap<GroceryList, ListDataProvider<Group>>();
		}

		/**
		 * Formulare fuer die Setter.
		 * 
		 * @param groceryListForm GroceryList die gesetzt wird
		 */
		public void setKontaktlisteForm(GroceryListForm groceryListForm) {
			this.groceryListForm = groceryListForm;
		}

		public void setKontaktForm(GroupForm groupForm) {
			this.groupForm = groupForm;
		}

		/**
		 * Methode zum Abfragen der aktuell selektierten GroceryList.
		 * 
		 * @return aktuell selektierte GroceryList
		 */
		public GroceryList getSelectedGroceryList() {
			return selectedGroceryList;
		}

		/**
		 * Setzt die selektierte GroceryList welches der User angeklickt hat. Die
		 * selektierte GroceryList wird der GroceryListForm/Groupform mitgeteilt.
		 * 
		 * @param selectedGroceryList die ausgewahelt wurde
		 */
		public void setSelectedGroceryList(GroceryList selectedGroceryList) {

			this.selectedGroceryList = selectedGroceryList;
			this.groceryListForm.setSelectedGroceryList(selectedGroceryList);
			this.groupForm.setSelectedGroup(selectedGroup);

			this.selectedGroup = null;
			this.groupForm.setSelectedGroup(null);

		}

		/**
		 * Methode zum Abfragen des aktuell selektierten Group.
		 * 
		 * @return aktuell selektierterte Group
		 */
		public Group getSelectedGroup() {
			return selectedGroup;
		}

		/**
		 * Diese Methode wird aufgerufen wenn der User eine Group selektiert.
		 * 
		 * @param selectedGroup der ausgewaehlt wurde
		 */
		public void setSelectedGroup(Group selectedGroup) {

			// Aenderungen am Group durch GroupForm moeglich.
		    this.selectedGroup = selectedGroup;
			this.groupForm.setSelectedGroup(selectedGroup);

			// GroceryList in der sich groups befinden wird aktualsiert.
			if (selectedGroup != null) {

				// Selektierte Group anhand der ID in der Datenbank finden.
				this.ev.getGroupById(this.selectedGroup.getGroupById(), new AsyncCallback<Group>() {

					
					@Override
					public void onFailure(Throwable caught) {
						ClientsideSettings.getLogger().severe("Hopala");
					}

					@Override
					public void onSuccess(Group result) {
						//there is a bug i need to resolve.. will be fixed soon <3
			//			selectedGroup = result;
						groupForm.setSelectedGroup(result);
					}
				});
			}
		}

		/**
		 * Fuegt eine neue GroceryList dem Baum hinzu.
		 * 
		 * @param GroceryList die hinzugefuegt wird
		 */
		public void addGroceryList(GroceryList groceryList) {
			this.groceryListDataProvider.getList().add(groceryList);

			// Selektiert die GroceryList
			this.selectionModel.setSelected(groceryList, true);
		}

		/**
		 * Aktualsierung des GroceryList-Objekts.
		 * 
		 * @param GroceryList die aktualisiert wird
		 */
		public void updateGroceryList(GroceryList groceryList) {
			List<GroceryList> groceryListList = this.groceryListDataProvider.getList();
			int i = 0;
			Window.alert("lol");
			for (GroceryList aktuell : groceryListList) {
				if (aktuell.getId() == groceryList.getId()) {
					groceryListList.set(i, groceryList);
					break;
				} else {
					i++;
				}
			}
			this.groceryListDataProvider.refresh();
		}

		/**
		 * Loeschung eines GroceryListen-Objekts.
		 * 
		 * @param groceryList die geloescht wird
		 */
		public void deleteGroceryList(GroceryList groceryList) {

			this.groupDataProvider.remove(groceryList);

			this.groceryListDataProvider.getList().remove(groceryList);
		}

		/**
		 * Hinzufuegen eines Group-Objekts in einer GroceryList.
		 * 
		 * @param group die hinzugefuegt wird
		 * @param groceryList in der die group hinzugefuegt wird
		 */
		public void addGroup(Group group, GroceryList groceryList) {

			// Falls es noch keinen Group Provider fuer diese groceryList gibt,
			// wurde der Baumknoten noch nicht geoeffnet und wir brauchen nichts tun.
			if (!this.groupDataProvider.containsKey(groceryList)) {
				return;
			}
			
			// Erstellt einen ListDataProvider mit Groups der ausgewaehlten GroceryList.
			ListDataProvider<Group> groupProvider = this.groupDataProvider.get(groceryList);
			if (!groupProvider.getList().contains(group)) {

				groupProvider.getList().add(group);
			}

			this.selectionModel.setSelected(group, true);

		}

		/**
		 * Aktualsierung eines Group-Objekts.
		 * 
		 * @param group die aktualisiert wird
		 */
		public void updateGroup(final Group group) {
			
			//Auslesen der Group per id
			this.ev.getGroupById(group.getGroupById(), new AsyncCallback<Group>() {

				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Upps");
				}

				@Override
				public void onSuccess(Group result) {
					List<Group> groupList = groupDataProvider.get(result).getList();

					for (int i = 0; i < groupList.size(); i++) {
						if (group.getId() == groupList.get(i).getId()) {
							groupList.set(i, group);
							break;
						}
					}
				}
			});
		}

		/**
		 * Loeschung eines Group-Objekts aus der GroceryList.
		 * 
		 * @param group die geloescht wird
		 */
		public void deleteGroup(Group group) {

			// Wenn der Baumknoten noch nicht angelegt wurde, gibt es hier nichts zu tun
			if (!this.groupDataProvider.containsKey(group)) {
				return;
			}

			groupDataProvider.remove(group);
			selectionModel.setSelected(group, true);
		}

		@Override
		public <T> NodeInfo<?> getNodeInfo(T value) {

			// Auflistung der GroceryListen
			if (value == null) {

				// Erzeugen eines neuen ListDataProviders fuer die GroceryList
				this.groceryListDataProvider = new ListDataProvider<GroceryList>();

				// Abfrage aller GroceryList des Users wo er als Eigentuemer deklariert ist
				this.ev.getGroceryListByOwner(this.u, new AsyncCallback<Vector<GroceryList>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Vector<GroceryList> result) {
							//Statusabfrage ob GroceryList geteilt wurde
							ev.statusSharingGroceryList(result, new AsyncCallback<Vector<GroceryList>>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();								
								}

								@Override
								public void onSuccess(Vector<GroceryList> result) {
									//ALle GroceryListen dem ListDataProvider hinzufuegen.
									groceryListDataProvider.getList().addAll(result);
									
								}
								
							});
						
						}
				});
				
				return new DefaultNodeInfo<GroceryList>(groceryListDataProvider, new GroceryListCell(), selectionModel, null);
			}
		

			
			 // Wenn Value eine Instanz von GroceryList ist werden die Kontakte aus der
			 // Datenbank rausgelesen und dem ListDataProvider hinzugef�gt.
			if (value instanceof GroceryList) {
				
				final ListDataProvider<Group> groupLDP = new ListDataProvider<Group>();
				this.groupDataProvider.put((GroceryList) value, groupLDP);
				int groceryListId = ((GroceryList) value).getId();
				
				// Auslesen der group die in einer spezfischen GroceryList enthalten sind
				this.ev.getGroupByGroceryList(groceryListId, new AsyncCallback<Vector<Group>>() {

					public void onFailure(Throwable caught) {
						ClientsideSettings.getLogger().severe("Upps, GroceryList is not available");
					}

					public void onSuccess(Vector<Group> result) {
						
							ev.statusSharingGroup(result, new AsyncCallback<Vector<Group>>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onSuccess(Vector<Group> result) {
									
										groupLDP.getList().addAll(result);
									 {
									
									}
									
								}
								
							});
						
					}

				});

				return new DefaultNodeInfo<Group>(groupLDP, new GroupCell(), selectionModel, null);
		
			}
			
			return null;
		}
		
		/**
		 * Ueberpruefung ob das Objekt ein Blatt ist.
		 */
		public boolean isLeaf(Object value) {
			return (value instanceof Group);
		}


}
