package de.hdm.itprojektss19.team03.scart.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Retailer extends BusinessObject implements IsSerializable {

	/**
	 * Retailer fuer GroceryList Entries, auf Datenbank fuer Groups abspeichern.
	 * 
	 * @author TomHager
	 */
	// INITIALIZATION=========================================================================

	private static final long serialVersionUID = 1L;
	private String retailerName;
	private int retailerId;
	private Group group;
	private User user;

	// CONSTRUCTORS===========================================================================

	public Retailer() {

	}

	public Retailer(String name, int retailerId) {
		this.retailerName = name;
		this.retailerId = retailerId;
	}

	public Retailer(String name) {
		this.retailerName = name;
	}

	// METHODS=================================================================================

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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// GROUP====================================================================================

}
