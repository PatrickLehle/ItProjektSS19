package de.hdm.itprojektss19.team03.scart.shared.bo;
<<<<<<< HEAD
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
=======

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.IsSerializable;

public class GroceryList extends BusinessObject implements IsSerializable {

	private static final long serialVersionUID = 1L;
	private String name = "";
	private Group group = new Group();
	private ArrayList<Article> articles = new ArrayList<Article>();

	public String getGroceryListName() {
		return name;
	}

	public void setGroceryListName(String name) {
		this.name = name;
>>>>>>> dev
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

<<<<<<< HEAD
	public String getName() {
		return name;
=======
	public ArrayList<Article> getArticles() {
		return articles;
>>>>>>> dev
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}

<<<<<<< HEAD

=======
>>>>>>> dev
}
