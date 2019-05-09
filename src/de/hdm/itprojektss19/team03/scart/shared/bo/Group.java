package de.hdm.itprojektss19.team03.scart.shared.bo;


import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Groups erstellen und verwalten.
 * @author TomHager
 */

public class Group implements IsSerializable {
	
	private static final long serialVersionUID = 1L;
	
	private long groupId;
	
	/*
	 * ArrayList fuer User in einer Group fehlt!
	 */
	private String groupName ="";

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public long getGroupById() {
		return groupId;
	}

}
