package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryListArticle;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public class ArticleForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	private GroceryList groceryList = null; //Muss bei Aufruf der ArticleForm-Seite uebergeben werden
	VerticalPanel vt = new VerticalPanel();
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel hp1 = new HorizontalPanel();
	HorizontalPanel hp2 = new HorizontalPanel();
	HorizontalPanel hp3 = new HorizontalPanel();
	HorizontalPanel hp4 = new HorizontalPanel();
	Article articleToDisplay = null;
	Button cancelBtn = new Button("Abbrechen");
	Button addBtn = new Button("Hinzuf�gen");

	Label newArticle = new Label("Artikel: ");
	Label newQuantity = new Label("Menge: ");
	Label newUnit = new Label("Mengeneinheit");
	Label newRetailer = new Label("Shop: ");

	TextBox articleTb = new TextBox();
	TextBox quantityTb = new TextBox();
	TextBox unitTb = new TextBox();
	ListBox retailerLb = new ListBox();

	public void onLoad() {
		super.onLoad();

		RootPanel.get("contentHeader").clear();
		RootPanel.get("content").clear();
		RootPanel.get("footer").clear();
		// eventuell Flextable nehmen um maximal 2 in eine Reihe zu bekommen
		RootPanel.get("content").add(vt);

		vt.add(hp);
		hp.add(newArticle);
		hp.add(articleTb);
		vt.add(hp1);
		hp1.add(newQuantity);
		hp1.add(quantityTb);
		vt.add(hp2);
		hp2.add(newUnit);
		hp2.add(unitTb);
		vt.add(hp3);
		hp3.add(newRetailer);
		hp3.add(retailerLb);

		vt.add(hp4);
		hp4.add(addBtn);
		addBtn.addClickHandler(new AddClickHandler());
		addBtn.setEnabled(true);

		hp4.add(cancelBtn);
		cancelBtn.addClickHandler(new DeleteClickHandler());
		cancelBtn.setEnabled(true);

		// ClientsideSettings.getEditorVerwaltung();
	}
	
	public void fillRetailerLb() {
		Vector<Retailer> vRetailer = new Vector<Retailer>();
		
		ev.findAllRetailer(new AsyncCallback<Vector<Retailer>>() {
			
		public void onFailure(Throwable caught) {
				Window.alert("Liste der Retailer konnte nicht geladen werden");
		}
		@Override
		public void onSuccess(Vector<Retailer> arg0) {
			// TODO Auto-generated method stub
			for(int i=0; i< vRetailer.size(); i++) {
				retailerLb.addItem(vRetailer.elementAt(i).getRetailerName());
			}
		}
});
		
		
	}

	/**
	 * ClickHandler fuer das Abbrechen beim Hinzufuegen eines neuen Artikels in
	 * einer GroceryList. RootPanel wird gecleared und GroceryList wird geoeffnet.
	 */
	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			RootPanel.get("content").clear();
			new GroceryListForm(groceryList);
		}
	}

	// ClickHandler fuer das Hinzufuegen eines Artikels in eine GroceryList
	private class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (articleTb != null && quantityTb != null && unitTb != null && retailerLb != null) {
				Article a = new Article();
				GroceryListArticle aGl = new GroceryListArticle(a.getId(), groceryList.getId());
				
				AsyncCallback<Article> asyncCallback = null;
				
				a.setName(articleTb.getText());
				a.setQuantity(Integer.parseInt(quantityTb.getText()));
				a.setUnit(unitTb.getText());
				a.setRetailerId(retailerLb.getItemCount());
				
				
				ev.createArticle(a, new AsyncCallback<Article>() {
					public void onFailure(Throwable caught) {
						Window.alert("Artikel konnte nicht erstellt werden");
					}
					public void onSuccess(Article arg0) {
						a = arg0;
						
						ev.addArticleToGroceryList(groceryList, a, new AsyncCallback<GroceryListArticle>() {
							
							public void onFailure(Throwable caught) {
								Window.alert("Artikel konnte nicht mit Einkaufsliste verbunden werden");
						}
						public void onSuccess(GroceryListArticle arg0) {
							aGl = arg0;
							Window.alert("Artikel "+a.getName()+" wurde  der Einkaufsliste "+groceryList.getGroceryListName()+" hinzugefügt");
						}
				});
					}
				});
				
				
				

				RootPanel.get("content").clear();
				
				new GroceryListForm(groceryList);
			} else {
				Window.alert("Bitte alle Angaben eintragen.");
			}
		}
	}

	public ArticleForm(User user) {
		// TODO Auto-generated constructor stub
	}

	public static void setSelectedArticle(Article selectedArticle) {
		// TODO Auto-generated method stub

	}

}
