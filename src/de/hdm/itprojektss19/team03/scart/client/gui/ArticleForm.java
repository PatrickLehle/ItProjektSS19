package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.ListBox;

import de.hdm.itprojektss19.team03.scart.shared.bo.Article;

public class ArticleForm extends VerticalPanel {

	TextBox articleTextBox = new TextBox();
	TextBox quantityTextBox = new TextBox();
	//ListBox unitListBox = new ListBox();
	Label newArticle = new Label("Artikel: ");
	Button cancelButton = new Button("Abbrechen");
	Button addButton = new Button("Artikel Hinzuf�gen");
	
	
	
	public ArticleForm(Article article) {
		// TODO Auto-generated constructor stub
	}

	public static void setSelectedArticle(Article selectedArticle) {
		// TODO Auto-generated method stub

	}

}
