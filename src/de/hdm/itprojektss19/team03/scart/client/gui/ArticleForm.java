package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public class ArticleForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	VerticalPanel vt = new VerticalPanel();
	HorizontalPanel hp = new HorizontalPanel();
	Button cancelBtn = new Button("Abbrechen");
	Button addBtn = new Button("Hinzufügen");
	Article articleToDisplay = null;
	
	Label newArticle = new Label("Artikel: ");
	Label newQuantity = new Label("Menge: ");
	Label newUnit = new Label("Mengeneinheit");	
	Label newRetailer = new Label("Shop: ");
	
	TextBox articleTb = new TextBox();
	TextBox quantityTb = new TextBox();
	TextBox unitTb = new TextBox();
	TextBox retailerTb = new TextBox();
	
	public void onLoad() {
		super.onLoad();
		
		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		RootPanel.get("footer").clear();
		
		//RootPanel.get("contentHeader").add("Neuer Artikel");
		RootPanel.get("content").add(newArticle);
		RootPanel.get("content").add(newQuantity);
		RootPanel.get("content").add(newUnit);
		RootPanel.get("content").add(newRetailer);
		
		
		
		
		vt.add(addBtn);
		addBtn.addClickHandler(new AddClickHandler());
		addBtn.setEnabled(true);
		
		vt.add(cancelBtn);
		cancelBtn.addClickHandler(new DeleteClickHandler());
		cancelBtn.setEnabled(true);
		
		this.add(hp);
		
	}
		
	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (articleToDisplay == null) {
				
			} else {
			}
		}
	}
	private class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (articleToDisplay != null) {	
				
			} else {
				Window.alert("Bitte Artikel eintragen.");
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
