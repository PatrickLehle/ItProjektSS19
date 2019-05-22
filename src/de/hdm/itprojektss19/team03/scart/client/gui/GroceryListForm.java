package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.bo.Article;

public class GroceryListForm extends VerticalPanel {

	HorizontalPanel hp = new HorizontalPanel();
	Button newArticleBtn = new Button(
			"<image src='/images/plusButton.png' width='16px' height='16px' align='center'/>");

	public GroceryListForm(final Article article) {

		ScrollPanel sc = new ScrollPanel();
		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);

		newArticleBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(new ArticleForm(article));
			}
		});
		
		newArticleBtn.setPixelSize(100, 60);
		newArticleBtn.setStyleName("button1");
		newArticleBtn.setTitle("add Article");
		
		hp.add(newArticleBtn);
		this.add(hp);
		this.add(sc);
	}

	public GroceryListForm(de.hdm.itprojektss19.team03.scart.client.gui.GroceryListForm groceryList) {
		// TODO Auto-generated constructor stub
	}

	public static void setSelectedGroceryListForm(GroceryListForm selectedGroceryList) {
		// TODO Auto-generated method stub
		
	}

	

}
