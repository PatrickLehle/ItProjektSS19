package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.sql.Timestamp;

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
	
	
//CONSTRUCTORS==========================================================
public Article (String name, int quantity, String unit, int retailerId) {
		setName(name);
		setQuantity(quantity);
		setUnit(unit);
		setRetailerId(retailerId);
		
	}
	
//Default Constructor
public Article () {
		
	}
	
//METHODS===============================================================
	
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
