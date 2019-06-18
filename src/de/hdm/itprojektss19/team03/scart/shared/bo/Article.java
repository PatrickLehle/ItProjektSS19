package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Ein Article besitzt einen Namen, eine Quantity mit ihrer Unit und einen dazugehoerigen Retailer
 * 
 * @see <code>Retailer</code>
 * @see <code>Unit</code>
 * @author PatrickLehle
 *
 */
public class Article extends BusinessObject {

	private static final long serialVersionUID = 1L;
	private String name = "";
	private int quantity;
	private String unit;
	private int retailerId;
	private Timestamp creationDat;
	private Timestamp modDat;
	private Boolean checkBoolean;
	
	
//CONSTRUCTORS==========================================================
	public Article(String name, int quantity, String unit, int retailerId) {
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
		this.retailerId = retailerId;
		creationDat.setTime(new Date().getTime());
		modDat.setTime(new Date().getTime());
		setCheckBoolean(false);
	}
	
//Default Constructor
	public Article () {
		
	}
	
//METHODS===============================================================
	
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


	public Timestamp getModDat() {
		return modDat;
	}

	public void setModDat(Timestamp modDat) {
		this.modDat = modDat;
	}

//RETAILER======================================================
	
	public void setRetailerId(int retailerId) {
		this.retailerId =retailerId;
		
	}

	public int getRetailerId() {
		return this.retailerId;
	}

}
