package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroceryList extends BusinessObject implements IsSerializable {

	private static final long serialVersionUID = 1L;
	private String name = "";
	private Group group = new Group();
	private Vector<Article> articles = new Vector<Article>();

	public GroceryList() {
		
	}
	public GroceryList(String name, Group g) {
		
	}
	public String getGroceryListName() {
		return name;
	}

	public void setGroceryListName(String name) {
		this.name = name;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroup(int groupId) {
		this.groupId = groupId;
	}

	public Vector<Article> getArticles() {
		return articles;
	}

	public void setArticles(Vector<Article> articles) {
		this.articles = articles;
	}

}
