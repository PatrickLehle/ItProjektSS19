package de.hdm.itprojektss19.team03.scart.shared.bo;


import com.google.gwt.user.client.rpc.IsSerializable;


public class Unit implements IsSerializable {

/*
 * Units fuer GroceryList Entries, auf Datenbank fuer Groups abspeichern.
 * @author TomHager
 */
	
	private static final long serialVersionUID = 1L;
	
	private long unitId;
	
	public long getUnitById() {
		return unitId;
	}
	
	public long getUnitId() {
		return unitId;
	}

	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String unitName = "";
	
	public String getUnitName() {
		return unitName;
	}
	
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	
}
