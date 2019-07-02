package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung einer GroceryList. Ein GroceryList-Objekt zeichnet sich durch
 * seine Gruppe mit einem Artikel, seiner Menge und dem dazugehoerigen Retailer
 * aus.
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
	private int ownerId;
	private int groupId;
	private String groupName;
	private Vector<Article> articles = new Vector<Article>();

// CONSTRUCTORS==========================================================
		public GroceryList(String name, int quantity, int ownerId, int groupId, String groupname) {
			
			this.name = name;
			creationDat.setTime(new Date().getTime());
			modDat.setTime(new Date().getTime());
			this.ownerId = ownerId;
			this.groupId = groupId;
			
		}

// Default Constructor
		public GroceryList() {

		}
	
// Methods	
		
	/** Setzt die Modifikationszeit auf die aktuelle Zeit
	 * 
	 * @return (neu gesetzter) Modifikations-Timestamp
	 */
	public Timestamp getNewModDat() {
		modDat.setTime(new Date().getTime());
		return this.modDat;
	}		
		
//Getter und Setter		
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
	
	

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getOwnerId() {
		return this.ownerId;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
