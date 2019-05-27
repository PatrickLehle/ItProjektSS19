package de.hdm.itprojektss19.team03.scart.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Retailer extends BusinessObject implements IsSerializable {

	/**
	 * Retailer fuer GroceryList Entries, auf Datenbank fuer Groups abspeichern.
	 * 
	 * @author TomHager
	 */

	private static final long serialVersionUID = 1L;
	private String retailerName = "";
	private int retailerId;
	
	public Retailer() {
		
	}

	public Retailer(String name) {
		
	}
	public String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}
	
	public void setRetailerId(int retailerId) {
		this.retailerId = retailerId;
	}
	
	public int getRetailerId() {
		return this.retailerId;
	}

}
