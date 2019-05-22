package de.hdm.itprojektss19.team03.scart.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.ReportGenerator;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Entry;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.Unit;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.thies.bankProjekt.shared.bo.Account;
import de.hdm.itprojektss19.team03.scart.server.db.UserMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroupMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroceryListMapper;
import de.hdm.itprojektss19.team03.scart.server.db.ArticleMapper;
import de.hdm.itprojektss19.team03.scart.server.db.UnitMapper;
import de.hdm.itprojektss19.team03.scart.server.db.EntryMapper;
import de.hdm.itprojektss19.team03.scart.server.db.RetailerMapper;
import de.hdm.itprojektss19.team03.scart.*;

/**
 * 
 * @author bastiantilk, PatrickLehle
 * Serverseitiger RPC-Service fuer den Editor.
 * 
 */
public class EditorServiceImpl extends RemoteServiceServlet implements
EditorService{
	
	public EditorServiceImpl() throws IllegalArgumentException {
	
		
	}
	
	/**
	 * Serialisierung
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Die Mapperklasse wird referenziert, die die <code>User</code>-mit der
	 * Datenbank vergleicht.
	 */
	private UserMapper uMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>Group</code>-mit der
	 * Datenbank vergleicht.
	 */
	private gMapper gMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>Retailer</code>-mit
	 * der Datenbank vergleicht.
	 */
	private RetailerMapper rMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die
	 * <code>ArticleMapper</code> mit der Datenbank vergleicht.
	 */
	private ArticleMapper aMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>Unit</code> mit
	 * der Datenbank vergleicht.
	 */
	private UnitMapper unMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>Entry</code> mit
	 * der Datenbank vergleicht.
	 */
	private EntryMapper eMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>GroceryList</code> mit
	 * der Datenbank vergleicht.
	 */
	private GroceryListMapper glMapper = null;
	
	
	/**
	 * *****************************************************************************
	 * **** Initialisierung
	 * *****************************************************************************
	 * ****
	 */
	
	public void init() throws IllegalArgumentException {
		
		this.uMapper = UserMapper.userMapper();
		this.gMapper = GroupMapper.groupMapper();
		this.glMapper = GroceryListMapper.groceryListMapper();
		this.aMapper = ArticleMapper.articleMapper();
		this.eMapper = EntryMapper.entryMapper();
		this.rMapper = RetailerMapper.retailerMapper();
		this.unMapper = UnitMapper.unitMapper();
		
	}
	public User createUser(String emailAdress) throws IllegalArgumentException{
		
		User u = new User();
		u.setEmailAdress(emailAdress);
		
		u.setId(1);
		
		return this.uMapper.insert(u);
		
		
	}
	

	public void deleteUser(User u) throws IllegalArgumentException{
		
		Vector<Group> groups = this.getGroupsof(u);
		
	    if (groups != null) {
	        for (group g : groups) {
	          this.delete(u);
	        }
	      }

	      this.uMapper.delete(u);
		
	}
	
	
	public User getUserById(int userId) throws IllegalArgumentException{
		
		return this.uMapper.findById(userId);
		
	}
	

	public User getUserByGMail(String email) throws IllegalArgumentException{
		
		return this.uMapper.findByEmail(email);
		
	}
	
	
	/**
	 * **********************************************************************
	 * Group
	 * **********************************************************************
	 */
	
	public Group createGroup(Group g) throws IllegalArgumentException{
		
		Group group = new Group();
		
		return this.gMapper.insert(group);
		
	}
	
	public void saveGroup(Group g) throws IllegalArgumentException{
		
		this.gMapper.update(g);
		
	}
	
	public void deleteGroup(Group g) throws IllegalArgumentException{
		
		this.gMapper.delete(g);
		
	}
	
	public Group getGrouById(int groupId) throws IllegalArgumentException{
		
		return this.uMapper.findById(userId);
	
	}
	
	public Vector<Group> getAllGroupsByUser(User u) throws IllegalArgumentException{
	
		return this.gMapper.findByUser();
		
	}

	public void addUserToGroup(User u, Group g) throws IllegalArgumentException{
		
		
		
	}
	
	public void leaveGroup(User u, Group g) throws IllegalArgumentException{
		
	}
	
	public Vector<Group> statusSharingGroup(Vector<Group> result) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * **********************************************************************
	 * GroceryList
	 * **********************************************************************
	 */
	
	public GroceryList createGroceryList(String name, GroceryList gl) throws IllegalArgumentException{
		
	}
	
	public void saveGroceryList(GroceryList gl) throws IllegalArgumentException{
		
	}
	
	public void deleteGroceryList(GroceryList gl) throws IllegalArgumentException{
		
	}
	
	public Vector<GroceryList> getGroceryListByOwner(User u) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	//public Vector<GroceryList> statusSharingGroceryList(Vector<GroceryList> result, AsyncCallback<Vector<GroceryList>> asyncCallback);

	public GroceryList getGroceryListById(Object groceryListId) throws IllegalArgumentException{
		
	}
	
	public Vector<GroceryList> statusSharingGroceryList(Vector<GroceryList> result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector<Group> getGroupByGroceryList(int groceryListId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * **********************************************************************
	 * Article
	 * **********************************************************************
	 */
	
	public Article createArticle(Article a) throws IllegalArgumentException{
		
	}
	
	public void saveArticle(Article a) throws IllegalArgumentException{
		
	}
	
	public void deleteArticle(Article a) throws IllegalArgumentException{
		
	}
	
	public Article getArticleById(int articleId) throws IllegalArgumentException{
		
	}
	
	/**
	 * **********************************************************************
	 * Retailer
	 * **********************************************************************
	 */
	
	public Retailer createRetailer(Retailer r) throws IllegalArgumentException{
		
	}
	
	public void saveRetailer(Retailer r) throws IllegalArgumentException{
		
	}
	
	public void deleteRetailer(Retailer r) throws IllegalArgumentException{
		
	}
	
	public Article getArticleByRetailer(Retailer r) throws IllegalArgumentException{
		
	}
	
	public Vector<Article> getArticleByDate(Date start, Date end) throws IllegalArgumentException{
		
	}
	
	public Vector<Article> getArticleByDateRetailer(Date start, Date end, Retailer r) throws IllegalArgumentException{
		
	}
	
	public Retailer getRetailerById(int retailerId) throws IllegalArgumentException{
		
	}
	
	/**
	 * **********************************************************************
	 * Unit
	 * **********************************************************************
	 */
	
	public Unit createUnit(Unit u) throws IllegalArgumentException{
		try {
			int temp1=0;
			
			for(int i=0; i<= unMapper.findAll().size(); i++) {
				if(unMapper.findAll().elementAt(i).getUnitName() == u.getUnitName()) {
					temp1++;
				}
			}
				if(temp1 ==0) {
					unMapper.insert(u);
					//Rueckgabewert vom Mapper wird noch nicht verarbeitet
				}
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
				
			
	
	public void saveUnit(Unit u) throws IllegalArgumentException{
		
	}
	
	public void deleteUnit(Unit u) throws IllegalArgumentException{
		
	}
	
	public Unit getUnitById(int unitId) throws IllegalArgumentException{
		
	}
	
	public Unit getUnitByName(String unitName) throws IllegalArgumentException{
		
	}
	
	/**
	 * **********************************************************************
	 * Entry
	 * **********************************************************************
	 */
	
	public Entry createEntry(Entry e) throws IllegalArgumentException{
		
	}
	
	public void saveEntry(Entry e) throws IllegalArgumentException{
		
	}
	
	public void deleteEntry(Entry e) throws IllegalArgumentException{
		
		
	}

	
}

