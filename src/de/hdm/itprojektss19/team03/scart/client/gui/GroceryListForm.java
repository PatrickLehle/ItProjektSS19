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
	Article a = new Article();
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

		
		/* Jetzt in loadTable()
		ev.findAllArticleByGroceryList(groceryListId, new AsyncCallback<Vector<Article>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Einkaufsliste konnte nicht geladen werden");
			}

			public void onSuccess(Vector<Article> arg0) {
				articleVec = arg0;
			}
		});
		*/

		aTable.setText(0, 0, "Artikel");
		aTable.setText(0, 1, "Menge");
		aTable.setText(0, 2, "Mengeneinheit");
		aTable.setText(0, 3, "Laden");
		bTable.setText(0, 0, "Gekauft");
		
		/* Jetzt in der loadTableMethod()
		int vecNum = 0;
		int trueCount = 1;
		int falseCount = 1;
		int visibleNum = 0;

		/**
		 * for Schleife das alle Artikel mit Name Quantity Unit und RetailerName
		 * aufgelistet werden im Panel.
		 */
		for (int aNum = 0; aNum <= articleVec.size(); aNum++) {
			// a ist ein einzelnes Article-Object
			if (articleVec.get(vecNum).getCheckBoolean() == false) {
				aTable.setText(falseCount, 0, articleVec.get(vecNum).getName());
				aTable.setText(falseCount, 1, Integer.toString(articleVec.get(vecNum).getQuantity()));
				aTable.setText(falseCount, 2, articleVec.get(vecNum).getUnit());
				aTable.setText(falseCount, 3, Integer.toString(articleVec.get(vecNum).getRetailerId()));
				falseCount++;
			} else {
				bTable.setText(trueCount, 0, articleVec.get(vecNum).getName());
				bTable.setText(trueCount, 1, Integer.toString(articleVec.get(vecNum).getQuantity()));
				bTable.setText(trueCount, 2, articleVec.get(vecNum).getUnit());
				bTable.setText(trueCount, 3, Integer.toString(articleVec.get(vecNum).getRetailerId()));
				trueCount++;
				visibleNum = trueCount;
			}
			vecNum++;
		}
		*/
		
		loadTable(); //Ruft Metode zum laden/fuellen der Tabelle auf
		
		vt.add(aTable);
		
		//if(visibleNum > 1) { bTable.setVisible(false);} jetzt in loadTable
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

		RootPanel.get("content").add(vt);
	}
	
	/* Metjode zum Laden der Tabelle bei erstem Aufruf oder zum Neu-laden bei einer Aktualisierung der Daten
	 * 
	 */
	public void loadTable() {
		
		int groceryListId = groceryList.getId();

		ev.findAllArticleByGroceryList(groceryListId, new AsyncCallback<Vector<Article>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Einkaufsliste konnte nicht geladen werden");
			}

			public void onSuccess(Vector<Article> arg0) {
				articleVec = arg0;
				
				int vecNum = 0;
				int trueCount = 1;
				int falseCount = 1;
				int visibleNum = 0;

				// for Schleife das alle Artikel mit Name Quantity Unit und RetailerName
				// aufgelistet werden im Panel.
				for (int aNum = 0; aNum <= articleVec.size(); aNum++) {
					
					  if (articleVec.get(vecNum).getCheckBoolean() == false) { 
						  aTable.setText(falseCount, 0, articleVec.get(vecNum).getName());
						  aTable.setText(falseCount, 1, Integer.toString(articleVec.get(vecNum).getQuantity()));
						  aTable.setText(falseCount, 2, articleVec.get(vecNum).getUnit());
						  aTable.setText(falseCount, 3, Integer.toString(articleVec.get(vecNum).getRetailerId())); 
						  falseCount++;
					 	} else {
						  bTable.setText(trueCount, 0, articleVec.get(vecNum).getName()); 
						  bTable.setText(trueCount, 1, Integer.toString(articleVec.get(vecNum).getQuantity())); 
						  bTable.setText(trueCount, 2, articleVec.get(vecNum).getUnit()); 
						  bTable.setText(trueCount, 3, Integer.toString(articleVec.get(vecNum).getRetailerId())); 
						  trueCount++;
						  visibleNum = trueCount; 
					 	}
					  vecNum++;
				}
				if(visibleNum > 1) { bTable.setVisible(false);}
			}
		});
		
		
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
				int rowIndex = aTable.getCellForEvent(event).getRowIndex();
				int rowCount = bTable.getRowCount();
				
				/* Auskommentiert, da diese Aenderungen von der erfolgreichen DB-Connection abhaengt. Aenderungen werden in der onSuccess-Methode nun verarbeitet
				bTable.setText(rowCount, 0, aTable.getText(rowIndex, 0));
				bTable.setText(rowCount, 1, aTable.getText(rowIndex, 1));
				bTable.setText(rowCount, 2, aTable.getText(rowIndex, 2));
				bTable.setText(rowCount, 3, aTable.getText(rowIndex, 3));
				bTable.setWidget(rowCount, 4, getCbReturn());
				*/
				
				/* Auskommentiert, da dies erst in der onSuccess-Methode behandelt werden sollte
				if (bTable.getRowCount() >= 2) {
					bTable.setVisible(true);
				}
				*/
				
				a.setName(aTable.getText(rowIndex, 0));
				a.setQuantity(Integer.parseInt(aTable.getText(rowIndex, 1)));
				a.setUnit(aTable.getText(rowIndex, 2));
				a.setRetailerId(Integer.parseInt(aTable.getText(rowIndex, 3)));
				a.setCheckBoolean(true);
				
				ev.saveArticle(a, new AsyncCallback<Article>() {
					public void onFailure(Throwable caught) {
						Window.alert("Artikel konnte nicht hinzugefügter werden");
						a = null; //Article-Objekt wird geleert, da Artikel nicht hinzugefuegt werdn konnte
					}

					public void onSuccess(Article arg0) {
						bTable.setText(rowCount, 0, arg0.getName());
						bTable.setText(rowCount,1, Integer.toString(arg0.getQuantity()));
						bTable.setText(rowCount, 2, arg0.getUnit());
						bTable.setText(rowCount, 3, Integer.toString(arg0.getRetailerId()));
						bTable.setWidget(rowCount, 4, getCbReturn());
						aTable.removeRow(rowIndex);
						
						if (bTable.getRowCount() >= 2) {
							bTable.setVisible(true);
						}
						
						a = null; //Artikle-Objekt wurde geleert, da Artikel erfolgreich in der DB angelegt werden konnte
						
						//Vektor der alle Artikel speichert muss aktualisiert werden
						//Neu Laden der gesamten Tabelle ? (notwendig)
					}
				});
				//aTable.removeRow(rowIndex); auskommentiert, da Zeile nur entfernt werden sollte, wenn der Artikel auch in der DB als gekauft markiert werden konnte
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
				int rowIndex = bTable.getCellForEvent(event).getRowIndex();
				int rowCount = aTable.getRowCount();
				
				/* Aenderungen werden nun in der onSuccess-Methode verarbeitet
				aTable.setText(rowCount, 0, bTable.getText(rowIndex, 0));
				aTable.setText(rowCount, 1, bTable.getText(rowIndex, 1));
				aTable.setText(rowCount, 2, bTable.getText(rowIndex, 2));
				aTable.setText(rowCount, 3, bTable.getText(rowIndex, 3));
				aTable.setWidget(rowCount, 4, getCbCheck());
				bTable.removeRow(rowIndex);
				*/
				
				
				/* Auskommentiert, da dies erst in der onSuccess-Methode behandelt werden sollte
				if (bTable.getRowCount() < 2) {
					bTable.setVisible(false);
				}
				*/
				
				a.setName(bTable.getText(rowIndex, 0));
				a.setQuantity(Integer.parseInt(bTable.getText(rowIndex, 1)));
				a.setUnit(bTable.getText(rowIndex, 2));
				a.setRetailerId(Integer.parseInt(bTable.getText(rowIndex, 3)));
				a.setCheckBoolean(false);
				
				ev.saveArticle(a, new AsyncCallback<Article>() {
					public void onFailure(Throwable caught) {
						Window.alert("Artikel konnte nicht von der gekauft Tabelle entfernt werden");
						a = null;
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
						
						a = null;
						
						//Vektor der alle Artikel speichert muss aktualisiert werden
						//Neu Laden der gesamten Tabelle ? (notwendig)
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
				int rowIndex = aTable.getCellForEvent(event).getRowIndex();
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

						ev.saveArticle(a, new AsyncCallback<Article>() {
							public void onFailure(Throwable caught) {
							}

							public void onSuccess(Article arg0) {
								
								/* FEHLT NOCH: arg0. sollte nicht mehr lokal veraendert werden, da dies das Artikel-Objekt aus der DB ist
								arg0.setName(editTb1.getText());
								arg0.setQuantity(Integer.parseInt(editTb2.getText()));
								arg0.setUnit(editTb3.getText());
								arg0.setRetailerId(Integer.parseInt(editTb4.getText()));

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
						Window.alert("�nderung wurde nicht gespeichert");
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

						ev.saveArticle(a, new AsyncCallback<Article>() {
							public void onFailure(Throwable caught) {
							}

							public void onSuccess(Article arg0) {
								/* FEHLT NOCH: arg0. sollte nicht mehr lokal veraendert werden, da dies das Artikel-Objekt aus der DB ist
								arg0.setName(editTb1.getText());
								arg0.setQuantity(Integer.parseInt(editTb2.getText()));
								arg0.setUnit(editTb3.getText());
								arg0.setRetailerId(Integer.parseInt(editTb4.getText()));
								*/
								
								//FEHLT NOCH: Aktualisieren des Artikel-Vektors und erneuern der gesamten Tabelle
								
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
						Window.alert("�nderung wurde nicht gespeichert");
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

					ev.saveArticle(a, new AsyncCallback<Article>() {
						public void onFailure(Throwable caught) {
						}

						public void onSuccess(Article arg0) {
							/* FEHLT NOCH: arg0. sollte nicht mehr lokal veraendert werden, da dies das Artikel-Objekt aus der DB ist
							arg0.setName(editTb1.getText());
							arg0.setQuantity(Integer.parseInt(editTb2.getText()));
							arg0.setUnit(editTb3.getText());
							arg0.setRetailerId(Integer.parseInt(editTb4.getText()));
							*/
							
							//FEHLT NOCH: Aktualisieren des Artikel-Vektors und erneuern der gesamten Tabelle
							
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
					Window.alert("�nderung wurde nicht gespeichert");
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
	 * CheckBox fuer DelteButton.
	 * Loescht die ausgewaehlte Reihe aus der Tabele.
	 */
	public CheckBox getCbDel() {
		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int rowIndex = aTable.getCellForEvent(event).getRowIndex();
				ev.deleteArticle(a, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess(Void arg0) {
						//FEHLT NOCH: Verarbeitung in der DB
						
						//FEHLT NOCH: Aktualisieren des Artikel-Vektors und erneuern der gesamten Tabelle
						
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
	 *         false und schaut ob andere Buttons aktiv sind
	 *         Ausgewaehlte Reihe wird aus der Tabele gel�scht.
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

					ev.createArticle(a, new AsyncCallback<Article>() {
						public void onFailure(Throwable caught) {
							Window.alert("Artikel konnte nicht erstellt werden");
							// Article Object a existiert dennoch mit den zugewiesenen Attributen
						}

						public void onSuccess(Article arg0) {
							a = arg0;

							ev.addArticleToGroceryList(groceryList, a, new AsyncCallback<GroceryListArticle>() { 
								public void onFailure(Throwable caught) {
									// Artikel konnte nicht mit der GroceryList verbunden werden. Das Article-Objekt
									// wird jetzt auch aus der DB geloescht
									ev.deleteArticle(a, new AsyncCallback<Void>() {
										public void onFailure(Throwable caught) {
											Window.alert(
													"Artikel konnte nicht mit der GroceryListe verbunden werden. Außerdem konnte der fehlerhafte Artikel konnte nicht gelöscht werden");
										}

										@Override
										public void onSuccess(Void arg0) {

											Window.alert("Artikel konnte nicht mit Einkaufsliste verbunden werden");
										}
									});
								}

								public void onSuccess(GroceryListArticle arg0) {
									aGl = arg0;
									Window.alert("Artikel " + a.getName() + " wurde  der Einkaufsliste "
											+ groceryList.getGroceryListName() + " hinzugefügt");
									// FEHLT NOCH: Tabelle muss neu geladen werden
								}
							});

							arg0.setId(articleVec.size() + 1);
							arg0.setName(editTb1.getText());
							arg0.setQuantity(Integer.parseInt(aTable.getText(addRow, 1)));
							arg0.setUnit(editTb3.getText());
							arg0.setRetailerId(Integer.parseInt(aTable.getText(addRow, 3)));
							
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