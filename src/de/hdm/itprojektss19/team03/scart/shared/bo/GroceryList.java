package de.hdm.itprojektss19.team03.scart.shared.bo;


import com.google.gwt.user.client.rpc.IsSerializable;


public class GroceryList implements IsSerializable {
	
	private static final long serialVersionUID = 1L;
	
	private long groceryListId;
		
	public String groceryListName = "";
	
	/*
	 * ArrayList Articles fehlt noch!!!
	 */
	
	private long groupId;
	
	public long getGroupById(long groupId) {
		this.groupId = groupId;
		
		
	}
}
