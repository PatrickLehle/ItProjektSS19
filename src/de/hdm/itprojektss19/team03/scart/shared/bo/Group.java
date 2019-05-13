package de.hdm.itprojektss19.team03.scart.shared.bo;
import java.util.ArrayList;

public class Group {
	public static long serialVersionUID;
	
	private long GroupID;
	private ArrayList<User> user;
	private String name;
	
	public Group getGroup() {
		
	}
	
	public Group getGroupByID(long groupID) {
		
	}
	
	public ArrayList<Group> getUserGroups(long userID) {
		
	}
	
	public void setUserGroup(long userID, long groupID) {
		
	}
	
	public void setGroupName(String name) {
		
	}
	
	
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public static void setSerialVersionUID(long serialVersionUID) {
		Group.serialVersionUID = serialVersionUID;
	}
	public long getGroupID() {
		return GroupID;
	}
	public void setGroupID(long groupID) {
		GroupID = groupID;
	}
	public ArrayList<User> getUser() {
		return user;
	}
	public void setUser(ArrayList<User> user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
