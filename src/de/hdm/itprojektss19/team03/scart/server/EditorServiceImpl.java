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
		this.glMapper = GroceryListMapper.grocerylistMapper();
		this.aMapper = ArticleMapper.articleMapper();
		this.eMapper = EntryMapper.entryMapper();
		this.rMapper = RetailerMapper.retailerMapper();
		this.unMapper = UnitMapper.unitMapper();
		
	}
	public User createUser(String username, String emailAdress) throws IllegalArgumentException{
		//E-Mail und Username muss zunaechst ueber GUI abgefragt werden
		
		
		try {
			
			Vector<User> results= uMapper.findByUserEmail(emailAdress);
			
			if(results.size() > 1) {
				
				//Email bereits vorhanden
			} else {
				User temp = new User(username, emailAdress);
				uMapper.insert(temp);
				//Ausgabe der Rueckgabe aus der insert Funktion fehlt
			}
		
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		
		
	}
	

	public void deleteUser(User u) throws IllegalArgumentException{
		//Sollte es ueberhaupt erlaubt sein einen User zu loeschen?
		
		try { 
			uMapper.delete(u);
			
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	
	public User getUserById(int userId) throws IllegalArgumentException{
		try { 
			Vector<User> foundUsers = uMapper.findbyUserId(userId);
			
			//Fehlt noch: Ausgabe des foundUsers Vektor
			
			
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	

	public User getUserByGMail(String email) throws IllegalArgumentException{
		try { 
			Vector<User> foundUsers = uMapper.findByUserEmail(email);
			
			//Fehlt noch: Ausgabe des 'foundUsers' Vektor
			
			
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * **********************************************************************
	 * Group
	 * **********************************************************************
	 */
	
	public Group createGroup(Group g) throws IllegalArgumentException{
		
	}
	
	public void saveGroup(Group g) throws IllegalArgumentException{
		
	}
	
	public void deleteGroup(Group g) throws IllegalArgumentException{
		
	}
	
	public Group getGrouById(int groupId) throws IllegalArgumentException{
		
	}
	
	public Vector<Group> getAllGroupsByUser(User u) throws IllegalArgumentException{
		
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
		//Input fuer Article Attribute muss noch erledigt werden
		
		
		try {
			Article temp = new Article(); //Mit dem Input der Attribute muss hier ein neues Object mit den Attributen erstellt werden
			aMapper.insert(temp);
			//Ausgabe der Rueckgabe aus der insert Funktion fehlt
					
		
		} catch (IllegalArgumentException e) {
					e.printStackTrace();
		}
	}
	
	public void saveArticle(Article a) throws IllegalArgumentException{
		
	}
	
	public void deleteArticle(Article a) throws IllegalArgumentException{
		//Sollten Articles ueberhaupt geloescht werden
		
		try { 
			aMapper.delete(a);
			//Erfolgts Message fuer erfolgreiches Loeschen
			
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}
	
	public Article getArticleById(int articleId) throws IllegalArgumentException{
		aMapper.findByKey(articleId); //Ausgabe fuer diese Article-Objekt muss noch hinzugefuegt werden
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
	@Override
	public User createUser(String emailAdress) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	
}

