package de.hdm.itprojektss19.team03.scart.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Unit extends BusinessObject implements IsSerializable {

	/*
	 * Units fuer GroceryList Entries, auf Datenbank fuer Groups abspeichern.
	 * 
	 * @author TomHager
	 */

	private static final long serialVersionUID = 1L;
	private String name = "";

	public String getUnitName() {
		return name;
	}

	public void setUnitName(String name) {
		this.name = name;
	}

}
