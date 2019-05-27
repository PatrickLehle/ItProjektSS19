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
	private Unit unit;
	private Unit unitId;
	private Retailer retailer;
	private Retailer retailerId;
	private Timestamp creationDat;
	private Timestamp modDat;
	
//CONSTRUCTORS==========================================================
	
	public Article() {
		
	}
	
	public Article (String name, int quantity, Unit unit, Retailer retailer ) {
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
		this.retailer = retailer;
	}
	
//METHODS===============================================================
	
	
	public void setRetailerName(String name) {
		this.setRetailerName(name);
	}
	
	public String getRetailerName() {
			return retailer.getRetailerName();
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

	public String getUnitName() {
		return unit.getUnitName();
	}

	public void setUnitName(String name) {
		this.unit.setUnitName(name);
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

	public void setRetailerId(int retailerId) {
		this.retailerId.setRetailerId(retailerId);
		
	}

	public int getRetailerId() {
		return this.retailerId.getRetailerId();
	}

}
