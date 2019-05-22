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
	
	public Retailer() {
		
	}

	public String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}

}
