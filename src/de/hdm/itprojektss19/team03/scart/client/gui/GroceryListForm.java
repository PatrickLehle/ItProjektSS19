package de.hdm.itprojektss19.team03.scart.client.gui;

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


	}

	
//	void setArticle(Article a) {
//			if (a == null) {
//				cancelBtn.setEnabled(true);
//				addBtn.setEnabled(true);
//				firstNameTextBox.setText(customerToDisplay.getFirstName());
//				lastNameTextBox.setText(customerToDisplay.getLastName());
//				idValueLabel.setText("Kunde: " + Integer.toString(customerToDisplay.getId()));
//			} else {
//				firstNameTextBox.setText("");
//				lastNameTextBox.setText("");
//				idValueLabel.setText("Kunde: ");
//				deleteButton.setEnabled(false);
//				newButton.setEnabled(false);
//		}
//		}
		
	public GroceryListForm(de.hdm.itprojektss19.team03.scart.client.gui.GroceryListForm groceryList) {
		// TODO Auto-generated constructor stub
	}

			public static void setSelectedGroceryListForm(GroceryListForm selectedGroceryList) {
				// TODO Auto-generated method stub

			}

		
	
}
