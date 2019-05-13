package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Groups erstellen und verwalten.
 * @author TomHager
 */

public class Group extends BusinessObject implements IsSerializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<User> users = new ArrayList<User>();
	private String name = "";

	public ArrayList<User> getUsers() {
		return users;
	}
	
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	public String getGroupName() {
		return name;
	}

	public void setGroupName(String name) {
		this.name = name;
	}

	public Object getGroceryListId() {
		// TODO Auto-generated method stub
		return null;
	}

}
