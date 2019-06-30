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

	private String name;
	private int quantity;
	private String unit;
	private int retailerId;
	private int groupId;
	private String retailerName;
	private int ownerId;
	private Timestamp creationDat;
	private Timestamp modDat;
	private Timestamp delDat;
	private boolean fav;
	private Boolean checkBoolean;

	// CONSTRUCTORS==========================================================
	public Article(String name, int quantity, String unit, int retailerId) {
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
		this.retailerId = retailerId;
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

	public void setRetailerId(int retailerId) {
		this.retailerId = retailerId;

	}

	public int getRetailerId() {
		return this.retailerId;
	}

	public String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupID) {
		this.groupId = groupID;
	}

}
