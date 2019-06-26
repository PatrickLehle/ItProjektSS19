package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * @author Tom
 * @author bastiantilk
 * @author Marco
 *
 */
public class ShoppingListForm extends HorizontalPanel {

	public ShoppingListForm(User u, GroceryList gl) {
		user = u;
		groceryList = gl;
	}

	public void onLoad() {
		super.onLoad();
		retrieveArticles();
	}

	private EditorServiceAsync editorService = GWT.create(EditorService.class);

	private User user;
	private GroceryList groceryList;

	private void fillTable(String retailer, Vector<Article> articles) {

	}

	private Vector<Article> retrieveArticles() {
		Window.alert(groceryList.getArticles().get(0).getName());
		return new Vector<Article>();
	}

}
