package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * Realisierung einer GroceryList. Ein GroceryList-Objekt
 * zeichnet sich durch seine Gruppe mit einem Artikel,
 * seiner Menge und dem dazugehoerigen Retailer aus.
 * 
 * @see <code>BusinessObject</code>
 * @see <code>Article</code>
 * @see <code>Retailer</code>
 * 
 */
public class GroceryList extends BusinessObject implements IsSerializable {

	private static final long serialVersionUID = 1L;
	private String name = "";
	private Group group;
	private Vector<Article> articles = new Vector<Article>();
	private int groceryListId;
	
	public void setGroceryListId(int groceryListId) {
		this.groceryListId = groceryListId;
	}
	
	public int getGroceryListId() {
		return this.groceryListId;
	}

	public String getGroceryListName() {
		return name;
	}

	public void setGroceryListName(String name) {
		this.name = name;
	}

	public String getGroupName() {
		return group.getGroupName();
	}

	public void setGroupName(String group) {
		this.group.setGroupName(group);
	}

	public Vector<Article> getArticles() {
		return articles;
	}

	public void setArticles(Vector<Article> articles) {
		this.articles = articles;
	}

}
