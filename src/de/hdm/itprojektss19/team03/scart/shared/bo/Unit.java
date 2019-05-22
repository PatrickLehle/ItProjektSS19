package de.hdm.itprojektss19.team03.scart.shared.bo;
<<<<<<< HEAD
import java.util.ArrayList;

public class Unit {
	public static long serialVersionUID;
	
	private long unitID;
	private String name;
	
	public Unit getUnit() {
		
	}
	
	public Unit getUnitByID(long unitID) {
		
	}
	
	public void setName(String name) {
		
	}
	
	
	
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public static void setSerialVersionUID(long serialVersionUID) {
		Unit.serialVersionUID = serialVersionUID;
	}
	public long getUnitID() {
		return unitID;
	}
	public void setUnitID(long unitID) {
		this.unitID = unitID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
=======

import com.google.gwt.user.client.rpc.IsSerializable;

public class Unit extends BusinessObject implements IsSerializable {

	/**
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

>>>>>>> dev
}
