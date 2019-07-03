package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Ein Article besitzt einen Namen, eine Quantity mit ihrer Unit und einen
 * dazugehoerigen Retailer
 * 
 * @see <code>Retailer</code>
 * @see <code>Unit</code>
 * @author PatrickLehle
 *
 */
public class Article extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private String name;
	private int quantity;
	private String unit;
	private int retailerId;
	private int groupId;
	private String retailerName;
	private int ownerId;
	private Retailer retailer = new Retailer();
	private Timestamp creationDat;
	private Timestamp modDat;
	private Timestamp delDat;
	private boolean fav;
	private Boolean checkBoolean; // Boolean ob der Artikel gekauft wurde oder nicht

	// CONSTRUCTORS==========================================================
	public Article(String name, int quantity, String unit) {
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
		creationDat.setTime(new Date().getTime());
		modDat.setTime(new Date().getTime());
		setFav(false);
		setCheckBoolean(false);
	}

	// Default Constructor
	public Article() {

	}

	// METHODS===============================================================
	public int getId() {
		return this.id;
	}

	public void setCheckBoolean(Boolean checkBoolean) {
		this.checkBoolean = checkBoolean;
	}

	public Boolean getCheckBoolean() {
		return this.checkBoolean;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit() {
		return this.unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Timestamp getCreationDat() {
		return creationDat;
	}

	public void setCreationDat(Timestamp i) {
		this.creationDat = i;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public Timestamp getModDat() {
		return modDat;
	}

	public void setModDat(Timestamp modDat) {
		this.modDat = modDat;
	}

	public void setDelDat(Timestamp delDat) {
		this.delDat = delDat;
	}

	public Timestamp getDelDat() {
		return delDat;
	}

	public void setFav(boolean fav) {
		this.fav = fav;
	}

	public boolean getFav() {
		return this.fav;
	}

	// RETAILER======================================================

	// }

	// public int getRetailerId() {
	// return this.retailerId;
	// }

	public String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public void setRetailerId(int retailerId) {
		this.retailerId = retailerId;
		this.retailer.setId(retailerId);
	}

	public int getRetailerId() {
		return retailer.getId();
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupID) {
		this.groupId = groupID;
	}

	public void setRetailerReport(Retailer retailer) {
		this.retailer = retailer;
	}

	public Retailer getRetailerReport() {
		return retailer;
	}

	public void setRetailerIdReport(int retailerId) {
		this.retailer.setId(retailerId);
	}

	public int getRetailerIdReport() {
		return retailer.getId();
	}

}
