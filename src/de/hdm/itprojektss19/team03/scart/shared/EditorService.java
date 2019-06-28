package de.hdm.itprojektss19.team03.scart.shared;

import java.sql.Timestamp;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Entry;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryListArticle;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroupUser;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
//import de.hdm.itprojektss19.team03.scart.shared.bo.Unit;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Interface fuer den RPC-Service.
 *
 */
@RemoteServiceRelativePath("editorservice")
public interface EditorService extends RemoteService {

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl #init();
	 */
	public void init() throws IllegalArgumentException;

	// USER===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createUser(String emailAddress);
	 * @param username,
	 *            emailAddress Email Adresse des Users
	 * @return angelegter/ erstellter User
	 * @throws Exception
	 */
	public User createUser(User u) throws IllegalArgumentException, Exception;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createUser(String emailAddress);
	 * @param emailAddress
	 *            Email Adresse des Users
	 * @return angelegter/ erstellter UserF
	 * @throws DatabaseException
	 */
	public User createUser(String emailAdress) throws IllegalArgumentException, DatabaseException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteUser(User u);
	 * @param n
	 *            - aktuell eingeloggter User
	 * @throws DatabaseException
	 * 
	 */
	public void deleteUser(User u) throws IllegalArgumentException, DatabaseException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getUserById(int userId);
	 * @param userId
	 *            UserId des Users
	 * @return nutzer
	 * @throws DatabaseException
	 * 
	 */
	public User getUserById(int userId) throws IllegalArgumentException, DatabaseException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getUserByGmail(String email);
	 * @param email
	 *            Email des Email Users
	 * @return user
	 * @throws DatabaseException
	 * @throws IllegalArgumentException
	 * 
	 */
	public User getUserByGMail(String email)
			throws IllegalArgumentException, DatabaseException, IllegalArgumentException;

	/**
	 * 
	 * @param user
	 * @return gibt eigenes user Profil zurueck
	 * @throws IllegalArgumentException
	 */
	public User getOwnProfile(User user) throws IllegalArgumentException;

	// GROUP===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createUser(Group g);
	 * @param g
	 * @return angelegte/ erstellte Group
	 * @throws IllegalArgumentException
	 */
	public Group createGroup(Group g) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #saveGroup(Group g);
	 * @param g
	 * @throws IllegalArgumentException
	 */
	public void saveGroup(Group g) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteGroup(Group g);
	 * @param g
	 * @throws IllegalArgumentException
	 */
	public void deleteGroup(Group g) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getGroupById(int groupId);
	 * @param groupId
	 * @return gesuchte Gruppe via groupId
	 * @throws IllegalArgumentException
	 */
	public Group getGroupById(int groupId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getAllGroupsByUser(User u);
	 * @param u
	 * @return alle Gruppen eines Users
	 * @throws IllegalArgumentException
	 */
	public Vector<Group> getAllGroupsByUser(User u) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #addUserToGroup(User u, Group g);
	 * @param u
	 * @param g
	 * @throws IllegalArgumentException
	 */
	public GroupUser addUserToGroup(User user, Group group) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #leaveGroup(User u, Group g);
	 * @param u
	 * @param g
	 * @throws IllegalArgumentException
	 */
	public void leaveGroup(User u, Group g) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllGroups()
	 * @return
	 * @throws IllegalArgumentException
	 * @throws DatabaseException
	 */
	public Vector<Group> findAllGroups() throws IllegalArgumentException, DatabaseException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #statusSharingGroup(Vector<Group> result);
	 * @param result
	 * @return zeigt den Status der Gruppen an
	 */
	public Vector<Group> statusSharingGroup(Vector<Group> result);

	// GROUP-USER============================================================================
	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllGroupsByUserId(int id);
	 * @param id
	 * @return gibt alle Gruppen eines Users zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Group> findAllGroupsByUserId(int id) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getAllUsersByGroupId(int id);
	 * @param id
	 * @return gibt alle User einer Group zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<User> getAllUserByGroupId(int id) throws IllegalArgumentException;

	public void removeUserFromGroup(User u, Group g) throws IllegalArgumentException;

	// GROCERYLIST===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createGroceryList(String name, GroceryList gl);
	 * @param name
	 * @param gl
	 * @return angelegte/ erstellte GroceryList
	 * @throws IllegalArgumentException
	 */
	public GroceryList createGroceryList(GroceryList gl) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #saveGroceryList(GroceryList gl);
	 * @param gl
	 * @throws IllegalArgumentException
	 */
	public void saveGroceryList(GroceryList gl) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteGroceryList(GroceryList gl);
	 * @param gl
	 * @throws IllegalArgumentException
	 */
	public void deleteGroceryList(GroceryList gl) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getGroceryListByOwner(User u);
	 * @param u
	 * @return GroceryList des Erstellers
	 * @throws IllegalArgumentException
	 */
	public Vector<GroceryList> getGroceryListByOwner(User u) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #statusSharingGroceryList(Vector<GroceryList> result);
	 * @param result
	 * @return zeigt den Status einer geteilten GroceryList an
	 */
	public Vector<GroceryList> statusSharingGroceryList(Vector<GroceryList> result);

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getGroceryListById(Object groceryListId);
	 * @param groceryListId
	 * @return gibt gesuchte GroceryList via Id zurueck
	 * @throws IllegalArgumentException
	 */
	public GroceryList getGroceryListById(int groceryListId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getGroupByGroceryList(int groceryListId);
	 * @param groceryListId
	 * @return gibt Gruppe einer GroceryList als Ergebnis zurueck
	 * @throws IllegalArgumentException
	 */
	public Group getGroupByGroceryList(int groceryListId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllGroceryListByGroupId(int id);
	 * @return gibt alle GL's einer Grouppe via id zurueck
	 * @param id
	 * @throws IllegalArgumentException
	 */
	public Vector<GroceryList> findAllGroceryListByGroupId(int id) throws IllegalArgumentException;

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector<GroceryList> findAllGroceryListByUserId(int userId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllGroceryLists();
	 *
	 * @throws IllegalArgumentException
	 */
	public Vector<GroceryList> findAllGroceryLists() throws IllegalArgumentException;

	// GROCERYLIST-ARTICLE===============================================================
	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByGroceryList(int id);
	 * @param id
	 * @return gibt alle Article einer GroceryList aus.
	 * @throws IllegalArgumentException
	 */
	public Vector<Article> findAllArticleByGroceryListId(int id) throws IllegalArgumentException;

	// ARTICLE===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createArticle(Article a);
	 * @param a
	 * @return erstellt einen Articel
	 * @throws IllegalArgumentException
	 */
	public Article createArticle(Article a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #saveArticle(Article a);
	 * @param a
	 * @throws IllegalArgumentException
	 */
	public Article saveArticle(Article a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteArticle(Article a);
	 * @param a
	 * @throws IllegalArgumentException
	 */
	public void deleteArticle(Article a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getArticleById(int articleId);
	 * @param articleId
	 * @return gibt einen gesuchten Article via id zurueck
	 * @throws IllegalArgumentException
	 */
	public Article getArticleById(int articleId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticle()
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector<Article> findAllArticle() throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByGroupIdReport(int groupId)
	 * @param groupId
	 * @return gibt alle Artikel einer Gruppe zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Article> findAllArticleByGroupIdReport(int groupId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByOwnerId(int ownerId)
	 * @param ownerId
	 * @return gibt alle Artikel eines Owners zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Article> findAllArticleByOwnerId(int ownerId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByFavouriteTRUE()
	 * @return gibt alle Article die als Favoriten markiert sind zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Article> findAllArticleByFavouriteTRUE(Vector<Group> groups) throws IllegalArgumentException;

	// RETAILER===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createRetailer(Retailer r);
	 * @param r
	 * @return erstellt einen Retailer
	 * @throws IllegalArgumentException
	 */
	public Retailer createRetailer(Retailer r) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #saveRetailer(Retailer r);
	 * @param r
	 * @throws IllegalArgumentException
	 */
	public void saveRetailer(Retailer r) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteRetailer(Retailer r);
	 * @param r
	 * @throws IllegalArgumentException
	 */
	public void deleteRetailer(Retailer r) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getArticleByRetailer(Retailer r);
	 * @param r
	 * @return gibt einen Articel eines Retailers zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Article> getAllArticleByRetailer(Retailer r) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getArticleByDate(Date start, Date end);
	 * @param start
	 * @param end
	 * @return gibt einen/ mehrere Articel in einer Zeitspanne zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Article> getAllArticleByDate(Timestamp start, Timestamp end) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getArticleByDateRetailer(Date start, Date end, Retailer r);
	 * @param start
	 * @param end
	 * @param r
	 * @return gibt einen Article eines Retailers mit Zeitspanne zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Article> getAllArticleByDateRetailer(int id, Timestamp start, Timestamp end)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getRetailerById(int retailerId);
	 * @param retailerId
	 * @return gibt einen Retailer zurueck
	 * @throws IllegalArgumentException
	 */
	public Retailer getRetailerById(int retailerId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllRetailer();
	 * @return gibt alle Retailer zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Retailer> findAllRetailer() throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getAllRetailerByGroupId(int groupId);
	 * @param groupId
	 * @return gibt alle Retailer einer Gruppe via groupId zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Retailer> getAllRetailerByGroupId(int groupId) throws IllegalArgumentException;

	// UNIT===========================================================================

	// /**
	// * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	// * #createUnit(Unit u);
	// * @param u
	// * @return eine Unit wird erstellt
	// * @throws IllegalArgumentException
	// */
	// public Unit createUnit(Unit u) throws IllegalArgumentException;
	//
	// /**
	// * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	// * #saveUnit(Unit u);
	// * @param u
	// * @throws IllegalArgumentException
	// */
	// public void saveUnit(Unit u) throws IllegalArgumentException;
	//
	// /**
	// * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	// * #deleteUnit(Unit u);
	// * @param u
	// * @throws IllegalArgumentException
	// */
	// public void deleteUnit(Unit u) throws IllegalArgumentException;
	//
	// /**
	// * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	// * #getUnitById(int unitId);
	// * @param unitId
	// * @return es wird nach einer Unit via Id gesucht
	// * @throws IllegalArgumentException
	// */
	// public Unit getUnitById(int unitId) throws IllegalArgumentException;
	//
	// /**
	// * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	// * #getUnitByName(String unitName);
	// * @param unitName
	// * @return es wird eine Unit via name gesucht
	// * @throws IllegalArgumentException
	// */
	// public Unit getUnitByName(String unitName) throws IllegalArgumentException;

	// ENTRY===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createEntry(Entry e);
	 * @param e
	 * @return es wird ein Entry erstellt
	 * @throws IllegalArgumentException
	 */
	public Entry createEntry(Entry e) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #saveEntry(Entry e);
	 * @param e
	 * @throws IllegalArgumentException
	 */
	public void saveEntry(Entry e) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteEntry(Entry e);
	 * @param e
	 * @throws IllegalArgumentException
	 */
	public void deleteEntry(Entry e) throws IllegalArgumentException;

	// GroceryListArticle===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteEntry(Entry e);
	 * @param e
	 * @throws IllegalArgumentException
	 */
	public GroceryListArticle addArticleToGroceryList(GroceryList gl, Article a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #removeArticleFromGroceryList(GroceryList gl, Article a);
	 * @param gl,
	 *            a
	 * @throws IllegalArgumentException
	 */
	public void removeArticleFromGroceryList(GroceryList gl, Article a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByGroceryList(int groceryListId);
	 * @param gl,
	 *            a
	 * @return gibt alle Article einer GL zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Article> findAllArticleByGroceryList(GroceryList groceryList) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteArticleFromAllLists(Article a);
	 * @param a
	 * @throws IllegalArgumentException
	 */
	public void deleteArticleFromAllLists(Article a) throws IllegalArgumentException;

}
