package de.hdm.itprojektss19.team03.scart.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroceryList extends BusinessObject implements IsSerializable {

	private static final long serialVersionUID = 1L;
	private String name = "";
	private int groupId;
	private Article[] articles;

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

	public Article[] getArticles() {
		return articles;
	}

	public void setArticles(Article[] articles) {
		this.articles = articles;
	}

}
