package de.hdm.itprojektss19.team03.scart.client.gui;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryListArticle;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public class ArticlesForm extends VerticalPanel {

	public ArticlesForm(User u, Group g, Vector<Article> a, Retailer r, GroceryList gr) {
		this.user = u;
		this.group = g;
		this.retailer = r;
		this.articleVector = a;
		this.groceryList = gr;
	}

	private EditorServiceAsync editorService = ClientsideSettings.getEditor();

	private User user;
	private Group group;
	private Retailer retailer;
	private Vector<Article> articleVector;
	private GroceryList groceryList;

	private FlexTable articleTable = new FlexTable();
	private FlexTable boughtTable = new FlexTable();

	TextBox articleNameTB = new TextBox();
	TextBox articleQuantityTB = new TextBox();
	TextBox articleUnitTB = new TextBox();

	public void onLoad() {
		boughtTable.setVisible(false);
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
		articleTable.setText(0, 3, "Einheit");
		articleTable.getRowFormatter().setStyleName(0, "first-row");
		boughtTable.getRowFormatter().setStyleName(0, "first-row");
		boughtTable.setText(0, 1, "Gekauft");
		articleTable.setStyleName("tables");
		boughtTable.setStyleName("tables");
		boughtTable.setCellSpacing(2);
		boughtTable.setCellPadding(1);
		articleTable.setCellSpacing(2);
		articleTable.setCellPadding(1);

		Button addBtn = new Button("<image src='/images/plusButton.png' width='16px' height='16px' align='center'/>");
		addBtn.setStyleName("table-icon-button");
		addBtn.addClickHandler(new AddArticleClickHandler());

		articleNameTB.setWidth("80%");
		articleQuantityTB.setWidth("80%");
		articleUnitTB.setWidth("80%");

		articleTable.setWidget(1, 1, articleNameTB);
		articleTable.setWidget(1, 2, articleQuantityTB);
		articleTable.setWidget(1, 3, articleUnitTB);
		articleTable.setWidget(1, 4, addBtn);

		int trueCount = 1;
		int falseCount = 2;
		for (int articleNumber = 0; articleNumber < articleVector.size(); articleNumber++) {
			if (articleVector.get(articleNumber).getDelDat() == null
					&& articleVector.get(articleNumber).getCheckBoolean() == false) {

				Button checkBtn = new Button(
						"<image src='/images/check-bold.png' width='16px' height='16px' align='center'/>");
				checkBtn.setStyleName("table-icon-button");
				checkBtn.addClickHandler(new CheckClickHandler(articleVector.get(articleNumber)));
				checkBtn.setTitle("Als gekauft markieren");
				articleTable.setWidget(falseCount, 0, checkBtn);

				Label articleName = new Label(articleVector.get(articleNumber).getName());
				articleTable.setWidget(falseCount, 1, articleName);

				Label articleCount = new Label(Integer.toString(articleVector.get(articleNumber).getQuantity()));
				articleTable.setWidget(falseCount, 2, articleCount);

				Label articleUnit = new Label(articleVector.get(articleNumber).getUnit());
				articleTable.setWidget(falseCount, 3, articleUnit);

				if (articleVector.get(articleNumber).getFav()) {
					articleTable.setWidget(falseCount, 4, new FavButton(articleVector.get(articleNumber), false));
				} else {
					articleTable.setWidget(falseCount, 4, new FavButton(articleVector.get(articleNumber), true));
				}

				Button dotBtn = new Button(
						"<image src='/images/dots-vertical.png' width='20px' height='20	px' align='center'/>");
				dotBtn.setStyleName("table-icon-button");
				dotBtn.addClickHandler(new DotClickHandler(articleVector.get(articleNumber), falseCount));
				articleTable.setWidget(falseCount, 5, dotBtn);
				articleTable.getRowFormatter().setStyleName(falseCount, "article-row");

				falseCount++;
			} else if (articleVector.get(articleNumber).getDelDat() != null
					&& articleVector.get(articleNumber).getCheckBoolean() == false) {

				Button checkBtn = new Button(
						"<image src='/images/minusButton.png' width='16px' height='16px' align='center'/>");
				checkBtn.setStyleName("table-icon-button");
				checkBtn.addClickHandler(new CheckClickHandler(articleVector.get(articleNumber)));
				checkBtn.setTitle("Als nicht gekauft markieren");
				boughtTable.setWidget(trueCount, 0, checkBtn);

				Label articleName = new Label(articleVector.get(articleNumber).getName());
				boughtTable.setWidget(trueCount, 1, articleName);

				Label articleCount = new Label(Integer.toString(articleVector.get(articleNumber).getQuantity()));
				boughtTable.setWidget(trueCount, 2, articleCount);

				Label articleUnit = new Label(articleVector.get(articleNumber).getUnit());
				boughtTable.setWidget(trueCount, 3, articleUnit);

				if (articleVector.get(articleNumber).getFav()) {
					boughtTable.setWidget(trueCount, 4, new FavButton(articleVector.get(articleNumber), false));
				} else {
					boughtTable.setWidget(trueCount, 4, new FavButton(articleVector.get(articleNumber), true));
				}

				// Button dotBtn = new Button(
				// "<image src='/images/dots-vertical.png' width='20px' height='20 px'
				// align='center'/>");
				// dotBtn.setStyleName("table-icon-button");
				// dotBtn.addClickHandler(new DotClickHandler(articleVector.get(articleNumber),
				// trueCount));
				// boughtTable.setWidget(trueCount, 5, dotBtn);
				boughtTable.getRowFormatter().setStyleName(trueCount, "article-row");

				trueCount++;
			}
		}

		if (trueCount > 1) {
			boughtTable.setVisible(true);
		}

		this.add(articleTable);
		this.add(boughtTable);
		this.setSpacing(10);
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
			editorService.saveArticle(article, new SaveArticleCallback());
		}

	}

	/**
	 * ClickHandler für das Artikel Menue
	 * 
	 */
	class DotClickHandler implements ClickHandler {
		Article article;
		int row;

		public DotClickHandler(Article a, int r) {
			article = a;
			row = r;
		}

		public void onClick(ClickEvent event) {
			Widget source = (Widget) event.getSource();
			int left = source.getAbsoluteLeft() + 10;
			int top = source.getAbsoluteTop() + 10;

			DecoratedPopupPanel pop = new DecoratedPopupPanel(true);
			VerticalPanel vp = new VerticalPanel();
			FocusPanel f1 = new FocusPanel();
			FocusPanel f2 = new FocusPanel();
			Label delete = new Label("Löschen");
			Label edit = new Label("Bearbeiten");
			f1.addStyleName("pointer");
			f2.addStyleName("pointer");
			delete.setStyleName("text");
			edit.setStyleName("text");
			f1.add(edit);
			f2.add(delete);
			f2.addClickHandler(new DeleteArticleClickHandler(article));
			f1.addClickHandler(new EditArticleClickHandler(article, row));
			vp.add(f1);
			vp.add(f2);
			vp.setSpacing(2);

			pop.setPopupPosition(left, top);
			pop.setWidget(vp);
			pop.show();
		}

	}

	/**
	 * ClickHandler um Artikel zu bearbeiten
	 *
	 */
	class EditArticleClickHandler implements ClickHandler {
		Article article;
		int row;

		public EditArticleClickHandler(Article a, int r) {
			article = a;
			row = r;
		}

		public void onClick(ClickEvent e) {
			TextBox nameTB = new TextBox();
			nameTB.setText(article.getName());
			nameTB.setWidth(articleTable.getWidget(row, 1).getOffsetWidth() + "px");
			articleTable.setWidget(row, 1, nameTB);

			TextBox quantityTB = new TextBox();
			quantityTB.setText(Integer.toString(article.getQuantity()));
			quantityTB.setWidth(articleTable.getWidget(row, 2).getOffsetWidth() + "px");
			articleTable.setWidget(row, 2, quantityTB);

			TextBox unitTB = new TextBox();
			unitTB.setText(article.getUnit());
			unitTB.setWidth(articleTable.getWidget(row, 3).getOffsetWidth() + "px");
			articleTable.setWidget(row, 3, unitTB);

			articleTable.removeCell(row, 4);
			Button checkBtn = new Button(
					"<image src='/images/check-bold.png' width='16px' height='16px' align='center'/>");
			checkBtn.setStyleName("table-icon-button");
			checkBtn.addClickHandler(new SaveArticleClickHandler(article, nameTB, quantityTB, unitTB));
			checkBtn.setTitle("Speichern");
			articleTable.setWidget(row, 5, checkBtn);

		}
	}

	/**
	 * ClickHandler um einen Artikel zu loeschen
	 *
	 */
	class DeleteArticleClickHandler implements ClickHandler {
		Article article;

		public DeleteArticleClickHandler(Article a) {
			article = a;

		}

		public void onClick(ClickEvent arg0) {
			article.setCheckBoolean(true);
			editorService.saveArticle(article, new SaveArticleCallback());

		}
	}

	/**
	 * Clickhandler um Artikel als gekauft/nicht gekauft zu markieren
	 *
	 */
	class CheckClickHandler implements ClickHandler {
		Article article;

		public CheckClickHandler(Article a) {
			article = a;
		}

		public void onClick(ClickEvent arg0) {
			if (article.getDelDat() == null) {
				article.setDelDat(new Timestamp(new Date().getTime()));
			} else {
				article.setDelDat(null);
			}
			editorService.saveArticle(article, new SaveArticleCallback());
		}

	}

	/**
	 * Clickhandler, um einen Artikel zu speichern
	 *
	 */
	class SaveArticleClickHandler implements ClickHandler {
		Article article;
		TextBox nameTB;
		TextBox quantityTB;
		TextBox unitTB;

		public SaveArticleClickHandler(Article a, TextBox nTB, TextBox qTB, TextBox uTB) {
			article = a;
			nameTB = nTB;
			quantityTB = qTB;
			unitTB = uTB;
		}

		public void onClick(ClickEvent c) {
			article.setName(nameTB.getText());
			article.setQuantity(Integer.parseInt(quantityTB.getText()));
			article.setUnit(unitTB.getText());
			editorService.saveArticle(article, new SaveArticleCallback());

		}
	}

	/**
	 * ClickHandler um einen Artikel hinzuzufuegen
	 *
	 */
	class AddArticleClickHandler implements ClickHandler {

		public AddArticleClickHandler() {

		}

		public void onClick(ClickEvent e) {
			Article article = new Article();
			article.setCheckBoolean(false);
			article.setFav(false);
			article.setName(articleNameTB.getText());
			article.setQuantity(Integer.parseInt(articleQuantityTB.getText()));
			article.setUnit(articleUnitTB.getText());
			article.setRetailer(retailer);
			article.setOwnerId(user.getId());
			article.setGroupId(group.getId());
			article.setGroceryListId(groceryList.getId());

			editorService.createArticle(article, new CreateArticleCallback());
		}
	}

	/**
	 * Callback-Methode um einen Artikel als Favorit zu markieren. Bei Erfolg wird
	 * die Tabelle neu geladen
	 */
	class SaveArticleCallback implements AsyncCallback<Article> {

		public void onFailure(Throwable caught) {
			GWT.log("Failed to save article: " + caught);
		}

		public void onSuccess(Article a) {
			onLoad();
		}
	}

	/**
	 * Callback-Methode um einen Artikel hinzuzufügen
	 *
	 */
	class CreateArticleCallback implements AsyncCallback<Article> {

		public void onFailure(Throwable t) {
			GWT.log("Failed to create Article: " + t);
		}

		public void onSuccess(Article article) {

			editorService.addArticleToGroceryList(groceryList, article, new AddArticleToGroceryList(article));
		}

	}

	/**
	 * Callback um einen Artikel einer Einkaufsliste hinzuzufuegen
	 *
	 */
	class AddArticleToGroceryList implements AsyncCallback<GroceryListArticle> {
		Article article;

		public AddArticleToGroceryList(Article a) {
			article = a;
		}

		public void onFailure(Throwable t) {
			GWT.log("Failed to add article to groceryList: " + t);
		}

		public void onSuccess(GroceryListArticle arg0) {
			articleVector.add(article);
			onLoad();
		}

	}

	/**
	 * FavButton Klasse
	 */
	class FavButton extends Image {

		public FavButton(Article a, Boolean f) {
			this.setPixelSize(25, 25);
			this.addStyleName("pointer");
			this.addClickHandler(new FavClickHandler(a, f));
			if (f) {
				this.setUrl("/images/heart-outlin.png");
			} else {
				this.setUrl("/images/heart.png");
			}
		}
	}

}
