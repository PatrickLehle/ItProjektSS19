package de.hdm.itprojektss19.team03.scart.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.ReportGenerator;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.Unit;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.*;

/**
 * 
 * @author bastiantilk
 * leaveUserGroup hat jetzt noch zusaetzlich zum Klassendiagramm den Parameter User u 
 * um zu wissen welcher USer aus welcher Gruppe austreten will
 * 
 */
public class EditorServiceImpl extends RemoteServiceServlet implements
EditorService{
	
	public EditorServiceImpl() {
		
	}
	
	public void init() {
		
	}
	public User createUser(User u) {
		
		return u;
	}
	
	public void deleteUser(User u) {
		
	}
	
	public Group createGroup(Group g) {
		
		return g;
		
	}
	
	public void leaveGroup(User u, Group g) {
		
	}
	
	public Group getGroupById(int GroupId) {
		
		return // Group Rueckgabevariable muss hier noch spezifiziert werden
	}
	
	public User getUserById(int id) {
		return // USer Rueckgabevariable muss hier noch spezifiziert werden
	}
	
	public ArrayList<User> getAllUserByGroup(Group g) {
		return // ArrayList Rueckgabevariable muss hier noch spezifiziert werden
	}
	
	public GroceryList createGroceryList(GroceryList gc) {
		return gc;
	}
	
	public void deleteGroceryList(GroceryList gc) {
		//Soll eine GroceryList ueberhaupt geloescht werden koennen?
	}
	
	public GroceryList archiveGroceryList(GroceryList gc) {
		return gc;
	}
	
	public ArrayList<GroceryList> getAllGroceryListByGroup(Group g) {
		return // ArrayList Rueckgabevariable muss hier noch spezifiziert werden
		
	}
	public Article getArticleById(Article a) {
		return a;
		
	}
	public Article getArticleByName(Article a) {
		return a;
	}
	public void deleteArticle(Article a) {
		
	}
	public Article updateArticle(Article a) {
		return a;
		
	}
	public ArrayList<Group> getUserGroups(long UserId) {
		return // ArrayList Rueckgabevariable muss hier noch spezifiziert werden		
	}
	public GroceryList getGroceryListById(GroceryList gl) {
		return gl;
	}
	
	public Group saveGroup(Group g) {
		return g;
	}
	
	public Article saveArticle(Article a) {
		return a;
		
	}
	
	public GroceryList saveGroceryList(GroceryList gl) {
		return gl;
	}
	
	public Retailer getRetailerById(Retailer r) {
		return r;
		
	}
	
	public void deleteRetailer(Retailer r) {
		//Soll man Retailer ueberhaupt loeschen koennen?
	}
	
	public Retailer saveRetailer(Retailer r) {
		return r;
		
	}
	public Retailer getRetailerByName(Retailer r) {
		return r;
		
	}
	
	public User getUserByName(User u) {
		return u;
		
	}
	public User getUSerByEmail(User u) {
		return u;
		
	}
	
	public ArrayList<Group> getAllGroupByUser(User u) {
		return // ArrayList Rueckgabevariable muss hier noch spezifiziert werden		
	}
	public void addUserToGroup(User u, Group g) {
		
	}
	
	public Unit createUnit(Unit un) {
		return un;
	}
	
	public void deleteUnit(Unit un) {
		
	}
	public Unit getUnitById(int id) {
		return // Unit Rueckgabevariable muss hier noch spezifiziert werden		
	}
	
	public Unit getUnitByName(String name) {
		return // Unit Rueckgabevariable muss hier noch spezifiziert werden		
	}
	public Entry createEntry(Entry e) {
		return e;
	}
	public void deleteEntry(Entry e) {
		
	}
	public Entry getEntryById(int id) {
		return // Entry Rueckgabevariable muss hier noch spezifiziert werden		
	}
}

