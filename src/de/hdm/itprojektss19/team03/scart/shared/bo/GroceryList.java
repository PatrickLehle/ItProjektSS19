package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.sql.Timestamp;
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
	private Timestamp creationDat;
	private Timestamp modDat;
	private int groupId;
	private Vector<Article> articles = new Vector<Article>();

	public String getGroceryListName() {
		return name;
	}

	public void setGroceryListName(String name) {
		this.name = name;
	}

	public void setCreationDat(Timestamp creationDat) {
		this.creationDat = creationDat;
	}
	
	public Timestamp getCreationDat() {
		return this.creationDat;
	}
	
	public void setModDat(Timestamp modDat) {
		this.modDat = modDat;
	}
	
	public Timestamp getModDat() {
		return this.modDat;
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public int getGroupId() {
		return this.groupId;
	}
	
	public Vector<Article> getArticles() {
		return articles;
	}

	public void setArticles(Vector<Article> articles) {
		this.articles = articles;
	}

}
