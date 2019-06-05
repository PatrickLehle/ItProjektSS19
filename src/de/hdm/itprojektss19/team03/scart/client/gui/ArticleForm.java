package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;

public class ArticleForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	VerticalPanel vt = new VerticalPanel();
	HorizontalPanel hp = new HorizontalPanel();
	TextBox articleTextBox = new TextBox();
	TextBox quantityTextBox = new TextBox();
	Label newArticle = new Label("Artikel: ");
	Button cancelBtn = new Button("Abbrechen");
	Button addBtn = new Button("Hinzufügen");
	Article articleToDisplay = null;

	
	public void onLoad() {
		super.onLoad();
		
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

	
	public ArticleForm(Article article) {
		// TODO Auto-generated constructor stub
	}

	public static void setSelectedArticle(Article selectedArticle) {
		// TODO Auto-generated method stub

	}

}
