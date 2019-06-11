package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;

public class GroceryListForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	// braucht man hier ein VerticalPanel, die Artikel werden sowiso in hp
	// angezeigt?
	VerticalPanel vt = new VerticalPanel();
	HorizontalPanel hpTitle = new HorizontalPanel();
	HorizontalPanel hpButtons = new HorizontalPanel();

	Button addBtn = new Button("<image src='/images/plusButton.png' width='16px' height='16px' align='center'/>");
	Button editBtn = new Button();
	Button deleteBtn = new Button();

	CellTable<Article> ArticleCt = new CellTable<Article>();

	// Name der GroceryList wird ausgegeben
	Label titelLabel = new Label();

	ScrollPanel sc = new ScrollPanel();

	public void onLoad() {
		super.onLoad();

		TextColumn<Article> articleName = new TextColumn<Article>() {
			@Override
			public String getValue(Article article) {
				return article.getName();
			}
		};
		Column<Article, Number> articleQuantity = new Column<Article, Number>(new NumberCell()) {
			@Override
			public Integer getValue(Article article) {
				return article.getQuantity();
			}
		};

		TextColumn<Article> articleUnit = new TextColumn<Article>() {
			@Override
			public String getValue(Article article) {
				return article.getUnit();
			}
		};
		TextColumn<Retailer> articleRetailer = new TextColumn<Retailer>() {
			@Override
			public String getValue(Retailer retailer) {
				return retailer.getRetailerName();
			}
		};

		RootPanel.get("contentHeader").clear();
		RootPanel.get("content").clear();
		RootPanel.get("footer").clear();

		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);

		// Titel Label wird in Horitontales Panel eingefuegt
		hpTitle.add(titelLabel);

		// CellTable wird in das Scroll Panel hinzugefuegt
		sc.add(ArticleCt);

		// Buttons werden dem horizontal Panel unten hinzugefuegt
		hpButtons.add(addBtn);
		hpButtons.add(editBtn);
		hpButtons.add(deleteBtn);

		vt.add(hpTitle);
		vt.add(sc);

		Article a = new Article();
		Retailer r = new Retailer();
		ArticleCt.addColumn(articleName, "Artikel Name");
		ArticleCt.insertColumn(0, articleName, a.getName());
		// ArticleCt.addColumn(articleQuantity, "Menge");
		// ArticleCt.insertColumn(1, articleQuantity, a.getQuantity());
		ArticleCt.addColumn(articleUnit, "Mengeneinheit");
		ArticleCt.insertColumn(2, articleUnit, a.getUnit());
		ArticleCt.addColumn(articleRetailer, "Laden");
		ArticleCt.insertColumn(3, articleRetailer, r.getRetailerName());

		vt.add(ArticleCt);
		vt.add(hpButtons);

		RootPanel.get("content").add(vt); // Fraglich ob man in dieser Klasse auch das RootPanel veraendern sollte, oder
											// ob das die "Seiten"-Klasse macht

		// Vector in das HorizontalePanel hinzufuegen/ Artikel als Liste anzeigen
		// hp.getElement(articleList);
		// getArticles().iterator().next().getName());
		// if bedingung

		// hpF.add(editBtn);
		editBtn.addClickHandler(new EditClickHandler());
		editBtn.setEnabled(true);
		// hpF.add(deleteBtn);
		deleteBtn.addClickHandler(new DeleteClickHandler());
		deleteBtn.setEnabled(true);
		// hpF.add(addBtn);
		addBtn.addClickHandler(new AddClickHandler());
		addBtn.setEnabled(true);
		/**
		 * newArticleBtn.setPixelSize(100, 60); newArticleBtn.setStyleName("button1");
		 * newArticleBtn.setTitle("add Article");
		 */

	}

	private class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
		}
	}

	// Oeffnet ArticleForm Panel zum Hinzufuegen eines Artikels
	private class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {

		}
	}

	public GroceryListForm(de.hdm.itprojektss19.team03.scart.client.gui.GroceryListForm groceryList) {
		// TODO Auto-generated constructor stub
	}

	public static void setSelectedGroceryListForm(GroceryListForm selectedGroceryList) {
		// TODO Auto-generated method stub

	}

}
