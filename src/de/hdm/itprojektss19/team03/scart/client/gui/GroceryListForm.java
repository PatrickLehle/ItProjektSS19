package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
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
 * GUI-Seite der Einkaufsliste einer Gruppe. Hier koennen der Einkaufsliste neue
 * Artikel hinzugefuegt, eingekauft oder geloescht werden.
 * 
 * @author tom
 * @author bastiantilk
 * @author Marco
 */
public class GroceryListForm extends VerticalPanel {

	public GroceryListForm(User u, Group g, Vector<Article> a, Retailer r, GroceryList gr) {
		this.user = u;
		this.group = g;
		this.retailer = r;
		this.articleVector = a;
		this.groceryList = gr;
	};

	private User user;
	private Group group;
	private Retailer retailer;

	private EditorServiceAsync ev = ClientsideSettings.getEditor();

	HorizontalPanel hpTitle = new HorizontalPanel();
	HorizontalPanel hpUserRetailer = new HorizontalPanel();
	HorizontalPanel hpButtons = new HorizontalPanel();

	Button addBtn = new Button("<image src='/images/plusButton.png' width='16px' height='16px' align='center'/>");
	Button editBtn = new Button("<image src='/images/editButton.png' width='16px' height='16px' align='center'/>");
	Button deleteBtn = new Button("<image src='/images/minusButton.png' width='16px' height='16px' align='center'/>");
	Button checkBtn = new Button();

	Boolean addBtnBoolean = false;
	Boolean editBtnBoolean = false;
	Boolean deleteBtnBoolean = false;
	Boolean checkBtnBoolean = false;
	TextBox articleTextBox = new TextBox(); // Artikel
	TextBox quantityTextBox = new TextBox(); // Menge
	TextBox unitTextBox = new TextBox(); // Mengeneinheit

	ListBox retailerListBox = new ListBox();

	FlexTable articleTable = new FlexTable();
	FlexTable boughtTable = new FlexTable();
	Article article = new Article(); // temporaerer Artikel wenn ein Artikel
	// geupdated/neu erstellt wird, um dieses Objekt auch an die DB zu pushen

	Vector<Article> articleVector;
	Vector<Retailer> retailerVector = new Vector<Retailer>();
	Vector<GroupUser> groupUserVector = new Vector<GroupUser>();
	Vector<User> userVector = new Vector<User>();

	GroceryList groceryList; // Muss bei dem Aufruf der GUI-Seite uebergeben werden
	GroceryListArticle groceryListArticle = new GroceryListArticle();

	int trueCount = 1;
	int falseCount = 1;
	int rowIndex;
	int rowCount;
	int rowIndexA;
	int rowIndexB;
	int articleNumber;

	// setzt Atribute die in mehreren ClickHandlern gebraucht werden.
	public int globalRow;
	public int finalGlobalRow;
	public String first;
	public String second;
	public String third;
	public String fourth;

	// Wird bei dem Aufruf der Klasse/des Widgets automatisch ausgefuehrt
	public void onLoad() {
		super.onLoad();

		articleTextBox.setVisibleLength(5);
		quantityTextBox.setVisibleLength(5);
		unitTextBox.setVisibleLength(5);
		// zum laden/fuellen der Tabelle auf
		boughtTable.setVisible(false);
		loadTable();
		Timer refresh = new Timer() {
			public void run() {
				if (checkBtnBoolean == false && editBtnBoolean == false && deleteBtnBoolean == false
						&& addBtnBoolean == false) {
					refreshTable();
				}
			}
		};
		refresh.scheduleRepeating(2000);
		this.add(articleTable);
		this.add(boughtTable);

		// add stylenames
		addBtn.setStyleName("icon-button");
		editBtn.setStyleName("icon-button");
		deleteBtn.setStyleName("icon-button");
		checkBtn.setStyleName("icon-button");
		articleTextBox.setStyleName("textbox");
		quantityTextBox.setStyleName("textbox");
		unitTextBox.setStyleName("textbox");

		// Buttons werden dem untersten horizontal Panel hinzugefuegt
		hpButtons.add(addBtn);
		hpButtons.add(editBtn);
		hpButtons.add(deleteBtn);
		hpButtons.add(checkBtn);

		addBtn.addClickHandler(new AddClickHandler());
		editBtn.addClickHandler(new EditClickHandler());
		deleteBtn.addClickHandler(new DeleteClickHandler());
		checkBtn.addClickHandler(new CheckClickHandler());

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

	/**
	 * Methode um die Tabelle bei Aenderungen neu zu laden
	 * 
	 */
	public void refreshTable() {
		ev.findAllArticleByGroceryListId(groceryList.getId(), new setArticleVectorCallback());
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
		// for Schleife das alle Artikel mit Name Quantity Unit und RetailerName
		// aufgelistet werden im Panel.
		for (articleNumber = 0; articleNumber < articleVector.size(); articleNumber++) {
			if (articleVector.get(articleNumber).getDelDat() == null
					&& articleVector.get(articleNumber).getCheckBoolean() == false) {
				articleTable.setText(falseCount, 0, Integer.toString(articleVector.get(articleNumber).getId()));
				articleTable.setText(falseCount, 1, articleVector.get(articleNumber).getName());
				articleTable.setText(falseCount, 2, Integer.toString(articleVector.get(articleNumber).getQuantity()));
				articleTable.setText(falseCount, 3, articleVector.get(articleNumber).getUnit());
				articleTable.setText(falseCount, 4, articleVector.get(articleNumber).getRetailerName());
				if (articleVector.get(articleNumber).getFav()) {
					articleTable.setWidget(falseCount, 6, new FavButton(articleVector.get(articleNumber), false));
				} else {
					articleTable.setWidget(falseCount, 6, new FavButton(articleVector.get(articleNumber), true));
				}
				falseCount++;
			} else if (articleVector.get(articleNumber).getDelDat() != null
					&& articleVector.get(articleNumber).getCheckBoolean() == false) {
				boughtTable.setText(trueCount, 0, Integer.toString(articleVector.get(articleNumber).getId()));
				boughtTable.setText(trueCount, 1, articleVector.get(articleNumber).getName());
				boughtTable.setText(trueCount, 2, Integer.toString(articleVector.get(articleNumber).getQuantity()));
				boughtTable.setText(trueCount, 3, articleVector.get(articleNumber).getUnit());
				boughtTable.setText(trueCount, 4, articleVector.get(articleNumber).getRetailerName());
				if (articleVector.get(articleNumber).getFav()) {
					boughtTable.setWidget(trueCount, 6, new FavButton(articleVector.get(articleNumber), false));
				} else {
					boughtTable.setWidget(trueCount, 6, new FavButton(articleVector.get(articleNumber), true));
				}
				trueCount++;
				visibleNum = trueCount;
			}
		}
		if (visibleNum > 1) {
			boughtTable.setVisible(true);
		}
	}

	//
	// public ListBox getRetailerListBox() {
	// ev.getAllRetailerByGroupId(1, new AllRetailersCallback());
	// ev.getArticleByArticleId(globalRow, new GetArticleCallback());
	// ev.getRetailerById(article.getRetailerId(), new GetRetailerCallback());
	// retailerListBox.setItemSelected(retailer.getId() - 1, true);
	// return retailerListBox;
	// }
	//

	/**
	 * @author tom
	 * 
	 *         getCbCheck: CheckBox und ClickHandler die bei dem kaufen eines
	 *         Artikels aufgerufen werden. CheckBox wird im letzem Column generiert
	 *         Ausgewaehlte Reihe wird geloescht und in die zweite Tabelle kopiert
	 *         und eine neue CheckBox wird kreiert getCbReturn.
	 * 
	 * @return eine CheckBox wird zurueckgegeben
	 */
	public ListBox getRetailerListBox() {
		ev.getAllRetailerByGroupId(group.getId(), new AllRetailersCallback());
		retailerListBox.setItemSelected(1, true);
		return retailerListBox;
	}

	/**
	 * @author tom
	 * 
	 *         Schreibt die ausgewaehlte Zeile in die erste Tabele fals ein Artikel
	 *         falscherweise als gekauft markiert wurde.
	 * @return Es gibt eine cB zurueck die true oder false gesetzt wurde.
	 */
	class getCbReturn extends CheckBox {

		public getCbReturn(Article a, int row) {
			if (a.getDelDat() == null) {
				this.setValue(false);
			} else if (a.getDelDat() != null) {
				this.setValue(true);
			}
			this.addClickHandler(new CbCheckClickHandler(a, row));
		}
	}

	class CbCheckClickHandler implements ClickHandler {
		Article a;
		int row;

		public CbCheckClickHandler(Article a, int row) {
			this.a = a;
			this.row = row;
		}

		public void onClick(ClickEvent event) {
			if (a.getDelDat() == null) {
				ev.deleteArticle(a, new SetCheckCallback(row));
			} else if (a.getDelDat() != null) {
				a.setDelDat(null);
				ev.deleteArticle(a, new SetCheckCallback(row));
			}
		}
	}

	/**
	 * @author tom
	 * 
	 *         Button ClickHandler fuer gekaufte Artikel. Setzt den Boolean des
	 *         Buttons true und false und schaut ob andere Buttons aktiv sind. Setzt
	 *         und loescht CheckBoxen aus der dem letzten Column.
	 *
	 */
	class CheckClickHandler implements ClickHandler {
//HIER
		@Override
		public void onClick(ClickEvent e) {
			if (checkBtnBoolean == false && editBtnBoolean == false && deleteBtnBoolean == false
					&& addBtnBoolean == false) {
				checkBtnBoolean = true;
				for (int articleNum = 0; articleNum < articleVector.size(); articleNum++) {
					if (articleVector.get(articleNum).getDelDat() == null
							&& articleVector.get(articleNum).getCheckBoolean() == false) {
						for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
							articleTable.setWidget(aNum, 5, new getCbReturn(articleVector.get(articleNum), articleNum));
						}
					} else if (articleVector.get(articleNum).getDelDat() != null
							&& articleVector.get(articleNum).getCheckBoolean() == false) {
						for (int bNum = 1; bNum < boughtTable.getRowCount(); bNum++) {
							boughtTable.setWidget(bNum, 5, new getCbReturn(articleVector.get(articleNum), articleNum));
						}
					}
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

// Methoden zum Editieren eines Artikels====================================
	public void saveRowContent() {
		first = articleTable.getText(globalRow, 1);
		second = articleTable.getText(globalRow, 2);
		third = articleTable.getText(globalRow, 3);
	}

	public void setTextBoxesContent() {
		articleTextBox.setText(articleTable.getText(globalRow, 1));
		quantityTextBox.setText(articleTable.getText(globalRow, 2));
		unitTextBox.setText(articleTable.getText(globalRow, 3));
		articleTable.setWidget(globalRow, 1, articleTextBox);
		articleTable.setWidget(globalRow, 2, quantityTextBox);
		articleTable.setWidget(globalRow, 3, unitTextBox);
		getRetailerListBox();
	}

	public void clearTextBoxes() {
		articleTextBox.setText(null);
		quantityTextBox.setText(null);
		unitTextBox.setText(null);
	}


	public void replaceUnchangedText() {
		articleTable.setText(globalRow, 1, first);
		articleTable.setText(globalRow, 2, second);
		articleTable.setText(globalRow, 3, third);
		// Warum geht das nicht??
		Window.alert(retailer.getRetailerName());
		articleTable.setText(globalRow, 4, retailer.getRetailerName());
	}

	public String textBoxesEmpty() {
		if (articleTextBox.getText().isEmpty() == true && quantityTextBox.getText().isEmpty() == true
				&& unitTextBox.getText().isEmpty() == true) {
			return "true";
		} else if (articleTextBox.getText().isEmpty() == false && quantityTextBox.getText().isEmpty() == false
				&& unitTextBox.getText().isEmpty() == false) {
			return "false";
		} else {
			return "else";
		}
	}

	public void setArticleAtributes() {
		article.setName(articleTextBox.getText());
		article.setQuantity(Integer.parseInt(quantityTextBox.getText()));
		article.setUnit(unitTextBox.getText());
		article.setRetailerId(getRetailerListBox().getSelectedIndex());
	}

	/**
	 * @author tom CheckBox fuer DeleteButton. Loescht die ausgewaehlte Reihe aus
	 *         der Tabelle.
	 * @return gibt eine cB zurueck die true oder false gesetzt wurde.
	 */
	public CheckBox getCbEdit() {
		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				globalRow = articleTable.getCellForEvent(event).getRowIndex();
			}
		});
		cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					if (textBoxesEmpty() == "true") {
						finalGlobalRow = globalRow;
						saveRowContent();
						setTextBoxesContent();
					} else if (textBoxesEmpty() == "false") {
						ev.getArticleByArticleId(Integer.parseInt(articleTable.getText(finalGlobalRow, 0)), new GetArticleCallback());
						setArticleAtributes();
						clearTextBoxes();
						articleTable.removeCell(finalGlobalRow, 5);
						articleTable.setWidget(finalGlobalRow, 5, getCbEdit());
						if (articleTable.getText(globalRow, 1).isEmpty() == false
								&& articleTable.getText(globalRow, 2).isEmpty() == false
								&& articleTable.getText(globalRow, 3).isEmpty() == false
								&& articleTable.getText(globalRow, 4).isEmpty() == false) {
							finalGlobalRow = globalRow;
							saveRowContent();
							setTextBoxesContent();
						}
					} else {
						articleTable.removeCell(finalGlobalRow, 5);
						articleTable.setWidget(finalGlobalRow, 5, getCbEdit());
						articleTable.setText(finalGlobalRow, 1, first);
						articleTable.setText(finalGlobalRow, 2, second);
						articleTable.setText(finalGlobalRow, 3, third);
						saveRowContent();
						clearTextBoxes();
						articleTable.setWidget(finalGlobalRow, 5, getCbEdit());
						if (articleTable.getText(globalRow, 1).isEmpty() == false
								&& articleTable.getText(globalRow, 2).isEmpty() == false
								&& articleTable.getText(globalRow, 3).isEmpty() == false) {
							finalGlobalRow = globalRow;
							setTextBoxesContent();
						}
						Window.alert("Anderung wurde nicht gespeichert");
					}
				} else {
					if (textBoxesEmpty() == "false") {
						ev.getArticleByArticleId(Integer.parseInt(articleTable.getText(finalGlobalRow, 0)), new GetArticleCallback2());
						setArticleAtributes();
						clearTextBoxes();
						globalRow = 0;
					} else {
						replaceUnchangedText();
						clearTextBoxes();
						articleTable.setWidget(globalRow, 5, getCbEdit());
						Window.alert("Anderung wurde nicht gespeichert");
						globalRow = 0;
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
				for (int articleNum = 0; articleNum < articleVector.size(); articleNum++) {
					if (articleVector.get(articleNum).getDelDat() == null
							&& articleVector.get(articleNum).getCheckBoolean() == false) {
						for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
							articleTable.setWidget(aNum, 5, getCbEdit());
						}
					}
				}
			} else if (editBtnBoolean == true) {
				editBtnBoolean = false;
				if (textBoxesEmpty() == "false") {
					ev.getArticleByArticleId(Integer.parseInt(articleTable.getText(finalGlobalRow, 0)), new GetArticleCallback2());
					setArticleAtributes();
					clearTextBoxes();
				} else if (globalRow != 0) {
					replaceUnchangedText();
					clearTextBoxes();
					Window.alert("Anderung wurde nicht gespeichert");
				}
				refreshTable();
			} else if (checkBtnBoolean == false || deleteBtnBoolean == false || addBtnBoolean == false) {
				Window.alert("Bitte anderen Button deaktivieren.");
			} else {
			}
		}
	}

	/**
	 * @author tom
	 * 
	 *         CheckBox fuer DeleteButton. Loescht die ausgewaehlte Reihe aus der
	 *         Tabele.
	 */
	class getCbDel extends CheckBox {
		public getCbDel(Article a) {
			this.addClickHandler(new CbDeleteClickHandler(a));
			this.setValue(false);
		}
	}

	class CbDeleteClickHandler implements ClickHandler {
		Article article;

		public CbDeleteClickHandler(Article a) {
			this.article = a;
		}

		public void onClick(ClickEvent e) {
			article.setCheckBoolean(true);
			ev.saveArticle(article, new DeleteArticleCallback());
		}

	}

	/**
	 * @author tom
	 *
	 *         ClickHandler fuer DeleteButton setzt Boolean des Buttons true und
	 *         false und schaut ob andere Buttons aktiv sind Ausgewaehlte Reihe wird
	 *         aus der Tabele gel�scht.
	 */
	class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (deleteBtnBoolean == false && checkBtnBoolean == false && editBtnBoolean == false
					&& addBtnBoolean == false) {
				deleteBtnBoolean = true;
				for (int articleNum = 0; articleNum < articleVector.size(); articleNum++) {
					if (articleVector.get(articleNum).getDelDat() == null
							&& articleVector.get(articleNum).getCheckBoolean() == false) {
						for (int i = 1; i < articleTable.getRowCount(); i++) {
							articleTable.setWidget(i, 5, new getCbDel(articleVector.get(articleNum)));
						}
					}
				}
			} else if (deleteBtnBoolean == true) {
				refreshTable();
				deleteBtnBoolean = false;
			} else if (checkBtnBoolean == true || editBtnBoolean == true || addBtnBoolean == false) {
				Window.alert("Bitte anderen Button deaktivieren.");
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}
	}

	/**
	 * ClickHandler um ein Artikel zu favorisieren
	 * 
	 */
	class FavClickHandler implements ClickHandler {
		Article article;
		Boolean fav;

		public FavClickHandler(Article a, Boolean f) {
			article = a;
			fav = f;
		}

		public void onClick(ClickEvent e) {
			article.setFav(fav);
			ev.saveArticle(article, new SaveArticleFavoriteCallback(fav));
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
	class AddClickHandler implements ClickHandler {

		public void onClick(ClickEvent e) {

			if (deleteBtnBoolean == false && checkBtnBoolean == false && editBtnBoolean == false
					&& addBtnBoolean == false) {
				addBtnBoolean = true;
				int i = articleTable.getRowCount();
				articleTable.setWidget(i, 1, articleTextBox);
				articleTable.setWidget(i, 2, quantityTextBox);
				articleTable.setWidget(i, 3, unitTextBox);
				articleTable.setText(i, 4, retailer.getRetailerName());
			} else if (addBtnBoolean == true) {
				if (textBoxesEmpty() == "false") {
					article.setName(articleTextBox.getText());
					article.setQuantity(Integer.parseInt(quantityTextBox.getText()));
					article.setUnit(unitTextBox.getText());
					article.setRetailerId(retailer.getId());
					article.setOwnerId(retailer.getUser().getId());
					article.setGroupId(group.getId());
					article.setCheckBoolean(false);
					ev.getAllArticleByName(articleTextBox.getText(), group.getId(), new GetArticleVectorByGroup());

				} else if (textBoxesEmpty() == "true") {
					addBtnBoolean = false;
					articleTextBox.setText(null);
					quantityTextBox.setText(null);
					unitTextBox.setText(null);
					refreshTable();
				} else if (articleTextBox.getText().isEmpty() == false || quantityTextBox.getText().isEmpty() == false
						|| unitTextBox.getText().isEmpty() == false) {
					Window.alert("Es wurde kein Artikel hinzugef\u00fcgt da die Angaben nicht vollst\u00e4ndig waren");
				}
			} else if (checkBtnBoolean == true || editBtnBoolean == true || deleteBtnBoolean == false) {
				Window.alert("Bitte anderen Button deaktivieren.");
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}
	}

	// CALLBACKS============================================================

	/**
	 * Callback-Methode um alle Retailer der Gruppe aus der DB zu finden. Die
	 * RetailerListBox wird anschliessend mit den Retailern gefuellt.
	 */
	class AllRetailersCallback implements AsyncCallback<Vector<Retailer>> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Vector<Retailer> result) {
			retailerVector = result;
			for (int retailerNumber = 0; retailerNumber < retailerVector.size(); retailerNumber++) {
				retailerListBox.addItem(retailerVector.get(retailerNumber).getRetailerName());
			}
			retailerListBox.setVisibleItemCount(1);
			retailerListBox.setSelectedIndex(retailer.getId() - 1);
			articleTable.setWidget(globalRow, 4, retailerListBox);
		}
	}
	
	class GetRetailerCallback implements AsyncCallback<Retailer> {

		public void onFailure(Throwable caught) {
			Window.alert("Fehler Zeile 670");
		}

		public void onSuccess(Retailer result) {
			article.setRetailerId(result.getId());
			ev.saveArticle(article, new saveEditedArticle(result));
		}
	}
	
	class GetRetailerCallback2 implements AsyncCallback<Retailer> {

		public void onFailure(Throwable caught) {
			Window.alert("Fehler Zeile 673");
		}

		public void onSuccess(Retailer result) {
			article.setRetailerId(result.getId());
			ev.saveArticle(article, new saveEditedArticle2(result));
		}
	}

	class GetArticleCallback implements AsyncCallback<Article> {

		public void onFailure(Throwable caught) {
			Window.alert("Fehler Zeile 692");
		}

		public void onSuccess(Article result) {
			article = result;
			article.setQuantity(Integer.parseInt(quantityTextBox.getText()));
			article.setUnit(unitTextBox.getText());
			ev.getRetailerById(retailerListBox.getSelectedIndex(), new GetRetailerCallback());
		}
	}
	
	class GetArticleCallback2 implements AsyncCallback<Article> {

		public void onFailure(Throwable caught) {
			Window.alert("Fehler Zeile 700");
		}

		public void onSuccess(Article result) {
			article = result;
			article.setQuantity(Integer.parseInt(quantityTextBox.getText()));
			article.setUnit(unitTextBox.getText());
			ev.getRetailerById(retailerListBox.getSelectedIndex(), new GetRetailerCallback2());
		}
	}

	class GetArticleVectorByGroup implements AsyncCallback<Vector<Article>> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Vector<Article> result) {
			if (addBtnBoolean) {
				article.setFav(result.get(result.size() - 1).getFav());
				ev.createArticle(article, new AddArticleCallback());
			} else {
				for (int i = 0; i < result.size(); i++) {
					article.setId(result.get(i).getId());
					Window.alert(article.getId() + "");
					article.setName(result.get(i).getName());
					article.setQuantity(result.get(i).getQuantity());
					article.setUnit(result.get(i).getUnit());
					article.setRetailerId(result.get(i).getRetailerId());
					article.setOwnerId(result.get(i).getOwnerId());
					article.setGroupId(result.get(i).getGroupId());
					article.setCheckBoolean(result.get(i).getCheckBoolean());
					ev.saveArticle(article, new saveArticleCallback());
				}
				refreshTable();
			}
		}
	}

	class saveArticleCallback implements AsyncCallback<Article> {

		public void onFailure(Throwable caught) {
			Window.alert(article.getId() + caught.getMessage());

		}

		public void onSuccess(Article result) {
			Window.alert(article.getId() + "");
		}
	}

	/**
	 * Callback-Methode um einen Artikel als gekauft/nicht-gekauft zu markieren.
	 * Dies geschieht ueber die delDat-Variable die gesetzt/entfernt wird
	 * Anschliessend wird die Tabelle aktualisiert
	 */
	class SetCheckCallback implements AsyncCallback<Article> {
		int row;

		public SetCheckCallback(int row) {
			this.row = row;
		}

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Article result) {
			refreshTable();
		}
	}

	/**
	 * CallBack um die ausgewaehlte Reihe aus der Tabelle zu loeschen. Anschliessend
	 * wir did eTabelle aktualisiert.
	 */
	class DeleteArticleCallback implements AsyncCallback<Article> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Article result) {
			refreshTable();
		}
	}

	class AddArticleCallback implements AsyncCallback<Article> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Article result) {
			ev.addArticleToGroceryList(groceryList, result, new AddArticleToGroceryListCallback());
			addBtnBoolean = false;
		}
	}

	/**
	 * Callback-Methode um einen Artikel der Einkaufsliste hinzuzufuegen
	 * Anschliessend wird die Tabelle nue geladen
	 */
	class AddArticleToGroceryListCallback implements AsyncCallback<GroceryListArticle> {
		public void onFailure(Throwable caught) {
		}

		public void onSuccess(GroceryListArticle result) {
			refreshTable();
		}
	}

	/**
	 * Callback-Methode um alle Artikel der Einkaufsliste in der DB zu finden Bei
	 * Erfolg wird die Tabelle mit den Artikeln der Einkaufsliste erneut geladen.
	 */
	class setArticleVectorCallback implements AsyncCallback<Vector<Article>> {
		public void onFailure(Throwable caugth) {
		}

		public void onSuccess(Vector<Article> result) {
			articleVector = result;
			loadTable();
			if (deleteBtnBoolean == true && articleTable.getRowCount() > 1) {
				for (int articleNum = 0; articleNum < articleVector.size(); articleNum++) {
					if (articleVector.get(articleNum).getDelDat() == null
							&& articleVector.get(articleNum).getCheckBoolean() == false) {
						for (int i = 1; i < articleTable.getRowCount(); i++) {
							articleTable.setWidget(i, 5, new getCbDel(articleVector.get(articleNum)));
						}
					}
				}
			} else if (deleteBtnBoolean == true && articleTable.getRowCount() == 1) {
				deleteBtnBoolean = false;
			} else if (checkBtnBoolean == true) {
				for (int articleNum = 0; articleNum < articleVector.size(); articleNum++) {
					if (articleVector.get(articleNum).getDelDat() == null
							&& articleVector.get(articleNum).getCheckBoolean() == false) {
						for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
							articleTable.setWidget(aNum, 5, new getCbReturn(articleVector.get(articleNum), articleNum));
						}
					} else if (articleVector.get(articleNum).getDelDat() != null
							&& articleVector.get(articleNum).getCheckBoolean() == false) {
						for (int bNum = 1; bNum < boughtTable.getRowCount(); bNum++) {
							boughtTable.setWidget(bNum, 5, new getCbReturn(articleVector.get(articleNum), articleNum));
						}
					}
				}
			}else if (editBtnBoolean == true && articleTable.getRowCount() > 1) {
				for (int articleNum = 0; articleNum < articleVector.size(); articleNum++) {
					if (articleVector.get(articleNum).getDelDat() == null
							&& articleVector.get(articleNum).getCheckBoolean() == false) {
						for (int aNum = 1; aNum < articleTable.getRowCount(); aNum++) {
							articleTable.setWidget(aNum, 5, getCbEdit());
						}
					}
				}
			} else if (editBtnBoolean == true && articleTable.getRowCount() == 1) {
				editBtnBoolean = false;
			}
		}
	}

	/**
	 * Callback-Methode um einen Artikel als Favorit zu markieren. Bei Erfolg wird
	 * die Tabelle neu geladen
	 */
	class SaveArticleFavoriteCallback implements AsyncCallback<Article> {
		Boolean fav;

		public SaveArticleFavoriteCallback(Boolean fav) {
			this.fav = fav;
		}

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Article a) {
			ev.getAllArticleByName(a.getName(), group.getId(), new GetArticleVectorByGroup());

		}
	}

	class SaveArticleFavoriteForGroupCallback implements AsyncCallback<Article> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Article result) {

		}
	}

	class FavButton extends Image {

		public FavButton(Article a, Boolean f) {
			this.setPixelSize(25, 25);
			this.addClickHandler(new FavClickHandler(a, f));
			if (f) {
				this.setUrl("/images/heart-outlin.png");
			} else {
				this.setUrl("/images/heart.png");
			}
		}
	}

	/**
	 * Methode um einen geaenderten Artikel in der DB zu updaten
	 *
	 */
	class saveEditedArticle implements AsyncCallback<Article> {
		Retailer r;
		public saveEditedArticle(Retailer r) {
			this.r = r;
		}

		public void onFailure(Throwable caught) {
			Window.alert("Fehler Zeile 879");
		}

		public void onSuccess(Article result) {
			articleTable.setText(finalGlobalRow, 1, articleTextBox.getText());
			articleTable.setText(finalGlobalRow, 2, quantityTextBox.getText());
			articleTable.setText(finalGlobalRow, 3, unitTextBox.getText());
			articleTable.setText(finalGlobalRow, 4, r.getRetailerName());
			refreshTable();
			article = null;
		}
	}

	class saveEditedArticle2 implements AsyncCallback<Article> {
		Retailer r;
		
		public saveEditedArticle2(Retailer r) {
			this.r = r;
		}
		
		public void onFailure(Throwable caught) {
		Window.alert("Fehler Zeile 901");
		}

		public void onSuccess(Article result) {
			articleTable.setText(globalRow, 1, articleTextBox.getText());
			articleTable.setText(globalRow, 2, quantityTextBox.getText());
			articleTable.setText(globalRow, 3, unitTextBox.getText());
			articleTable.setText(globalRow, 4, r.getRetailerName());
			refreshTable();
			article = null;
		}
	}

}