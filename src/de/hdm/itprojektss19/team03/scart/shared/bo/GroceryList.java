package de.hdm.itprojektss19.team03.scart.shared.bo;
import java.util.ArrayList;

public class GroceryList {
	public static long serialVersionUID;
	
	private long groceryListID;
	private Group group;
	private String name;
	private ArrayList<Article> articles;
	
	public GroceryList getGroceryList() {
		
	}
	
	public GroceryList GetGroceryListById(long GroceryList) {
		
	}
	
	public ArrayList<GroceryList> getGroceryListByGroup(Group group) {
		
	}
	
	public ArrayList<Article> getArticles() {
		
	}
	
	public void setName(String name) {
		
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public static void setSerialVersionUID(long serialVersionUID) {
		GroceryList.serialVersionUID = serialVersionUID;
	}

	public long getGroceryListID() {
		return groceryListID;
	}

	public void setGroceryListID(long groceryListID) {
		this.groceryListID = groceryListID;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}


}
