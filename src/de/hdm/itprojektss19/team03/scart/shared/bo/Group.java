package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Groups erstellen und verwalten.
 * @author TomHager
 */

public class Group extends BusinessObject implements IsSerializable {

	private static final long serialVersionUID = 1L;
	private Vector<User> users = new Vector<User>();
	private String name = "";
	private int groupId;
	private GroceryList groceryList;
	
	public Group() {
		
	}
	public Group(String name) {
		this.name = name;
	}

	public Vector<User> getUsers() {
		return users;
	}
	
	public void setUsers(Vector<User> users) {
		this.users = users;
	}
	
	public String getGroupName() {
		return name;
	}

	public void setGroupName(String name) {
		this.name = name;
	}

	public GroceryList getGroceryList() {
		return this.groceryList;
	}
	
	public int getGroupById() {
		return groupId;
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public void addUser(User u) {
		this.users.add(u);
	}
	
	public void removeUser(User u) {
		this.users.remove(u);
	}

}
