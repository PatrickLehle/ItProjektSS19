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

	FlexTable articleTable = new FlexTable();
	FlexTable boughtTable = new FlexTable();
	Article article = new Article(); //temporaerer Artikel wenn ein Artikel 
	//geupdated/neu erstellt wird, um dieses Objekt auch an die DB zu pushen
	
	Retailer retailer = new Retailer();
	Vector<Article> articleVector = new Vector<Article>();

	Label titelLabel = new Label();

	ScrollPanel sc = new ScrollPanel();

	GroceryList groceryList = new GroceryList(); // Muss bei dem Aufruf der GUI-Seite uebergeben werden

	GroceryListArticle groceryListArticle = new GroceryListArticle();
	// GroceryListArticle aGl = new GroceryListArticle(a.getId(),
	// groceryList.getId());

	public void onLoad() {
		super.onLoad();
		this.addStyleName("main-panel");

		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);

		// Titel Label wird in Horitontales Panel eingefuegt
		hpTitle.add(titelLabel);

		// CellTable wird in das Scroll Panel hinzugefuegt
		// sc.add(aTable);

		this.add(hpTitle);

		// GroceryListId = Parameter sollte bei seitenaufruf uebergeben werden.
		int groceryListId = groceryList.getId();

		loadTable(); // Ruft Metode zum laden/fuellen der Tabelle auf

		this.add(articleTable);

		this.add(boughtTable);

		// Buttons werden dem untersten horizontal Panel hinzugefuegt
		hpButtons.add(addBtn);
		hpButtons.add(editBtn);
		hpButtons.add(deleteBtn);
		hpButtons.add(checkBtn);

		addBtn.addClickHandler(new AddClickHandler());
		editBtn.addClickHandler(new EditClickHandler());
		deleteBtn.addClickHandler(new DeleteClickHandler());
		checkBtn.addClickHandler(new CheckClickHandler());
		
		this.add(hpButtons);
		this.add(vt);
	}

	/*
	 * @author bastiantilk
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
					articleVector = arg0;

					// Loescht alle Zeilen der FlexTables
					articleTable.removeAllRows();
					boughtTable.removeAllRows();

					// Header der Flextable werden gesetzt
					articleTable.setText(0, 0, "Artikel");
					articleTable.setText(0, 1, "Menge");
					articleTable.setText(0, 2, "Mengeneinheit");
					articleTable.setText(0, 3, "Laden");
					boughtTable.setText(0, 0, "Gekauft");

					int vectorNumber = 0; //temporaerer Zaehler um durch den Artikel-Vektor durchzugehen
					int trueCount = 1; //Checkbox gleich true. Artikel wurd gekauft
					int falseCount = 1; //Checkbox gleich false. Artikel noch nicht gekauft
					int visibleNum = 0;

					// for Schleife das alle Artikel mit Name Quantity Unit und RetailerName
					// aufgelistet werden im Panel.
<<<<<<< Updated upstream
					for (int i = 0; i <= articleVec.size(); i++) {
=======
					for (int aNum = 0; aNum <= articleVector.size(); aNum++) {
>>>>>>> Stashed changes

						if (articleVec.get(vectorNumber).getCheckBoolean() == false) {
							articleTable.setText(falseCount, 0, articleVec.get(vectorNumber).getName());
							articleTable.setText(falseCount, 1, Integer.toString(articleVec.get(vectorNumber).getQuantity()));
							articleTable.setText(falseCount, 2, articleVec.get(vectorNumber).getUnit());
							articleTable.setText(falseCount, 3, Integer.toString(articleVec.get(vectorNumber).getRetailerId()));
							falseCount++;
						} else {
							boughtTable.setText(trueCount, 0, articleVec.get(vectorNumber).getName());
							boughtTable.setText(trueCount, 1, Integer.toString(articleVec.get(vectorNumber).getQuantity()));
							boughtTable.setText(trueCount, 2, articleVec.get(vectorNumber).getUnit());
							boughtTable.setText(trueCount, 3, Integer.toString(articleVec.get(vectorNumber).getRetailerId()));
							trueCount++;
							visibleNum = trueCount;
						}
						vectorNumber++;
					}
					if (visibleNum > 1) {
						boughtTable.setVisible(false);
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
	 *         Liest aus, welche Reihe ausgewaehlt wurde durch einen ClickHandler.
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
	 *         getCbCheck: CheckBox und ClickHandler die bei dem kaufen eines
	 *         Artikels aufgerufen werden. CheckBox wird im letzem Column generiert
	 *         Ausgewaehlte Reihe wird geloescht und in die zweite Tabelle kopiert und
	 *         eine neue CheckBox wird kreiert getCbReturn.
	 * 
	 */
	public CheckBox getCbCheck() {
		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final int rowIndex = articleTable.getCellForEvent(event).getRowIndex();
				final int rowCount = boughtTable.getRowCount();

				article.setName(articleTable.getText(rowIndex, 0));
				article.setQuantity(Integer.parseInt(articleTable.getText(rowIndex, 1)));
				article.setUnit(articleTable.getText(rowIndex, 2));
				article.setRetailerId(Integer.parseInt(articleTable.getText(rowIndex, 3)));
				article.setCheckBoolean(true);

				ev.saveArticle(article, new AsyncCallback<Article>() {
					public void onFailure(Throwable caught) {
						Window.alert("Artikel konnte nicht hinzugefügt werden");
						article = null; // Article-Objekt wird geleert, da Artikel nicht hinzugefuegt werdn konnte
					}

					public void onSuccess(Article arg0) {
						boughtTable.setText(rowCount, 0, arg0.getName());
						boughtTable.setText(rowCount, 1, Integer.toString(arg0.getQuantity()));
						boughtTable.setText(rowCount, 2, arg0.getUnit());
						boughtTable.setText(rowCount, 3, Integer.toString(arg0.getRetailerId()));
						boughtTable.setWidget(rowCount, 4, getCbReturn());
						articleTable.removeRow(rowIndex);

						if (boughtTable.getRowCount() >= 2) {
							boughtTable.setVisible(true);
						}
						article = null; //Artikle-Objekt wurde geleert, da Artikel erfolgreich in der DB angelegt werden konnte
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
				final int rowIndex = boughtTable.getCellForEvent(event).getRowIndex();
				final int rowCount = articleTable.getRowCount();
				
				article = null;
				
				article.setName(boughtTable.getText(rowIndex, 0));
				article.setQuantity(Integer.parseInt(boughtTable.getText(rowIndex, 1)));
				article.setUnit(boughtTable.getText(rowIndex, 2));
				article.setRetailerId(Integer.parseInt(boughtTable.getText(rowIndex, 3)));
				article.setCheckBoolean(false);

				ev.saveArticle(article, new AsyncCallback<Article>() {
					public void onFailure(Throwable caught) {
						Window.alert("Artikel konnte nicht von der gekauft Tabelle entfernt werden");
						article = null;
					}

					public void onSuccess(Article arg0) {
						articleTable.setText(rowCount, 0, arg0.getName());
						articleTable.setText(rowCount, 1, Integer.toString(arg0.getQuantity()));
						articleTable.setText(rowCount, 2, arg0.getUnit());
						articleTable.setText(rowCount, 3, Integer.toString(arg0.getRetailerId()));
						articleTable.setWidget(rowCount, 4, getCbCheck());
						boughtTable.removeRow(rowIndex);

						if (boughtTable.getRowCount() < 2) {
							boughtTable.setVisible(false);
						}
						loadTable();
						//Vektor der alle Artikel speichert muss aktualisiert werden
						//Neu Laden der gesamten Tabelle ? (notwendig)
						article = null;
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
				for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
					articleTable.setWidget(aNum, 4, getCbCheck());
				}
				for (int bNum = 1; bNum < boughtTable.getRowCount(); bNum++) {
					boughtTable.setWidget(bNum, 4, getCbReturn());
				}
			} else if (checkBtnBoolean == true) {
				checkBtnBoolean = false;
				for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
					articleTable.removeCell(aNum, 4);
				}
				for (int bNum = 1; bNum < articleTable.getRowCount(); bNum++) {
					boughtTable.removeCell(bNum, 4);
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
				final int rowIndex = articleTable.getCellForEvent(event).getRowIndex();
				globalRow = rowIndex;
			}
		});
		cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue() == true) {
					if (editTb1.getText().isEmpty() == true && editTb2.getText().isEmpty() == true
							&& editTb3.getText().isEmpty() == true && editTb4.getText().isEmpty() == true) {
						finalGlobalRow = globalRow;
						first = articleTable.getText(globalRow, 0);
						second = articleTable.getText(globalRow, 1);
						third = articleTable.getText(globalRow, 2);
						fourth = articleTable.getText(globalRow, 3);
						editTb1.setText(articleTable.getText(globalRow, 0));
						editTb2.setText(articleTable.getText(globalRow, 1));
						editTb3.setText(articleTable.getText(globalRow, 2));
						editTb4.setText(articleTable.getText(globalRow, 3));
						articleTable.setWidget(globalRow, 0, editTb1);
						articleTable.setWidget(globalRow, 1, editTb2);
						articleTable.setWidget(globalRow, 2, editTb3);
						articleTable.setWidget(globalRow, 3, editTb4);
					} else if (editTb1.getText().isEmpty() == false && editTb2.getText().isEmpty() == false
							&& editTb3.getText().isEmpty() == false && editTb4.getText().isEmpty() == false) {
						articleTable.clearCell(finalGlobalRow, 0);
						articleTable.clearCell(finalGlobalRow, 1);
						articleTable.clearCell(finalGlobalRow, 2);
						articleTable.clearCell(finalGlobalRow, 3);
						articleTable.setText(finalGlobalRow, 0, editTb1.getText());
						articleTable.setText(finalGlobalRow, 1, editTb2.getText());
						articleTable.setText(finalGlobalRow, 2, editTb3.getText());
						articleTable.setText(finalGlobalRow, 3, editTb4.getText());
						
						tempArticle = null;
						
						article.setName(editTb1.getText());
						article.setQuantity(Integer.parseInt(editTb2.getText()));
						article.setUnit(editTb3.getText());
						article.setRetailerId(Integer.parseInt(editTb4.getText()));
						
						ev.saveArticle(article, new AsyncCallback<Article>() {
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
						first = articleTable.getText(globalRow, 0);
						second = articleTable.getText(globalRow, 1);
						third = articleTable.getText(globalRow, 2);
						fourth = articleTable.getText(globalRow, 3);
						articleTable.removeCell(finalGlobalRow, 4);
						articleTable.setWidget(finalGlobalRow, 4, getCbEdit());
						if (articleTable.getText(globalRow, 0).isEmpty() == false
								&& articleTable.getText(globalRow, 1).isEmpty() == false
								&& articleTable.getText(globalRow, 2).isEmpty() == false
								&& articleTable.getText(globalRow, 3).isEmpty() == false) {
							finalGlobalRow = globalRow;
							first = articleTable.getText(globalRow, 0);
							second = articleTable.getText(globalRow, 1);
							third = articleTable.getText(globalRow, 2);
							fourth = articleTable.getText(globalRow, 3);
							editTb1.setText(articleTable.getText(globalRow, 0));
							editTb2.setText(articleTable.getText(globalRow, 1));
							editTb3.setText(articleTable.getText(globalRow, 2));
							editTb4.setText(articleTable.getText(globalRow, 3));
							articleTable.setWidget(globalRow, 0, editTb1);
							articleTable.setWidget(globalRow, 1, editTb2);
							articleTable.setWidget(globalRow, 2, editTb3);
							articleTable.setWidget(globalRow, 3, editTb4);
						}
					} else {
						articleTable.clearCell(finalGlobalRow, 0);
						articleTable.clearCell(finalGlobalRow, 1);
						articleTable.clearCell(finalGlobalRow, 2);
						articleTable.clearCell(finalGlobalRow, 3);
						articleTable.removeCell(finalGlobalRow, 4);
						articleTable.setWidget(finalGlobalRow, 4, getCbEdit());
						articleTable.setText(finalGlobalRow, 0, first);
						articleTable.setText(finalGlobalRow, 1, second);
						articleTable.setText(finalGlobalRow, 2, third);
						articleTable.setText(finalGlobalRow, 3, fourth);
						first = articleTable.getText(globalRow, 0);
						second = articleTable.getText(globalRow, 1);
						third = articleTable.getText(globalRow, 2);
						fourth = articleTable.getText(globalRow, 3);
						editTb1.setText(null);
						editTb2.setText(null);
						editTb3.setText(null);
						editTb4.setText(null);
						articleTable.setWidget(finalGlobalRow, 4, getCbEdit());
						if (articleTable.getText(globalRow, 0).isEmpty() == false
								&& articleTable.getText(globalRow, 1).isEmpty() == false
								&& articleTable.getText(globalRow, 2).isEmpty() == false
								&& articleTable.getText(globalRow, 3).isEmpty() == false) {
							finalGlobalRow = globalRow;
							editTb1.setText(articleTable.getText(globalRow, 0));
							editTb2.setText(articleTable.getText(globalRow, 1));
							editTb3.setText(articleTable.getText(globalRow, 2));
							editTb4.setText(articleTable.getText(globalRow, 3));
							articleTable.setWidget(globalRow, 0, editTb1);
							articleTable.setWidget(globalRow, 1, editTb2);
							articleTable.setWidget(globalRow, 2, editTb3);
							articleTable.setWidget(globalRow, 3, editTb4);
						}
						Window.alert("Änderung wurde nicht gespeichert");
					}
				} else {
					if (editTb1.getText().isEmpty() == false && editTb2.getText().isEmpty() == false
							&& editTb3.getText().isEmpty() == false && editTb4.getText().isEmpty() == false) {
						articleTable.clearCell(globalRow, 0);
						articleTable.clearCell(globalRow, 1);
						articleTable.clearCell(globalRow, 2);
						articleTable.clearCell(globalRow, 3);
						articleTable.setText(globalRow, 0, editTb1.getText());
						articleTable.setText(globalRow, 1, editTb2.getText());
						articleTable.setText(globalRow, 2, editTb3.getText());
						articleTable.setText(globalRow, 3, editTb4.getText());
						
						article.setName(editTb1.getText());
						article.setQuantity(Integer.parseInt(editTb2.getText()));
						article.setUnit(editTb3.getText());
						article.setRetailerId(Integer.parseInt(editTb4.getText()));
						
						ev.saveArticle(article, new AsyncCallback<Article>() {
							public void onFailure(Throwable caught) {
							}

							public void onSuccess(Article arg0) {
								arg0 = article;
								loadTable();
							}
						});

						editTb1.setText(null);
						editTb2.setText(null);
						editTb3.setText(null);
						editTb4.setText(null);
					} else {
						articleTable.remove(editTb1);
						articleTable.remove(editTb2);
						articleTable.remove(editTb3);
						articleTable.remove(editTb4);
						articleTable.setText(globalRow, 0, first);
						articleTable.setText(globalRow, 1, second);
						articleTable.setText(globalRow, 2, third);
						articleTable.setText(globalRow, 3, fourth);
						editTb1.setText(null);
						editTb2.setText(null);
						editTb3.setText(null);
						editTb4.setText(null);
						articleTable.setWidget(globalRow, 4, getCbEdit());
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
				for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
					articleTable.setWidget(aNum, 4, getCbEdit());
				}
			} else if (editBtnBoolean == true) {
				editBtnBoolean = false;
				if (editTb1.getText().isEmpty() == false && editTb2.getText().isEmpty() == false
						&& editTb3.getText().isEmpty() == false && editTb4.getText().isEmpty() == false) {
					articleTable.clearCell(globalRow, 0);
					articleTable.clearCell(globalRow, 1);
					articleTable.clearCell(globalRow, 2);
					articleTable.clearCell(globalRow, 3);
					articleTable.setText(globalRow, 0, editTb1.getText());
					articleTable.setText(globalRow, 1, editTb2.getText());
					articleTable.setText(globalRow, 2, editTb3.getText());
					articleTable.setText(globalRow, 3, editTb4.getText());

					article.setName(editTb1.getText());
					article.setQuantity(Integer.parseInt(editTb2.getText()));
					article.setUnit(editTb3.getText());
					article.setRetailerId(Integer.parseInt(editTb4.getText()));
					 
					ev.saveArticle(article, new AsyncCallback<Article>() {
						public void onFailure(Throwable caught) {
						}

						public void onSuccess(Article arg0) {
							loadTable();
						}
					});

					editTb1.setText(null);
					editTb2.setText(null);
					editTb3.setText(null);
					editTb4.setText(null);
				} else if (globalRow != 0) {
					articleTable.remove(editTb1);
					articleTable.remove(editTb2);
					articleTable.remove(editTb3);
					articleTable.remove(editTb4);
					articleTable.setText(globalRow, 0, first);
					articleTable.setText(globalRow, 1, second);
					articleTable.setText(globalRow, 2, third);
					articleTable.setText(globalRow, 3, fourth);
					editTb1.setText(null);
					editTb2.setText(null);
					editTb3.setText(null);
					editTb4.setText(null);
					Window.alert("Änderung wurde nicht gespeichert");
				}
				for (int i = 1; i <= articleTable.getRowCount(); i++) {
					articleTable.removeCell(i, 4);
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
				final int rowIndex = articleTable.getCellForEvent(event).getRowIndex();
				ev.deleteArticle(article, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess(Void arg0) {
						loadTable();
						// FEHLT NOCH: Verarbeitung in der DB
					}
				});
				articleTable.removeRow(rowIndex);
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
				for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
					articleTable.setWidget(aNum, 4, getCbDel());
				}
			} else if (deleteBtnBoolean == true) {
				for (int i = 1; i < articleTable.getRowCount(); i++) {
					if (articleTable.getText(i, 4).isEmpty() == true) {
						articleTable.removeCell(i, 4);
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
				int i = articleTable.getRowCount();
				articleTable.setWidget(i, 0, editTb1);
				articleTable.setWidget(i, 1, editTb2);
				articleTable.setWidget(i, 2, editTb3);
				articleTable.setWidget(i, 3, editTb4);
			} else if (addBtnBoolean == true) {
				if (editTb1.getText().isEmpty() == false && editTb2.getText().isEmpty() == false
						&& editTb3.getText().isEmpty() == false && editTb4.getText().isEmpty() == false) {
					addBtnBoolean = false;
					int addRow = articleTable.getRowCount() - 1;
					articleTable.clearCell(addRow, 0);
					articleTable.clearCell(addRow, 1);
					articleTable.clearCell(addRow, 2);
					articleTable.clearCell(addRow, 3);
					articleTable.setText(addRow, 0, editTb1.getText());
					articleTable.setText(addRow, 1, editTb2.getText());
					articleTable.setText(addRow, 2, editTb3.getText());
					articleTable.setText(addRow, 3, editTb4.getText());
					// Artikel-Object muss schon erstellt sein, bevor es an die DB zur speicherung
					// weitergegeben wird
					article.setId(articleVec.size() + 1);
					article.setName(editTb1.getText());
					article.setQuantity(Integer.parseInt(articleTable.getText(addRow, 1)));
					article.setUnit(editTb3.getText());
					article.setRetailerId(Integer.parseInt(articleTable.getText(addRow, 3)));
					
					 ev.createArticle(article, new AsyncCallback<Article>() {
						public void onFailure(Throwable caught) {
							Window.alert("Artikel konnte nicht erstellt werden");
							// Article Object a existiert dennoch mit den zugewiesenen Attributen
						}

						public void onSuccess(Article arg0) {

							ev.addArticleToGroceryList(groceryList, article, new AsyncCallback<GroceryListArticle>() { 
								public void onFailure(Throwable caught) {
									// Artikel konnte nicht mit der GroceryList verbunden werden. Das Article-Objekt
									// wird jetzt auch aus der DB geloescht
									ev.deleteArticle(article, new AsyncCallback<Void>() {
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
									groceryListArticle = arg0;
									Window.alert("Artikel " + tempArticle.getName() + " wurde  der Einkaufsliste "
											+ groceryList.getGroceryListName() + " hinzugefügt");
									loadTable();								}
							});
							arg0 = article;
							loadTable();
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
					articleTable.removeRow(articleTable.getRowCount() - 1);
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