package de.hdm.itprojektss19.team03.scart.shared.bo;


import com.google.gwt.user.client.rpc.IsSerializable;


public class Retailer implements IsSerializable {

/*
 * Retailer fuer GroceryList Entries, auf Datenbank fuer Groups abspeichern.
 * @author TomHager
 */
	
	private static final long serialVersionUID = 1L;
	
	private long retailerId;
	
	public long getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(long retailerId) {
		this.retailerId = retailerId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getRetailerById() {
		return retailerId;
	}
	
	private String retailerName = "";
	
	public String getRetailerName() {
		return retailerName;
	}
	
	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}
	
}
