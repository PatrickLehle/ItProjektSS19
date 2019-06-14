package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;

public class GroceryListForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditor();

	// braucht man hier ein VerticalPanel, die Artikel werden sowiso in hp
	// angezeigt?
	VerticalPanel vt = new VerticalPanel();
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel hpF = new HorizontalPanel();

	Button addBtn = new Button(
			"<image src='/images/plusButton.png' width='16px' height='16px' align='center'/>");
	Button editBtn = new Button();
	Button deleteBtn = new Button();
	
	// private Vector<Article> articleList = new Vector<Article>();

	public void onLoad() {
		super.onLoad();

		RootPanel.get("contentHeader").clear();
		RootPanel.get("content").clear();
		RootPanel.get("footer").clear();

		ScrollPanel sc = new ScrollPanel();
		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);
		vt.add(sc);

		RootPanel.get("content").add(vt);
		vt.add(this.hp);

		// Vector in das HorizontalePanel hinzufuegen/ Artikel als Liste anzeigen
		// hp.getElement(articleList);
		// getArticles().iterator().next().getName());
		// if bedingung

		hpF.add(editBtn);
		editBtn.addClickHandler(new EditClickHandler());
		editBtn.setEnabled(true);
		hpF.add(deleteBtn);
		deleteBtn.addClickHandler(new DeleteClickHandler());
		deleteBtn.setEnabled(true);
		hpF.add(addBtn);
		addBtn.addClickHandler(new AddClickHandler());
		addBtn.setEnabled(true);
		/**
		 * newArticleBtn.setPixelSize(100, 60); 
		 * newArticleBtn.setStyleName("button1");
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
