package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *Erstellung eines <code>Group</code> Objekts.
 * @author TomHager, Patrick Lehle
 */

public class Group extends BusinessObject implements IsSerializable {
	
//INITIALIZATION=============================================================

	private static final long serialVersionUID = 1L;
	private String name = "";
	private boolean status;

//METHODS====================================================================
	
	
	public Group() {
		
	}
	
	public Group(String name) {
		setGroupName(name);
	}
	
	public String getGroupName() {
		return name;
	}

	/**
	 * Setzen eines Group Namens
	 * @param name beschreibt den Namen einer Gruppe
	 */
	public void setGroupName(String name) {
		this.name = name;
	}
	
	/**
	 * Setzen eines Statuses einer Group
	 * @param status beschreibt den Status einer Gruppe
	 */
	public void setStatus(boolean status) {
		//Hat noch keine sinnvolle implementierung <3
		this.status = status;
	}
	
	/**
	 * Holen eines Statuses einer Gruppe
	 * @return gibt den Status zurueck
	 */
	public boolean getStatus() {
		//Hat noch keine sinnvolle implementierung <3
		return status;
	}
	
	public int getId() {
		return this.id;
	}
	

}
