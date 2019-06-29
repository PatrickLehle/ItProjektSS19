package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.client.gui.ReportFilterForm.AllGroupsCallback;
import de.hdm.itprojektss19.team03.scart.client.gui.ReportFilterForm.AllRetailersCallback;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryListArticle;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroupUser;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

// TODO Change Constructor add GroceryList
//		Zwei mal Klicken auf einmal aendern

/**
 * 
 * @author tom
 * @author bastiantilk
 *
 */
public class GroceryListForm extends VerticalPanel {

	public GroceryListForm(User u, Group g, Vector<Article> a, Retailer r) {
		this.user = u;
		this.group = g;
		this.retailer = r;
		this.articleVector = a;
	};

	private User user;
	private Group group;
	private Retailer retailer;

	private EditorServiceAsync ev = ClientsideSettings.getEditor();

	HorizontalPanel hpTitle = new HorizontalPanel();
	HorizontalPanel hpUserRetailer = new HorizontalPanel();
	HorizontalPanel hpButtons = new HorizontalPanel();

	Button addBtn = new Button("<image src='/images/plusButton.png' width='16px' height='16px' align='right'/>");
	Button editBtn = new Button("<image src='/images/editButton.png' width='16px' height='16px' align='center'/>");
	Button deleteBtn = new Button("<image src='/images/minusButton.png' width='16px' height='16px' align='left'/>");
	Button checkBtn = new Button();
	Button refreshBtn = new Button();

	Boolean addBtnBoolean = false;
	Boolean editBtnBoolean = false;
	Boolean deleteBtnBoolean = false;
	Boolean checkBtnBoolean = false;
	TextBox articleTextBox = new TextBox(); // Artikel
	TextBox quantityTextBox = new TextBox(); // Menge
	TextBox unitTextBox = new TextBox(); // Mengeneinheit
	ListBox retailerListBox = new ListBox();
	ListBox userListBox = new ListBox();

	FlexTable articleTable = new FlexTable();
	FlexTable boughtTable = new FlexTable();
	Article article = new Article(); // temporaerer Artikel wenn ein Artikel
	// geupdated/neu erstellt wird, um dieses Objekt auch an die DB zu pushen

	Vector<Article> articleVector;
	Vector<Retailer> retailerVector = new Vector<Retailer>();
	Vector<GroupUser> groupUserVector = new Vector<GroupUser>();
	Vector<User> userVector = new Vector<User>();

	Label userRetailerLabel = new Label("zuteilen f\u00fcr");

	GroceryList groceryList = new GroceryList(); // Muss bei dem Aufruf der GUI-Seite uebergeben werden
	GroceryListArticle groceryListArticle = new GroceryListArticle();

	int trueCount = 1;
	int falseCount = 1;
	int rowIndex;
	int rowCount;
	int rowIndexA;
	int rowIndexB;
	int articleNumber;

	// Wird bei dem Aufruf der Klasse/des Widgets automatisch ausgefuehrt
	public void onLoad() {
		super.onLoad();
		this.addStyleName("main-panel");
		// CHANGE
		//groceryList.setGroupId(this.group.getId());

		// CALLBACKS=============================================
		// ev.getAllUserByGroupId(1, new AllUserCallback());
		// ev.findAllRetailer(new AllRetailersCallback());
		hpUserRetailer.add(userListBox);
		hpUserRetailer.add(userRetailerLabel);
		hpUserRetailer.add(retailerListBox);
		this.add(hpUserRetailer);

		// zum laden/fuellen der Tabelle auf
		boughtTable.setVisible(false);
		loadTable();
		Timer refresh = new Timer() {
			public void run() {
				if (checkBtnBoolean == false && editBtnBoolean == false && deleteBtnBoolean == false
					&& addBtnBoolean == false) {
					refreshTable();
				}
		}};
		refresh.scheduleRepeating(1000);
		this.add(articleTable);
		this.add(boughtTable);

		// add stylenames
		addBtn.setStyleName("button");
		editBtn.setStyleName("button");
		deleteBtn.setStyleName("button");
		checkBtn.setStyleName("button");
		articleTextBox.setStyleName("textbox");
		quantityTextBox.setStyleName("textbox");
		unitTextBox.setStyleName("textbox");

		// Buttons werden dem untersten horizontal Panel hinzugefuegt
		hpButtons.add(addBtn);
		hpButtons.add(editBtn);
		hpButtons.add(deleteBtn);
		hpButtons.add(checkBtn);
		hpButtons.add(refreshBtn);

//		addBtn.addClickHandler(new AddClickHandler());
//		editBtn.addClickHandler(new EditClickHandler());
//		deleteBtn.addClickHandler(new DeleteClickHandler());
		checkBtn.addClickHandler(new CheckClickHandler());
		refreshBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				refreshTable();
			}
		});

		quantityTextBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				quantityTextBox.addChangeHandler(new ChangeHandler() {
					public void onChange(ChangeEvent event) {
						String input = quantityTextBox.getText();
						if (!input.matches("[0-9]*")) {
							quantityTextBox.setText(null);
							quantityTextBox.addStyleName("wrong-input");
							Window.alert("TO-DO CSS CLASS worng-input");
							return;
						} else {
							quantityTextBox.removeStyleName("wrong-input");
						}
					}
				});
			}
		});

		this.add(hpButtons);
	}

	public void refreshTable() {
		ev.findAllArticleByGroceryListId(1, new setArticleVectorCallback());
		loadTable();
	}
		
	/**
	 * @author tom
	 * @author bastiantilk
	 * 
	 *         Methode zum Laden der Tabelle bei erstem Aufruf oder zum Neu-laden
	 *         bei einer Aktualisierung der Daten. Es werden alle Artikel der
	 *         aktuellen GroceryList aus der DB abgefragt. Dann wird die Tabelle
	 *         geleert und mit dem Vektor der Artikel-Objekte gefuellt.
	 */
	public void loadTable() {

		// Loescht alle Zeilen der FlexTables
		articleTable.removeAllRows();
		boughtTable.removeAllRows();

		// Header der Flextable werden gesetzt
		articleTable.setText(0, 1, "Artikel");
		articleTable.setText(0, 2, "Menge");
		articleTable.setText(0, 3, "Mengeneinheit");
		articleTable.setText(0, 4, "Laden");
		boughtTable.setText(0, 1, "Gekauft");
		
		trueCount = 1;
		falseCount = 1;
		int visibleNum = 0;
		int retailerId = 1;
		// for Schleife das alle Artikel mit Name Quantity Unit und RetailerName
		// aufgelistet werden im Panel.
		for (articleNumber = 0; articleNumber < articleVector.size(); articleNumber++) {
			if (articleVector.get(articleNumber).getDelDat() == null && articleVector.get(articleNumber).getCheckBoolean() == false) {
				articleTable.setText(falseCount, 0, Integer.toString(articleVector.get(articleNumber).getId()));
				articleTable.setText(falseCount, 1, articleVector.get(articleNumber).getName());
				articleTable.setText(falseCount, 2, Integer.toString(articleVector.get(articleNumber).getQuantity()));
				articleTable.setText(falseCount, 3, articleVector.get(articleNumber).getUnit());
				articleTable.setText(falseCount, 4, articleVector.get(articleNumber).getRetailerName());
				setFavButtonFalse();
				falseCount++;
			} else if (articleVector.get(articleNumber).getDelDat() != null && articleVector.get(articleNumber).getCheckBoolean() == false) {
				boughtTable.setText(trueCount, 0, Integer.toString(articleVector.get(articleNumber).getId()));
				boughtTable.setText(trueCount, 1, articleVector.get(articleNumber).getName());
				boughtTable.setText(trueCount, 2, Integer.toString(articleVector.get(articleNumber).getQuantity()));
				boughtTable.setText(trueCount, 3, articleVector.get(articleNumber).getUnit());
				boughtTable.setText(trueCount, 4, articleVector.get(articleNumber).getRetailerName());
				setFavButtonTrue();
				trueCount++;
				visibleNum = trueCount;
			}
		}
		if (visibleNum > 1) {
			boughtTable.setVisible(true);
		}
	}

	public void setFavButtonFalse() {
		if (articleVector.get(articleNumber).getFav() == true) {
			articleTable.setWidget(falseCount, 6, getFavButtonTrueArticle());
		} else {
			articleTable.setWidget(falseCount, 6, getFavButtonFalseArticle());
		}
	}

	public void setFavButtonTrue() {
		if (articleVector.get(articleNumber).getFav() == true) {
			boughtTable.setWidget(trueCount, 6, getFavButtonTrueBought());
		} else {
			boughtTable.setWidget(trueCount, 6, getFavButtonFalseBought());
		}
	}
//
//	public ListBox getRetailerListBox() {
//		ev.getAllRetailerByGroupId(1, new AllRetailersCallback());
//		ev.getArticleByArticleId(globalRow, new GetArticleCallback());
//		ev.getRetailerById(article.getRetailerId(), new GetRetailerCallback());
//		retailerListBox.setItemSelected(retailer.getId() - 1, true);
//		return retailerListBox;
//	}
//
//	/**
//	 * @param node
//	 * @return https://stackoverflow.com/questions/11415652/remove-row-from-flextable-in-gwt
//	 *         Liest aus, welche Reihe ausgewaehlt wurde durch einen ClickHandler.
//	 */
//	public static TableRowElement findNearestParentRow(Node node) {
//		Node element = findNearestParentNodeByType(node, "tr");
//		if (element != null) {
//			return element.cast();
//		}
//		return null;
//	}
//
//	public static Node findNearestParentNodeByType(Node node, String nodeType) {
//		while ((node != null)) {
//			if (Element.is(node)) {
//				Element elem = (Element) Element.as(node);
//
//				String tagName = elem.getTagName();
//
//				if (nodeType.equalsIgnoreCase(tagName)) {
//					return elem.cast();
//				}
//			}
//			node = node.getParentNode();
//		}
//		return null;
//	}
//
	public Button getFavButtonFalseArticle() {
		Button favBtnFalse = new Button("NOT ATM");

		favBtnFalse.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rowIndexA = articleTable.getCellForEvent(event).getRowIndex();
				getArticleForTable();

				if (article.getId() != 0) {
					article.setFav(true);
					ev.saveArticle(article, new SaveArticleCallback());
				}
			}
		});

		return favBtnFalse;
	}
	
	public Button getFavButtonFalseBought() {
		Button favBtnFalse = new Button("NOT ATM");

		favBtnFalse.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rowIndexB = boughtTable.getCellForEvent(event).getRowIndex();
				getArticleForTable();

				if (article.getId() != 0) {
					article.setFav(true);
					ev.saveArticle(article, new SaveArticleCallback());
				}
			}
		});

		return favBtnFalse;
	}
	
	public Button getFavButtonTrueArticle() {
		Button favBtnTrue = new Button("FAVORITE TRUE");

		favBtnTrue.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rowIndexA = articleTable.getCellForEvent(event).getRowIndex();
				getArticleForTable();
			
				if (article.getId() != 0) {
					article.setFav(false);
					ev.saveArticle(article, new SaveArticleCallback());
				}
			}
		});

		return favBtnTrue;
	}
	
	public Button getFavButtonTrueBought() {
		Button favBtnTrue = new Button("FAVORITE TRUE");
		
		favBtnTrue.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rowIndexB = boughtTable.getCellForEvent(event).getRowIndex();
				getArticleForTable();
				
				if (article.getId() != 0) {
					article.setFav(false);
					ev.saveArticle(article, new SaveArticleCallback());
				}
			}
		});
		
		return favBtnTrue;
	}
	
	public void getArticleForTable() {
		if (rowIndexA != 0) {
			ev.getArticleByArticleId(Integer.parseInt(articleTable.getText(rowIndexA, 0)), new GetArticleCallback());
		} else if (rowIndexB != 0) {
			ev.getArticleByArticleId(Integer.parseInt(boughtTable.getText(rowIndexA, 0)), new GetArticleCallback());
		} else {
			Window.alert("getFavButtonTrue Error");
		}

		rowIndexA = 0;
		rowIndexB = 0;
	}
//
//	/**
//	 * @author tom
//	 * 
//	 *         getCbCheck: CheckBox und ClickHandler die bei dem kaufen eines
//	 *         Artikels aufgerufen werden. CheckBox wird im letzem Column generiert
//	 *         Ausgewaehlte Reihe wird geloescht und in die zweite Tabelle kopiert
//	 *         und eine neue CheckBox wird kreiert getCbReturn.
//	 * 
//	 */
	public CheckBox getCbCheck() {
		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rowIndex = articleTable.getCellForEvent(event).getRowIndex();
				rowCount = boughtTable.getRowCount();
				article = null;
				
				article.setId(Integer.parseInt(articleTable.getText(rowIndex, 0)));
				article.setName(articleTable.getText(rowIndex, 1));
				article.setQuantity(Integer.parseInt(articleTable.getText(rowIndex, 2)));
				article.setQuantity(10);
				article.setUnit(articleTable.getText(rowIndex, 3));
				article.setUnit("kg");
				article.setRetailerId(1);
				if(article.getId() != 0) {
					ev.deleteArticle(article, new SetCheckCallback());
				}
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
				rowIndex = boughtTable.getCellForEvent(event).getRowIndex();
				rowCount = articleTable.getRowCount();
				article = null;

				article.setId(Integer.parseInt(boughtTable.getText(rowIndex, 0)));
				article.setName(boughtTable.getText(rowIndex, 1));
				article.setQuantity(Integer.parseInt(boughtTable.getText(rowIndex, 2)));
				article.setQuantity(Integer.parseInt(boughtTable.getText(rowIndex, 3)));
				article.setUnit(boughtTable.getText(rowIndex, 4));
				article.setRetailerId(1);
				ev.deleteArticle(article, new SetCheckCallback());
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
	 *         und loescht CheckBoxen aus der dem letzten Column.
	 *
	 */
	public class CheckClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (checkBtnBoolean == false && editBtnBoolean == false && deleteBtnBoolean == false
					&& addBtnBoolean == false) {
				checkBtnBoolean = true;
				for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
					articleTable.setWidget(aNum, 5, getCbCheck());
				}
				for (int bNum = 1; bNum < boughtTable.getRowCount(); bNum++) {
					boughtTable.setWidget(bNum, 5, getCbReturn());
				}
			} else if (checkBtnBoolean == true) {
				checkBtnBoolean = false;
				refreshTable();
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
//
//	// alle Methoden fuer Editieren eines Artikels
//	public void saveRowContent() {
//		first = articleTable.getText(globalRow, 1);
//		second = articleTable.getText(globalRow, 2);
//		third = articleTable.getText(globalRow, 3);
//	}
//
//	public void setTextBoxesContent() {
//		articleTextBox.setText(articleTable.getText(globalRow, 1));
//		quantityTextBox.setText(articleTable.getText(globalRow, 2));
//		unitTextBox.setText(articleTable.getText(globalRow, 3));
//		articleTable.setWidget(globalRow, 1, articleTextBox);
//		articleTable.setWidget(globalRow, 2, quantityTextBox);
//		articleTable.setWidget(globalRow, 3, unitTextBox);
//		articleTable.setWidget(globalRow, 4, getRetailerListBox());
//	}
//
//	public void clearTextBoxes() {
//		articleTextBox.setText(null);
//		quantityTextBox.setText(null);
//		unitTextBox.setText(null);
//	}
//
//	public void setTableTextFromTextBoxes() {
//		articleTable.setText(globalRow, 1, articleTextBox.getText());
//		articleTable.setText(globalRow, 2, quantityTextBox.getText());
//		articleTable.setText(globalRow, 3, unitTextBox.getText());
//		// articleTable.setText(globalRow, 4, getRetailerListBoxDisabled());
//	}
//
//	public void setTableTextFromFinalTextBox() {
//		articleTable.setText(finalGlobalRow, 1, articleTextBox.getText());
//		articleTable.setText(finalGlobalRow, 2, quantityTextBox.getText());
//		articleTable.setText(finalGlobalRow, 3, unitTextBox.getText());
//		// articleTable.setWidget(finalGlobalRow, 4, getRetailerListBoxDisabled());
//	}
//
//	public void replaceUnchangedText() {
//		articleTable.setText(globalRow, 1, first);
//		articleTable.setText(globalRow, 2, second);
//		articleTable.setText(globalRow, 3, third);
//		// articleTable.setWidget(globalRow, 4, getRetailerListBoxDisabled());
//	}
//
//	public String textBoxesEmpty() {
//		if (articleTextBox.getText().isEmpty() == true && quantityTextBox.getText().isEmpty() == true
//				&& unitTextBox.getText().isEmpty() == true) {
//			return "true";
//		} else if (articleTextBox.getText().isEmpty() == false && quantityTextBox.getText().isEmpty() == false
//				&& unitTextBox.getText().isEmpty() == false) {
//			return "false";
//		} else {
//			return "else";
//		}
//	}
//
//	public void setArticleAtributes() {
//		article.setName(articleTextBox.getText());
//		article.setQuantity(Integer.parseInt(quantityTextBox.getText()));
//		article.setUnit(unitTextBox.getText());
//		article.setRetailerId(getRetailerListBox().getSelectedIndex());
//	}
//
//	public void saveChangedOnDb() {
//		ev.saveArticle(article, new AsyncCallback<Article>() {
//			public void onFailure(Throwable caught) {
//				Window.alert("Artikel konnte nicht editiert werden");
//			}
//
//			public void onSuccess(Article arg0) {
//				article = null;
//				//refreshTable();
//			}
//		});
//	}
//
//	/**
//	 * @author tom
//	 * 
//	 *         CheckBox fuer Editieren ClickHandler um die ausgewaehlte Reihe global
//	 *         zu speichern ValueChangeHandler um den Text in TextBoxen zu
//	 *         uebertragen und den Text aus TextBoxen zu speichern.
//	 */
//	public CheckBox getCbEdit() {
//		CheckBox cb = new CheckBox();
//		cb.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				final int rowIndex = articleTable.getCellForEvent(event).getRowIndex();
//				globalRow = rowIndex;
//			}
//		});
//		cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
//			public void onValueChange(ValueChangeEvent<Boolean> event) {
//				if (event.getValue() == true) {
//					if (textBoxesEmpty() == "true") {
//						finalGlobalRow = globalRow;
//						saveRowContent();
//						setTextBoxesContent();
//					} else if (textBoxesEmpty() == "false") {
//						setTableTextFromFinalTextBox();
//						setArticleAtributes();
//						saveChangedOnDb();
//						clearTextBoxes();
//						articleTable.removeCell(finalGlobalRow, 5);
//						articleTable.setWidget(finalGlobalRow, 5, getCbEdit());
//						if (articleTable.getText(globalRow, 1).isEmpty() == false
//								&& articleTable.getText(globalRow, 2).isEmpty() == false
//								&& articleTable.getText(globalRow, 3).isEmpty() == false
//								&& articleTable.getText(globalRow, 4).isEmpty() == false) {
//							finalGlobalRow = globalRow;
//							saveRowContent();
//							setTextBoxesContent();
//						}
//					} else {
//						articleTable.removeCell(finalGlobalRow, 5);
//						articleTable.setWidget(finalGlobalRow, 5, getCbEdit());
//						articleTable.setText(finalGlobalRow, 1, first);
//						articleTable.setText(finalGlobalRow, 2, second);
//						articleTable.setText(finalGlobalRow, 3, third);
//						saveRowContent();
//						clearTextBoxes();
//						articleTable.setWidget(finalGlobalRow, 5, getCbEdit());
//						if (articleTable.getText(globalRow, 1).isEmpty() == false
//								&& articleTable.getText(globalRow, 2).isEmpty() == false
//								&& articleTable.getText(globalRow, 3).isEmpty() == false) {
//							finalGlobalRow = globalRow;
//							setTextBoxesContent();
//						}
//						Window.alert("Änderung wurde nicht gespeichert");
//					}
//				} else {
//					if (textBoxesEmpty() == "false") {
//						setTableTextFromTextBoxes();
//						setArticleAtributes();
//						saveChangedOnDb();
//						clearTextBoxes();
//						globalRow = 0;
//					} else {
//						replaceUnchangedText();
//						clearTextBoxes();
//						articleTable.setWidget(globalRow, 5, getCbEdit());
//						Window.alert("Änderung wurde nicht gespeichert");
//						globalRow = 0;
//					}
//				}
//			}
//		});
//		cb.setValue(false);
//		return cb;
//	}
//
//	/**
//	 * @author tom
//	 *
//	 *         ClickHandler fuer EditButton setzt Boolean des Buttons true und false
//	 *         und schaut ob andere Buttons aktiv sind.
//	 */
//	public class EditClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			if (editBtnBoolean == false && checkBtnBoolean == false && deleteBtnBoolean == false
//					&& addBtnBoolean == false) {
//				editBtnBoolean = true;
//				globalRow = 0;
//				for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
//					articleTable.setWidget(aNum, 5, getCbEdit());
//				}
//			} else if (editBtnBoolean == true) {
//				editBtnBoolean = false;
//				if (textBoxesEmpty() == "false") {
//					setTableTextFromTextBoxes();
//					setArticleAtributes();
//					saveChangedOnDb();
//					clearTextBoxes();
//				} else if (globalRow != 0) {
//					replaceUnchangedText();
//					clearTextBoxes();
//					Window.alert("Änderung wurde nicht gespeichert");
//				}
//				//refreshTable();
//			} else if (checkBtnBoolean == false || deleteBtnBoolean == false || addBtnBoolean == false) {
//				Window.alert("Bitte anderen Button deaktivieren.");
//			} else {
//				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
//			}
//		}
//	}
//
//	/**
//	 * @author tom
//	 * 
//	 *         CheckBox fuer DeleteButton. Loescht die ausgewaehlte Reihe aus der
//	 *         Tabele.
//	 */
//	public CheckBox getCbDel() {
//		CheckBox cb = new CheckBox();
//		cb.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				globalRow = articleTable.getCellForEvent(event).getRowIndex();
//				//ev.deleteArticle(article, new DeleteArticleCallBack());
//				// CHANGE Loescht nicht das zuletzt gewaehlte Objekt
//			}
//		});
//		cb.setValue(false);
//		return cb;
//	}
//
//	/**
//	 * @author tom
//	 *
//	 *         ClickHandler fuer DeleteButton setzt Boolean des Buttons true und
//	 *         false und schaut ob andere Buttons aktiv sind Ausgewaehlte Reihe wird
//	 *         aus der Tabele gel�scht.
//	 */
//	public class DeleteClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent e) {
//			if (deleteBtnBoolean == false && checkBtnBoolean == false && editBtnBoolean == false
//					&& addBtnBoolean == false) {
//				deleteBtnBoolean = true;
//				for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
//					articleTable.setWidget(aNum, 5, getCbDel());
//				}
//			} else if (deleteBtnBoolean == true) {
////				for (int i = 1; i < articleTable.getRowCount(); i++) {
////					if (articleTable.getText(i, 5).isEmpty() == true) {
////						articleTable.removeCell(i, 5);
////					}
////				}
//				//refreshTable();
//				deleteBtnBoolean = false;
//			} else if (checkBtnBoolean == true || editBtnBoolean == true || addBtnBoolean == false) {
//				Window.alert("Bitte anderen Button deaktivieren.");
//			} else {
//				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
//			}
//		}
//	}
//
//	/**
//	 * @author tom
//	 *
//	 *         ClickHandler fuer EditButton setzt Boolean des Buttons true und false
//	 *         und schaut ob andere Buttons aktiv sind. Fuegt eine neue Column mit
//	 *         TextBoxen hinzu in der man einen Neuen Artikel mit allen Atributen
//	 *         anlegen kann.
//	 */
//	public class AddClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent e) {
//
//			if (deleteBtnBoolean == false && checkBtnBoolean == false && editBtnBoolean == false
//					&& addBtnBoolean == false) {
//				addBtnBoolean = true;
//				int i = articleTable.getRowCount();
//				articleTable.setWidget(i, 1, articleTextBox);
//				articleTable.setWidget(i, 2, quantityTextBox);
//				articleTable.setWidget(i, 3, unitTextBox);
//				articleTable.setWidget(i, 4, retailerListBox);
//			} else if (addBtnBoolean == true) {
//				if (textBoxesEmpty() == "false") {
//					article.setId(articleVector.size() + 1);
//					article.setName(articleTextBox.getText());
//					article.setQuantity(Integer.parseInt(quantityTextBox.getText()));
//					article.setUnit(unitTextBox.getText());
//					// article.setRetailerId(getRetailerListBox().getSelectedIndex());
//					article.setRetailerId(4);
//					article.setOwnerId(1);
//					article.setCheckBoolean(false);
//					ev.createArticle(article, new AddArticleCallback());
//					groceryList.setId(1);
//					// ev.addArticleToGroceryList(groceryList, article, new
//					// AddArticleToGlCallback());
//					addBtnBoolean = false;
//				} else if (articleTextBox.getText().isEmpty() == false || quantityTextBox.getText().isEmpty() == false
//						|| unitTextBox.getText().isEmpty() == false) {
//					Window.alert("Es wurde kein Artikel hinzugef�gt da die Angaben nicht vollst�ndig waren");
//				} else {
//					addBtnBoolean = false;
//					articleTable.removeRow(articleTable.getRowCount() - 1);
//					articleTextBox.setText(null);
//					quantityTextBox.setText(null);
//					unitTextBox.setText(null);
//					//refreshTable();
//				}
//			} else if (checkBtnBoolean == true || editBtnBoolean == true || deleteBtnBoolean == false) {
//				Window.alert("Bitte anderen Button deaktivieren.");
//			} else {
//				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
//			}
//		}
//	}
//
//	// CALLBACKS===============================================================================================
//	class AllRetailersCallback implements AsyncCallback<Vector<Retailer>> {
//
//		public void onFailure(Throwable caught) {
//		}
//
//		public void onSuccess(Vector<Retailer> result) {
//			retailerVector = result;
//			for (int retailerNumber = 0; retailerNumber < retailerVector.size(); retailerNumber++) {
//				retailerListBox.addItem(retailerVector.get(retailerNumber).getRetailerName());
//			}
//			retailerListBox.setVisibleItemCount(1);
//		}
//	}
//
//	class AllUserCallback implements AsyncCallback<Vector<User>> {
//
//		public void onFailure(Throwable caught) {
//		}
//
//		public void onSuccess(Vector<User> result) {
//			userVector = result;
//			for (int userNumber = 0; userNumber < userVector.size(); userNumber++) {
//				userListBox.addItem(userVector.get(userNumber).getUsername());
//			}
//			userListBox.setVisibleItemCount(1);
//		}
//	}
//
//	class AllArticlesCallback implements AsyncCallback<Vector<Article>> {
//
//		public void onFailure(Throwable caught) {
//		}
//
//		public void onSuccess(Vector<Article> result) {
//			articleVector = result;
//			//refreshTable();
//		}
//	}
//
	class GetArticleCallback implements AsyncCallback<Article> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Article result) {
			article = result;
		}
	}

	class SetCheckCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Loeschen");
			articleTable.setWidget(rowIndex, 5, getCbCheck());
		}

		public void onSuccess(Void result) {
			if(article.getDelDat() == null) {
				boughtTable.setText(rowCount, 0, Integer.toString(article.getId()));
				boughtTable.setText(rowCount, 1, article.getName());
				boughtTable.setText(rowCount, 2, Integer.toString(article.getQuantity()));
				boughtTable.setText(rowCount, 3, article.getUnit());
				boughtTable.setText(rowCount, 4, Integer.toString(article.getRetailerId()));
				boughtTable.setWidget(rowCount, 5, getCbReturn());
				articleTable.removeRow(rowIndex);
			}else if(article.getDelDat() != null) {
				articleTable.setText(rowCount, 0, Integer.toString(article.getId()));
				articleTable.setText(rowCount, 1, article.getName());
				articleTable.setText(rowCount, 2, Integer.toString(article.getQuantity()));
				articleTable.setText(rowCount, 3, article.getUnit());
				articleTable.setText(rowCount, 4, Integer.toString(article.getRetailerId()));
				articleTable.setWidget(rowCount, 5, getCbReturn());
			}

			if (boughtTable.getRowCount() > 1) {
				boughtTable.setVisible(true);
			}
			article = null;
			//refreshTable();
		}
		}

//	class GetRetailerCallback implements AsyncCallback<Retailer> {
//
//		public void onFailure(Throwable caught) {
//		}
//
//		public void onSuccess(Retailer result) {
//			retailer = result;
//
//		}
//	}
//
	class SaveArticleCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Void result) {
			article = null;
			refreshTable();
		}
	}
	
//
//	class setArticleFavorite implements AsyncCallback<Article> {
//
//		public void onFailure(Throwable caught) {
//		}
//
//		public void onSuccess(Article result) {
//			result = article;
//			if (rowIndexA != 0) {
//				if (articleTable.getWidget(rowIndexA, 6) == getFavButtonFalse()) {
//					articleTable.setWidget(rowIndexA, 6, getFavButtonTrue());
//				} else {
//					articleTable.setWidget(rowIndexA, 6, getFavButtonFalse());
//				}
//
//			} else if (rowIndexB != 0) {
//				if (boughtTable.getWidget(rowIndexB, 6) == getFavButtonFalse()) {
//					boughtTable.setWidget(rowIndexB, 6, getFavButtonTrue());
//				} else {
//					boughtTable.setWidget(rowIndexB, 6, getFavButtonFalse());
//				}
//			}
//		}
//	}
//
//	class AddArticleCallback implements AsyncCallback<Article> {
//
//		public void onFailure(Throwable caught) {
//			Window.alert("NOPE to addArticle");
//		}
//
//		public void onSuccess(Article result) {
//			Window.alert("Check");
//			int addRow = articleTable.getRowCount() - 1;
//			articleTable.setText(addRow, 1, articleTextBox.getText());
//			articleTable.setText(addRow, 2, quantityTextBox.getText());
//			articleTable.setText(addRow, 3, unitTextBox.getText());
//			ev.getRetailerById(retailerListBox.getSelectedIndex(), new GetRetailerCallback());
//			articleTable.setText(addRow, 4, retailer.getRetailerName());
//			articleTextBox.setText(null);
//			quantityTextBox.setText(null);
//			unitTextBox.setText(null);
//		}
//	}
	
	class setArticleVectorCallback implements AsyncCallback<Vector<Article>> {
		
		public void onFailure(Throwable caugth) {
		}
		
		public void onSuccess(Vector<Article> result) {
			articleVector = result;
		}
	}
}