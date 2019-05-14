package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.IsSerializable;

public class GroceryList extends BusinessObject implements IsSerializable {

	private static final long serialVersionUID = 1L;
	private String name = "";
	private int groupId;
	private ArrayList<Integer> articleIds = new ArrayList<Integer>();

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

	public ArrayList<Integer> getArticles() {
		return articleIds;
	}

	public void setArticles(ArrayList<Integer> articleIds) {
		this.articleIds = articleIds;
	}

}
