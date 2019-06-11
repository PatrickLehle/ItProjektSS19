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

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	//braucht man hier ein VerticalPanel, die Artikel werden sowiso in hp angezeigt?
	VerticalPanel vt = new VerticalPanel();
	HorizontalPanel hp = new HorizontalPanel();
	Button newArticleBtn = new Button(
			"<image src='/images/plusButton.png' width='16px' height='16px' align='center'/>");
	//private Vector<Article> articleList = new Vector<Article>();

	public void onLoad() {
		super.onLoad();

		RootPanel.get("contentHeader").clear();
		RootPanel.get("content").clear();
		RootPanel.get("footer").clear();

		ScrollPanel sc = new ScrollPanel();
		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);

		RootPanel.get("content").add(vt);
		vt.add(this.hp);
		
		//Vector in das HorizontalePanel hinzufuegen/ Artikel als Liste anzeigen 
		//hp.getElement(articleList);
		//getArticles().iterator().next().getName());
		//if bedingung
		
		vt.add(newArticleBtn);
		newArticleBtn.setPixelSize(100, 60);
		newArticleBtn.setStyleName("button1");
		newArticleBtn.setTitle("add Article");

	}

	public GroceryListForm(de.hdm.itprojektss19.team03.scart.client.gui.GroceryListForm groceryList) {
		// TODO Auto-generated constructor stub
	}

	public static void setSelectedGroceryListForm(GroceryListForm selectedGroceryList) {
		// TODO Auto-generated method stub

	}

}
