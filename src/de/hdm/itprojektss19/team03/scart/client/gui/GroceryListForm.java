package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.List;

import java.util.Vector;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryListArticle;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * @author tom
 * @author bastiantilk
 *
 */
public class GroceryListForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditor();

	private User user = null;

	VerticalPanel vt = new VerticalPanel();
	HorizontalPanel hpTitle = new HorizontalPanel();
	HorizontalPanel hpButtons = new HorizontalPanel();

	Button addBtn = new Button("<image src='/images/plusButton.png' width='16px' height='16px' align='right'/>");
	Button editBtn = new Button("<image src='/images/editButton.png' width='16px' height='16px' align='center'/>");
	Button deleteBtn = new Button("<image src='/images/minusButton.png' width='16px' height='16px' align='left'/>");
	Button checkBtn = new Button();

	Boolean addBtnBoolean = false;
	Boolean editBtnBoolean = false;
	Boolean deleteBtnBoolean = false;
	Boolean checkBtnBoolean = false;

	TextBox editTb1 = new TextBox(); // Artikel
	TextBox editTb2 = new TextBox(); // Menge
	TextBox editTb3 = new TextBox(); // Mengeneinheit
	TextBox editTb4 = new TextBox(); // RetailerId

	FlexTable aTable = new FlexTable();
	FlexTable bTable = new FlexTable();
	Article tempArticle = new Article(); //temporaerer Artikel wenn ein Artikel 
	//geupdated/neu erstellt wird, um dieses Objekt auch an die DB zu pushen
	
	Retailer r = new Retailer();
	Vector<Article> articleVec = new Vector<Article>();

	Label titelLabel = new Label();

	ScrollPanel sc = new ScrollPanel();

	GroceryList groceryList = new GroceryList(); // Muss bei dem Aufruf der GUI-Seite uebergeben werden

	GroceryListArticle aGl = new GroceryListArticle();
	// GroceryListArticle aGl = new GroceryListArticle(a.getId(),
	// groceryList.getId());

	public void onLoad() {
		super.onLoad();
		vt.addStyleName("main-panel");

		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);

		// Titel Label wird in Horitontales Panel eingefuegt
		hpTitle.add(titelLabel);

		// CellTable wird in das Scroll Panel hinzugefuegt
		// sc.add(aTable);

		vt.add(hpTitle);

		// GroceryListId = Parameter sollte bei seitenaufruf uebergeben werden.
		int groceryListId = groceryList.getId();

		loadTable(); // Ruft Metode zum laden/fuellen der Tabelle auf

		vt.add(aTable);

		vt.add(bTable);

		// Buttons werden dem untersten horizontal Panel hinzugefuegt
		hpButtons.add(addBtn);
		hpButtons.add(editBtn);
		hpButtons.add(deleteBtn);
		hpButtons.add(checkBtn);

		addBtn.addClickHandler(new AddClickHandler());
		addBtn.setEnabled(true);

		editBtn.addClickHandler(new EditClickHandler());
		editBtn.setEnabled(true);

		deleteBtn.addClickHandler(new DeleteClickHandler());
		deleteBtn.setEnabled(true);

		checkBtn.addClickHandler(new CheckClickHandler());
		checkBtn.setEnabled(true);

		/**
		 * newArticleBtn.setPixelSize(100, 60); newArticleBtn.setStyleName("button1");
		 * newArticleBtn.setTitle("add Article");
		 */
		vt.add(hpButtons);
		this.add(vt);
		// RootPanel.get("content").add(vt);
	}

	/*
	 * Methode zum Laden der Tabelle bei erstem Aufruf oder zum Neu-laden bei einer
	 * Aktualisierung der Daten
	 * 
	 */
	public void loadTable() {
		try {
			int groceryListId = groceryList.getId();

			ev.findAllArticleByGroceryList(groceryListId, new AsyncCallback<Vector<Article>>() {
				public void onFailure(Throwable caught) {
					throw new IllegalArgumentException("Einkaufsliste konnte nicht geladen werden");
				}

				public void onSuccess(Vector<Article> arg0) {
					articleVec = arg0;

					// Loescht alle Zeilen der FlexTables
					aTable.removeAllRows();
					bTable.removeAllRows();

					// Header der Flextable werden gesetzt
					aTable.setText(0, 0, "Artikel");
					aTable.setText(0, 1, "Menge");
					aTable.setText(0, 2, "Mengeneinheit");
					aTable.setText(0, 3, "Laden");
					bTable.setText(0, 0, "Gekauft");

					int vectorNumber = 0; //temporaerer Zaehler um durch den Artikel-Vektor durchzugehen
					int trueCount = 1; //Checkbox gleich true. Artikel wurd gekauft
					int falseCount = 1; //Checkbox gleich false. Artikel noch nicht gekauft
					int visibleNum = 0;

					// for Schleife das alle Artikel mit Name Quantity Unit und RetailerName
					// aufgelistet werden im Panel.
					for (int aNum = 0; aNum <= articleVec.size(); aNum++) {

						if (articleVec.get(vectorNumber).getCheckBoolean() == false) {
							aTable.setText(falseCount, 0, articleVec.get(vectorNumber).getName());
							aTable.setText(falseCount, 1, Integer.toString(articleVec.get(vectorNumber).getQuantity()));
							aTable.setText(falseCount, 2, articleVec.get(vectorNumber).getUnit());
							aTable.setText(falseCount, 3, Integer.toString(articleVec.get(vectorNumber).getRetailerId()));
							falseCount++;
						} else {
							bTable.setText(trueCount, 0, articleVec.get(vectorNumber).getName());
							bTable.setText(trueCount, 1, Integer.toString(articleVec.get(vectorNumber).getQuantity()));
							bTable.setText(trueCount, 2, articleVec.get(vectorNumber).getUnit());
							bTable.setText(trueCount, 3, Integer.toString(articleVec.get(vectorNumber).getRetailerId()));
							trueCount++;
							visibleNum = trueCount;
						}
						vectorNumber++;
					}
					if (visibleNum > 1) {
						bTable.setVisible(false);
					}
				}
			});
		} catch (IllegalArgumentException e) {
			Window.alert("Einkaufsliste konnte nicht geladen werden");
		}

	}

	/**
	 * @param node
	 * @return https://stackoverflow.com/questions/11415652/remove-row-from-flextable-in-gwt
	 *         Liest aus welche Reihe ausgewaehlt wurde durch einen ClickHandler.
	 */
	public static TableRowElement findNearestParentRow(Node node) {
		Node element = findNearestParentNodeByType(node, "tr");
		if (element != null) {
			return element.cast();
		}
		return null;
	}

	public static Node findNearestParentNodeByType(Node node, String nodeType) {
		while ((node != null)) {
			if (Element.is(node)) {
				Element elem = (Element) Element.as(node);

				String tagName = elem.getTagName();

				if (nodeType.equalsIgnoreCase(tagName)) {
					return elem.cast();
				}

			}
			node = node.getParentNode();
		}
		return null;
	}

	/**
	 * @author tom
	 * 
	 *         getCbCheck: CheckBox und ClickHandler die das beim kaufen eines
	 *         Artikels aufgerufen werden. CheckBox wird im letzem Column generiert
	 *         Ausgewaehlte Reihe wird gel�scht und in die zweite Tabele kopiert und
	 *         eine neue CheckBox wird kreiert getCbReturn.
	 * 
	 */
	public CheckBox getCbCheck() {
		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final int rowIndex = aTable.getCellForEvent(event).getRowIndex();
				final int rowCount = bTable.getRowCount();

				tempArticle.setName(aTable.getText(rowIndex, 0));
				tempArticle.setQuantity(Integer.parseInt(aTable.getText(rowIndex, 1)));
				tempArticle.setUnit(aTable.getText(rowIndex, 2));
				tempArticle.setRetailerId(Integer.parseInt(aTable.getText(rowIndex, 3)));
				tempArticle.setCheckBoolean(true);

				ev.saveArticle(tempArticle, new AsyncCallback<Article>() {
					public void onFailure(Throwable caught) {
						Window.alert("Artikel konnte nicht hinzugefügt werden");
						tempArticle = null; // Article-Objekt wird geleert, da Artikel nicht hinzugefuegt werdn konnte
					}

					public void onSuccess(Article arg0) {
						bTable.setText(rowCount, 0, arg0.getName());
						bTable.setText(rowCount, 1, Integer.toString(arg0.getQuantity()));
						bTable.setText(rowCount, 2, arg0.getUnit());
						bTable.setText(rowCount, 3, Integer.toString(arg0.getRetailerId()));
						bTable.setWidget(rowCount, 4, getCbReturn());
						aTable.removeRow(rowIndex);

						if (bTable.getRowCount() >= 2) {
							bTable.setVisible(true);
						}
						tempArticle = null; //Artikle-Objekt wurde geleert, da Artikel erfolgreich in der DB angelegt werden konnte
						loadTable();
					}
				});
			}
		});
		cb.setValue(false);
		return cb;
	}

	/**
	 * @author tom
	 * 
	 *         Schreibt die ausgewaehlte Zeile in die erste Tabele fals ein Artikel
	 *         falscherweise als gekauft markiert wurde.
	 */
	public CheckBox getCbReturn() {
		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final int rowIndex = bTable.getCellForEvent(event).getRowIndex();
				final int rowCount = aTable.getRowCount();
				
				tempArticle = null;
				
				tempArticle.setName(bTable.getText(rowIndex, 0));
				tempArticle.setQuantity(Integer.parseInt(bTable.getText(rowIndex, 1)));
				tempArticle.setUnit(bTable.getText(rowIndex, 2));
				tempArticle.setRetailerId(Integer.parseInt(bTable.getText(rowIndex, 3)));
				tempArticle.setCheckBoolean(false);

				ev.saveArticle(tempArticle, new AsyncCallback<Article>() {
					public void onFailure(Throwable caught) {
						Window.alert("Artikel konnte nicht von der gekauft Tabelle entfernt werden");
						tempArticle = null;
					}

					public void onSuccess(Article arg0) {
						aTable.setText(rowCount, 0, arg0.getName());
						aTable.setText(rowCount, 1, Integer.toString(arg0.getQuantity()));
						aTable.setText(rowCount, 2, arg0.getUnit());
						aTable.setText(rowCount, 3, Integer.toString(arg0.getRetailerId()));
						aTable.setWidget(rowCount, 4, getCbCheck());
						bTable.removeRow(rowIndex);

						if (bTable.getRowCount() < 2) {
							bTable.setVisible(false);
						}
						loadTable();
						//Vektor der alle Artikel speichert muss aktualisiert werden
						//Neu Laden der gesamten Tabelle ? (notwendig)
						tempArticle = null;
					}
				});
			}
		});
		cb.setValue(true);
		return cb;
	}

	/**
	 * @author tom
	 * 
	 *         Button ClickHandler fuer gekaufte Artikel. Setzt den Boolean des
	 *         Buttons true und false und schaut ob andere Buttons aktiv sind. Setzt
	 *         und l�scht CheckBoxen aus der dem letzten Column.
	 *
	 */
	public class CheckClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (checkBtnBoolean == false && editBtnBoolean == false && deleteBtnBoolean == false
					&& addBtnBoolean == false) {
				checkBtnBoolean = true;
				for (int aNum = 1; aNum < aTable.getRowCount(); aNum++) {
					aTable.setWidget(aNum, 4, getCbCheck());
				}
				for (int bNum = 1; bNum < bTable.getRowCount(); bNum++) {
					bTable.setWidget(bNum, 4, getCbReturn());
				}
			} else if (checkBtnBoolean == true) {
				checkBtnBoolean = false;
				for (int aNum = 1; aNum < aTable.getRowCount(); aNum++) {
					aTable.removeCell(aNum, 4);
				}
				for (int bNum = 1; bNum < aTable.getRowCount(); bNum++) {
					bTable.removeCell(bNum, 4);
				}
			} else if (editBtnBoolean == false || deleteBtnBoolean == false || addBtnBoolean == false) {
				Window.alert("Bitte den anderen Button deaktivieren.");
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");

			}

		}
	}

	/**
	 * @author tom
	 * 
	 *         setzt Atribute die in mehreren ClickHandlern gebraucht werden.
	 */
	public int globalRow;
	public int finalGlobalRow;
	public String first;
	public String second;
	public String third;
	public String fourth;

	/**
	 * @author tom
	 * 
	 *         CheckBox fuer Editieren ClickHandler um die ausgewaehlte Reihe global
	 *         zu speichern ValueChangeHandler um den Text in TextBoxen zu
	 *         �bertragen und den Text aus TextBoxen zu speichern.
	 */
	public CheckBox getCbEdit() {
		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final int rowIndex = aTable.getCellForEvent(event).getRowIndex();
				globalRow = rowIndex;
			}
		});
		cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue() == true) {
					if (editTb1.getText().isEmpty() == true && editTb2.getText().isEmpty() == true
							&& editTb3.getText().isEmpty() == true && editTb4.getText().isEmpty() == true) {
						finalGlobalRow = globalRow;
						first = aTable.getText(globalRow, 0);
						second = aTable.getText(globalRow, 1);
						third = aTable.getText(globalRow, 2);
						fourth = aTable.getText(globalRow, 3);
						editTb1.setText(aTable.getText(globalRow, 0));
						editTb2.setText(aTable.getText(globalRow, 1));
						editTb3.setText(aTable.getText(globalRow, 2));
						editTb4.setText(aTable.getText(globalRow, 3));
						aTable.setWidget(globalRow, 0, editTb1);
						aTable.setWidget(globalRow, 1, editTb2);
						aTable.setWidget(globalRow, 2, editTb3);
						aTable.setWidget(globalRow, 3, editTb4);
					} else if (editTb1.getText().isEmpty() == false && editTb2.getText().isEmpty() == false
							&& editTb3.getText().isEmpty() == false && editTb4.getText().isEmpty() == false) {
						aTable.clearCell(finalGlobalRow, 0);
						aTable.clearCell(finalGlobalRow, 1);
						aTable.clearCell(finalGlobalRow, 2);
						aTable.clearCell(finalGlobalRow, 3);
						aTable.setText(finalGlobalRow, 0, editTb1.getText());
						aTable.setText(finalGlobalRow, 1, editTb2.getText());
						aTable.setText(finalGlobalRow, 2, editTb3.getText());
						aTable.setText(finalGlobalRow, 3, editTb4.getText());
						
						
						tempArticle.setName(editTb1.getText());
						tempArticle.setQuantity(Integer.parseInt(editTb2.getText()));
						tempArticle.setUnit(editTb3.getText());
						tempArticle.setRetailerId(Integer.parseInt(editTb4.getText()));
						
						ev.saveArticle(tempArticle, new AsyncCallback<Article>() {
							public void onFailure(Throwable caught) {
								Window.alert("Artikel konnte nicht editiert werden");
							}

							public void onSuccess(Article arg0) {
								//loadTable(); Tabelle wird neu geladen
								
								//Ist folgender Code noetig, wenn der editierte Artikel hinzugefuegt werden konnte
								//und die Tabelle neu geladen wird?
							}
						});

						editTb1.setText(null);
						editTb2.setText(null);
						editTb3.setText(null);
						editTb4.setText(null);
						first = aTable.getText(globalRow, 0);
						second = aTable.getText(globalRow, 1);
						third = aTable.getText(globalRow, 2);
						fourth = aTable.getText(globalRow, 3);
						aTable.removeCell(finalGlobalRow, 4);
						aTable.setWidget(finalGlobalRow, 4, getCbEdit());
						if (aTable.getText(globalRow, 0).isEmpty() == false
								&& aTable.getText(globalRow, 1).isEmpty() == false
								&& aTable.getText(globalRow, 2).isEmpty() == false
								&& aTable.getText(globalRow, 3).isEmpty() == false) {
							finalGlobalRow = globalRow;
							first = aTable.getText(globalRow, 0);
							second = aTable.getText(globalRow, 1);
							third = aTable.getText(globalRow, 2);
							fourth = aTable.getText(globalRow, 3);
							editTb1.setText(aTable.getText(globalRow, 0));
							editTb2.setText(aTable.getText(globalRow, 1));
							editTb3.setText(aTable.getText(globalRow, 2));
							editTb4.setText(aTable.getText(globalRow, 3));
							aTable.setWidget(globalRow, 0, editTb1);
							aTable.setWidget(globalRow, 1, editTb2);
							aTable.setWidget(globalRow, 2, editTb3);
							aTable.setWidget(globalRow, 3, editTb4);
						}
					} else {
						aTable.clearCell(finalGlobalRow, 0);
						aTable.clearCell(finalGlobalRow, 1);
						aTable.clearCell(finalGlobalRow, 2);
						aTable.clearCell(finalGlobalRow, 3);
						aTable.removeCell(finalGlobalRow, 4);
						aTable.setWidget(finalGlobalRow, 4, getCbEdit());
						aTable.setText(finalGlobalRow, 0, first);
						aTable.setText(finalGlobalRow, 1, second);
						aTable.setText(finalGlobalRow, 2, third);
						aTable.setText(finalGlobalRow, 3, fourth);
						first = aTable.getText(globalRow, 0);
						second = aTable.getText(globalRow, 1);
						third = aTable.getText(globalRow, 2);
						fourth = aTable.getText(globalRow, 3);
						editTb1.setText(null);
						editTb2.setText(null);
						editTb3.setText(null);
						editTb4.setText(null);
						aTable.setWidget(finalGlobalRow, 4, getCbEdit());
						if (aTable.getText(globalRow, 0).isEmpty() == false
								&& aTable.getText(globalRow, 1).isEmpty() == false
								&& aTable.getText(globalRow, 2).isEmpty() == false
								&& aTable.getText(globalRow, 3).isEmpty() == false) {
							finalGlobalRow = globalRow;
							editTb1.setText(aTable.getText(globalRow, 0));
							editTb2.setText(aTable.getText(globalRow, 1));
							editTb3.setText(aTable.getText(globalRow, 2));
							editTb4.setText(aTable.getText(globalRow, 3));
							aTable.setWidget(globalRow, 0, editTb1);
							aTable.setWidget(globalRow, 1, editTb2);
							aTable.setWidget(globalRow, 2, editTb3);
							aTable.setWidget(globalRow, 3, editTb4);
						}
						Window.alert("Änderung wurde nicht gespeichert");
					}
				} else {
					if (editTb1.getText().isEmpty() == false && editTb2.getText().isEmpty() == false
							&& editTb3.getText().isEmpty() == false && editTb4.getText().isEmpty() == false) {
						aTable.clearCell(globalRow, 0);
						aTable.clearCell(globalRow, 1);
						aTable.clearCell(globalRow, 2);
						aTable.clearCell(globalRow, 3);
						aTable.setText(globalRow, 0, editTb1.getText());
						aTable.setText(globalRow, 1, editTb2.getText());
						aTable.setText(globalRow, 2, editTb3.getText());
						aTable.setText(globalRow, 3, editTb4.getText());
						
						tempArticle.setName(editTb1.getText());
						tempArticle.setQuantity(Integer.parseInt(editTb2.getText()));
						tempArticle.setUnit(editTb3.getText());
						tempArticle.setRetailerId(Integer.parseInt(editTb4.getText()));
						
						ev.saveArticle(tempArticle, new AsyncCallback<Article>() {
							public void onFailure(Throwable caught) {
							}

							public void onSuccess(Article arg0) {

								// FEHLT NOCH: Aktualisieren des Artikel-Vektors und erneuern der gesamten
								// Tabelle

							}
						});

						editTb1.setText(null);
						editTb2.setText(null);
						editTb3.setText(null);
						editTb4.setText(null);
					} else {
						aTable.remove(editTb1);
						aTable.remove(editTb2);
						aTable.remove(editTb3);
						aTable.remove(editTb4);
						aTable.setText(globalRow, 0, first);
						aTable.setText(globalRow, 1, second);
						aTable.setText(globalRow, 2, third);
						aTable.setText(globalRow, 3, fourth);
						editTb1.setText(null);
						editTb2.setText(null);
						editTb3.setText(null);
						editTb4.setText(null);
						aTable.setWidget(globalRow, 4, getCbEdit());
						Window.alert("Änderung wurde nicht gespeichert");
					}
				}
			}
		});
		cb.setValue(false);
		return cb;
	}

	/**
	 * @author tom
	 *
	 *         ClickHandler fuer EditButton setzt Boolean des Buttons true und false
	 *         und schaut ob andere Buttons aktiv sind.
	 */
	public class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (editBtnBoolean == false && checkBtnBoolean == false && deleteBtnBoolean == false
					&& addBtnBoolean == false) {
				editBtnBoolean = true;
				globalRow = 0;
				for (int aNum = 1; aNum < aTable.getRowCount(); aNum++) {
					aTable.setWidget(aNum, 4, getCbEdit());
				}
			} else if (editBtnBoolean == true) {
				editBtnBoolean = false;
				if (editTb1.getText().isEmpty() == false && editTb2.getText().isEmpty() == false
						&& editTb3.getText().isEmpty() == false && editTb4.getText().isEmpty() == false) {
					aTable.clearCell(globalRow, 0);
					aTable.clearCell(globalRow, 1);
					aTable.clearCell(globalRow, 2);
					aTable.clearCell(globalRow, 3);
					aTable.setText(globalRow, 0, editTb1.getText());
					aTable.setText(globalRow, 1, editTb2.getText());
					aTable.setText(globalRow, 2, editTb3.getText());
					aTable.setText(globalRow, 3, editTb4.getText());
					
					tempArticle.setName(editTb1.getText());
					tempArticle.setQuantity(Integer.parseInt(editTb2.getText()));
					tempArticle.setUnit(editTb3.getText());
					tempArticle.setRetailerId(Integer.parseInt(editTb4.getText()));
					
					ev.saveArticle(tempArticle, new AsyncCallback<Article>() {
						public void onFailure(Throwable caught) {
						}

						public void onSuccess(Article arg0) {

							// FEHLT NOCH: Aktualisieren des Artikel-Vektors und erneuern der gesamten
							// Tabelle

						}
					});

					editTb1.setText(null);
					editTb2.setText(null);
					editTb3.setText(null);
					editTb4.setText(null);
				} else if (globalRow != 0) {
					aTable.remove(editTb1);
					aTable.remove(editTb2);
					aTable.remove(editTb3);
					aTable.remove(editTb4);
					aTable.setText(globalRow, 0, first);
					aTable.setText(globalRow, 1, second);
					aTable.setText(globalRow, 2, third);
					aTable.setText(globalRow, 3, fourth);
					editTb1.setText(null);
					editTb2.setText(null);
					editTb3.setText(null);
					editTb4.setText(null);
					Window.alert("Änderung wurde nicht gespeichert");
				}
				for (int i = 1; i <= aTable.getRowCount(); i++) {
					aTable.removeCell(i, 4);
				}
			} else if (checkBtnBoolean == false || deleteBtnBoolean == false || addBtnBoolean == false) {
				Window.alert("Bitte anderen Button deaktivieren.");
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}
	}

	/**
	 * @author tom
	 * 
	 *         CheckBox fuer DelteButton. Loescht die ausgewaehlte Reihe aus der
	 *         Tabele.
	 */
	public CheckBox getCbDel() {
		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final int rowIndex = aTable.getCellForEvent(event).getRowIndex();
				ev.deleteArticle(tempArticle, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess(Void arg0) {
						// FEHLT NOCH: Connection zwischen GroceryList 
						// 	und Article loesen und dann den Artikel aus der DB loeschen
						
						// FEHLT NOCH: Aktualisieren des Artikel-Vektors und erneuern der gesamten Tabelle
					}
				});
				aTable.removeRow(rowIndex);
			}
		});
		cb.setValue(false);
		return cb;
	}

	/**
	 * @author tom
	 *
	 *         ClickHandler fuer DeleteButton setzt Boolean des Buttons true und
	 *         false und schaut ob andere Buttons aktiv sind Ausgewaehlte Reihe wird
	 *         aus der Tabele gel�scht.
	 */
	public class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (deleteBtnBoolean == false && checkBtnBoolean == false && editBtnBoolean == false
					&& addBtnBoolean == false) {
				deleteBtnBoolean = true;
				for (int aNum = 1; aNum < aTable.getRowCount(); aNum++) {
					aTable.setWidget(aNum, 4, getCbDel());
				}
			} else if (deleteBtnBoolean == true) {
				for (int i = 1; i < aTable.getRowCount(); i++) {
					if (aTable.getText(i, 4).isEmpty() == true) {
						aTable.removeCell(i, 4);
					}
				}
				deleteBtnBoolean = false;
			} else if (checkBtnBoolean == true || editBtnBoolean == true || addBtnBoolean == false) {
				Window.alert("Bitte anderen Button deaktivieren.");
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}
	}

	/**
	 * @author tom
	 *
	 *         ClickHandler fuer EditButton setzt Boolean des Buttons true und false
	 *         und schaut ob andere Buttons aktiv sind. Fuegt eine neue Column mit
	 *         TextBoxen hinzu in der man einen Neuen Artikel mit allen Atributen
	 *         anlegen kann.
	 */
	public class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {

			if (deleteBtnBoolean == false && checkBtnBoolean == false && editBtnBoolean == false
					&& addBtnBoolean == false) {
				addBtnBoolean = true;
				int i = aTable.getRowCount();
				aTable.setWidget(i, 0, editTb1);
				aTable.setWidget(i, 1, editTb2);
				aTable.setWidget(i, 2, editTb3);
				aTable.setWidget(i, 3, editTb4);
			} else if (addBtnBoolean == true) {
				if (editTb1.getText().isEmpty() == false && editTb2.getText().isEmpty() == false
						&& editTb3.getText().isEmpty() == false && editTb4.getText().isEmpty() == false) {
					addBtnBoolean = false;
					int addRow = aTable.getRowCount() - 1;
					aTable.clearCell(addRow, 0);
					aTable.clearCell(addRow, 1);
					aTable.clearCell(addRow, 2);
					aTable.clearCell(addRow, 3);
					aTable.setText(addRow, 0, editTb1.getText());
					aTable.setText(addRow, 1, editTb2.getText());
					aTable.setText(addRow, 2, editTb3.getText());
					aTable.setText(addRow, 3, editTb4.getText());
					// Artikel-Object muss schon erstellt sein, bevor es an die DB zur speicherung
					// weitergegeben wird
					tempArticle.setId(articleVec.size() + 1);
					tempArticle.setName(editTb1.getText());
					tempArticle.setQuantity(Integer.parseInt(aTable.getText(addRow, 1)));
					tempArticle.setUnit(editTb3.getText());
					tempArticle.setRetailerId(Integer.parseInt(aTable.getText(addRow, 3)));
					
					 ev.createArticle(tempArticle, new AsyncCallback<Article>() {
						public void onFailure(Throwable caught) {
							Window.alert("Artikel konnte nicht erstellt werden");
							// Article Object a existiert dennoch mit den zugewiesenen Attributen
						}

						public void onSuccess(Article arg0) {
							tempArticle = arg0; //tempArticle bekommt die korrekte Id aus der DB

							ev.addArticleToGroceryList(groceryList, tempArticle, new AsyncCallback<GroceryListArticle>() { 
								public void onFailure(Throwable caught) {
									// Artikel konnte nicht mit der GroceryList verbunden werden. Das Article-Objekt
									// wird jetzt auch aus der DB geloescht
									ev.deleteArticle(tempArticle, new AsyncCallback<Void>() {
										public void onFailure(Throwable caught) {
											Window.alert(
													"Artikel konnte nicht mit der GroceryListe verbunden werden. Außerdem konnte der fehlerhafte Artikel konnte nicht gelöscht werden");
										}

										@Override
										public void onSuccess(Void arg0) {
											Window.alert("Artikel konnte nicht mit Einkaufsliste verbunden werden. Artikel aus der Datenbank gelöscht");
											
										}
									});
								}

								public void onSuccess(GroceryListArticle arg0) {
									aGl = arg0;
									Window.alert("Artikel " + tempArticle.getName() + " wurde  der Einkaufsliste "
											+ groceryList.getGroceryListName() + " hinzugefügt");
									
									loadTable(); //Tabelle frage alle Artikel neu an und wir neu geladen
									
								}
							});
						}
					});

					editTb1.setText(null);
					editTb2.setText(null);
					editTb3.setText(null);
					editTb4.setText(null);
				} else if (editTb1.getText().isEmpty() == false || editTb2.getText().isEmpty() == false
						|| editTb3.getText().isEmpty() == false || editTb4.getText().isEmpty() == false) {
					Window.alert("Es wurde kein Artikel hinzugef�gt da die Angaben nicht vollst�ndig waren");
				} else {
					addBtnBoolean = false;
					aTable.removeRow(aTable.getRowCount() - 1);
					editTb1.setText(null);
					editTb2.setText(null);
					editTb3.setText(null);
					editTb4.setText(null);
				}
			} else if (checkBtnBoolean == true || editBtnBoolean == true || deleteBtnBoolean == false) {
				Window.alert("Bitte anderen Button deaktivieren.");
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}
	}

}