package de.hdm.itprojektss19.team03.scart.shared;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
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
	public String generateIdenticons(String text, int image_width, int image_height)
			throws IllegalArgumentException, IOException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl #init();
	 */
	public void init() throws IllegalArgumentException;

	// USER===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createUser(String emailAddress);
	 * @param u neuer User der erstellt wird
	 * @return angelegter/ erstellter User
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 * @throws Exception Wenn ein unerwarteter Fehler eintritt
	 */
	public User createUser(User u) throws IllegalArgumentException, Exception;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createUser(String emailAddress);
	 * @param emailAdress Email Adresse des angelegten Users
	 * @return angelegter/ erstellter User
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public User createUser(String emailAdress) throws IllegalArgumentException, DatabaseException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteUser(User u);
	 * @param u User der geloescht wird
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public void deleteUser(User u) throws IllegalArgumentException, DatabaseException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getUserById(int userId);
	 * @param userId UserId des Users
	 * @return nutzer
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 * 
	 */
	public User getUserById(int userId) throws IllegalArgumentException, DatabaseException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getUserByGmail(String email);
	 * @param email Email des Email Users
	 * @return user
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 * 
	 */
	public User getUserByGMail(String email)
			throws IllegalArgumentException, DatabaseException, IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getOwnProfile(User user);
	 * @param user User der auf sein eigenes Profil zugreifen moechte
	 * @return gibt eigenes user Profil zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public User getOwnProfile(User user) throws IllegalArgumentException;

	// GROUP===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createUser(Group g);
	 * @param g Gruppe die erstellt wird
	 * @return angelegte/ erstellte Group
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Group createGroup(Group g) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #saveGroup(Group g);
	 * @param g Gruppe die gespeichert wird
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public void saveGroup(Group g) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteGroup(Group g);
	 * @param g Gruppe die geloescht wird
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public void deleteGroup(Group g) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getGroupById(int groupId);
	 * @param groupId Die Gruppen Id die die Eindeutigkeit einer Gruppe beschreibt
	 * @return gesuchte Gruppe via groupId
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Group getGroupById(int groupId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getAllGroupsByUser(User u);
	 * @param u beschreibt ein User Objekt
	 * @return alle Gruppen eines Users
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Group> getAllGroupsByUser(User u) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #addUserToGroup(User user, Group group);
	 * @param user User der in eine Gruppe moechte
	 * @param group Gruppe die den ausgewaehlten user aufnimmt
	 * @return Ein User wird einer Gruppe hinzugefuegt
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public GroupUser addUserToGroup(User user, Group group) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #leaveGroup(User u, Group g);
	 * @param u User der eine Gruppe verlassen moechte
	 * @param g Gruppe die verlassen wird vom User
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public void leaveGroup(User u, Group g) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllGroups()
	 * @return Gibt einen Vector mit allen Gruppen zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Group> findAllGroups() throws IllegalArgumentException, DatabaseException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #statusSharingGroup(Group result);
	 * @param result beschreibt ob eine Gruppe geteilt wird oder nicht
	 * @return zeigt den Status der Gruppen an
	 */
	public Vector<Group> statusSharingGroup(Vector<Group> result);

	// GROUP-USER============================================================================
	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllGroupsByUserId(int id);
	 * @param id beschreibt die Eindeutigkeit eines Users
	 * @return gibt alle Gruppen eines Users zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Group> findAllGroupsByUserId(int id) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getAllUsersByGroupId(int id);
	 * @param id beschreibt die Eindeutigkeit einer Gruppe
	 * @return gibt alle User einer Group zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<User> getAllUserByGroupId(int id) throws IllegalArgumentException;

	public void removeUserFromGroup(User u, Group g) throws IllegalArgumentException;

	// GROCERYLIST===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createGroceryList(GroceryList gl);
	 * @param gl die GroceryList die erstellt wird
	 * @return angelegte/ erstellte GroceryList
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public GroceryList createGroceryList(GroceryList gl) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #saveGroceryList(GroceryList gl);
	 * @param gl GroceryList die gezpeichert wird
	 * @return gibt die gespeicherte GroceryList zurueck mit neuen Werten
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public GroceryList saveGroceryList(GroceryList gl) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteGroceryList(GroceryList gl);
	 * @param gl Die zu loeschende GroceryList
	 * @return gibt die geloeschte GroceryList zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public GroceryList deleteGroceryList(GroceryList gl) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getGroceryListByOwner(User u);
	 * @param u beschreibt den Owner einer GroceryList
	 * @return GroceryList des Erstellers
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<GroceryList> getGroceryListByOwner(User u) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #statusSharingGroceryList(GroceryList result);
	 * @param result beschreibt den Status einer GroceryList
	 * @return zeigt den Status einer geteilten GroceryList an
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<GroceryList> statusSharingGroceryList(Vector<GroceryList> result) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getGroceryListById(Object groceryListId);
	 * @param groceryListId beschreibt die Eindeutigkeit einer GroceryList
	 * @return gibt gesuchte GroceryList via Id zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public GroceryList getGroceryListById(int groceryListId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getGroupByGroceryList(int groceryListId);
	 * @param groceryListId beschreibt die Eindeutigkeit einer GroceryList
	 * @return gibt Gruppe einer GroceryList als Ergebnis zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Group getGroupByGroceryList(int groceryListId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllGroceryListByGroupId(int id);
	 * @return gibt alle GL's einer Grouppe via id zurueck
	 * @param id beschreibt die Eindeutigkeit einer Gruppe via id
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<GroceryList> findAllGroceryListByGroupId(int id) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllGroceryListByUserId(int userId);
	 * @param userId beschreibt die Eindeutigkeit eins Users via id
	 * @return gibt alle GroceryLists eines Users zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<GroceryList> findAllGroceryListByUserId(int userId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllGroceryLists();
	 * @return gibt einen Vector mit allen gefundenen GroceryLists zurueck.
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<GroceryList> findAllGroceryLists() throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getAllGroceryListsByGroupVector(Group g);
	 * @param g ein Vector mit Gruppen
	 * @return gibt alle Grocerylists einer gruppe zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<GroceryList> getAllGroceryListsByGroupVector(Vector<Group> g) throws IllegalArgumentException;

	// GROCERYLIST-ARTICLE===============================================================
	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByGroceryList(int id);
	 * @param id beschreibt die Eindeutigkeit einer Grocerylist via id
	 * @return gibt alle Article einer GroceryList aus.
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> findAllArticleByGroceryListId(int id) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByGroceryListAndRetailerId(int id);
	 * @param id beschreibt die Eindeutigkeit einer Grocerylist via id
	 * @return gibt alle Article einer GroceryList eines retailers aus.
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> findAllArticleByGroceryListIdAndRetailerId(int groceryListId, int retailerId)
			throws IllegalArgumentException;

	// ARTICLE===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createArticle(Article a);
	 * @param a ein Article - Objekt
	 * @return erstellt einen Articel
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Article createArticle(Article a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #saveArticle(Article a);
	 * @param a ein Article Objekt
	 * @return gibt einen gespeicherten Article zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Article saveArticle(Article a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteArticle(Article a);
	 * @param a beschreibt ein Article Objekt
	 * @return gibt ein geloeschtes Article Objekt zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Article deleteArticle(Article a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getArticleById(int articleId);
	 * @param articleId beschreibt die Eindeutigkeit eines Articles via id
	 * @return gibt einen gesuchten Article via id zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Article getArticleById(int articleId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getArticleByArticleId(int id);
	 * @param id beschreibt die Eindeutigkeit eines Articles via id
	 * @return gibt einen vollstaendigen Article via id zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Article getArticleByArticleId(int id) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticle()
	 * @return gibt alle Article zurueck die es in der Datenbank gibt
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> findAllArticle() throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByGroupIdReport(int groupId)
	 * @param groupId beschreib die Eindeutigkeit einer Gruppe via id
	 * @return gibt alle Artikel einer Gruppe zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> findAllArticleByGroupIdReport(int groupId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByOwnerId(int ownerId)
	 * @param ownerId beschreibt die Eindeutigkeit eines Owners vioa id.
	 * @return gibt alle Artikel eines Owners zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> findAllArticleByOwnerId(int ownerId) throws IllegalArgumentException;

	/**
	 * 
	 * @param name
	 * @param groupId
	 * @return gibt alle Artikel eines Namens und Gruppe zurueck
	 * @throws IllegalArgumentException
	 */
	public Vector<Article> getAllArticleByName(String name, int groupId) throws IllegalArgumentException;

	// ARTICLE-REPORT=======================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByFavouriteTRUE(Group groups)
	 * @param groups Gruppen Vector
	 * @return gibt alle Article die als Favoriten markiert sind zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> findAllArticleByFavouriteTRUE(Vector<Group> groups) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByRetailerFavouriteTRUE(Group groups, Retailer retailers)
	 * @param groups beschreibt ein Group Objekt
	 * @param retailers beschreibt ein Retailer Objekt
	 * @return gibt alle Article sowie zugehoerigen Retailer die als Favoriten
	 *         markiert sind zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> findAllArticleByRetailerFavouriteTRUE(Vector<Group> groups, Vector<Retailer> retailers)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByDateFavouriteTRUE(Group groups, Timestamp start,
	 *      Timestamp end)
	 * @param groups beschreibt ein Group Objekt Vector
	 * @param start Datum
	 * @param end Datum
	 * @return gibt alle Article sowie den zugehoerigen Zeitraum die als Favoriten
	 *         markiert sind zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> findAllArticleByDateFavouriteTRUE(Vector<Group> groups, Timestamp start, Timestamp end)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByDateRetailerFavouriteTRUE(Group groups, Retailer
	 *      retailers, Timestamp start, Timestamp end);
	 * @param groups beschreibt ein Group Objekt Vector
	 * @param retailers beschreibt ein Retailer Objekt Vector
	 * @param start Datum
	 * @param end Datum
	 * @return gibt einen Vector mit Articlen im Zeitraum, Retailer zurueck die auf
	 *         Favourite gesetzt wurden.
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> findAllArticleByDateRetailerFavouriteTRUE(Vector<Group> groups, Vector<Retailer> retailers,
			Timestamp start, Timestamp end) throws IllegalArgumentException;

	// RETAILER===========================================================================

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #createRetailer(Retailer r);
	 * @param r beschreibt ein Retailer Objekt
	 * @return erstellt einen Retailer
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Retailer createRetailer(Retailer r) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #saveRetailer(Retailer r);
	 * @param r beschreibt ein Retailer Objekt
	 * @return gibt einen neu gespeicherten Retailer zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Retailer saveRetailer(Retailer r) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteRetailer(Retailer r);
	 * @param r beschreibt ein Retailer Objekt
	 * @return gibt einen geloeschten Retailer zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Retailer deleteRetailer(Retailer r) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getArticleByRetailer(Retailer r);
	 * @param r beschreibt ein Retailer Objekt
	 * @return gibt einen Articel eines Retailers zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> getAllArticleByRetailer(Retailer r) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getArticleByDate(Date start, Date end);
	 * @param start Datum
	 * @param end Datum
	 * @return gibt einen/ mehrere Articel in einer Zeitspanne zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> getAllArticleByDate(Timestamp start, Timestamp end) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getArticleByDateRetailer(int id, Timestamp start, Timestamp end);
	 * @param id beschreibt die Eindeutigkeit eines Retailers via id
	 * @param start Datum
	 * @param end Datum
	 * @return gibt einen Article eines Retailers mit Zeitspanne zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> getAllArticleByDateRetailer(int id, Timestamp start, Timestamp end)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getRetailerById(int retailerId);
	 * @param retailerId beschreibt die Eindeutigkeit eines Retailer Objekts via id
	 * @return gibt einen Retailer zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Retailer getRetailerById(int retailerId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllRetailer();
	 * @return gibt alle Retailer zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Retailer> findAllRetailer() throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #getAllRetailerByGroupId(int groupId);
	 * @param groupId beschreibt die Eindeutigkeit eines Gruppen Objekts via id
	 * @return gibt alle Retailer einer Gruppe via groupId zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Retailer> getAllRetailerByGroupId(int groupId) throws IllegalArgumentException;

	// GroceryListArticle===========================================================================

	/**
	 * * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 * #addArticleToGroceryList(GroceryList gl, Article a);
	 * 
	 * @param gl beschreibt ein GroceryList Objekt
	 * @param a beschreibt ein Article Objekt
	 * @return gibt ein GroceryListArticle Objekt zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public GroceryListArticle addArticleToGroceryList(GroceryList gl, Article a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #removeArticleFromGroceryList(GroceryList gl, Article a);
	 * @param gl beschreibt ein GroceryList Objekt
	 * @param a beschreibt ein Article Objekt
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public void removeArticleFromGroceryList(GroceryList gl, Article a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #findAllArticleByGroceryList(int groceryListId);
	 * @param groceryList beschreibt ein GroceryList Objekt
	 * @return gibt alle Article einer GroceryList zurueck
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public Vector<Article> findAllArticleByGroceryList(GroceryList groceryList) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteArticleFromAllLists(Article a);
	 * @param a beschreibt ein Article Objekt
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht
	 *             erlaubten Arguments.
	 */
	public void deleteArticleFromAllLists(Article a) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl
	 *      #deleteAllArticlesFromGroceryList(GroceryList gl);
	 * 
	 * @param gl
	 * @throws IllegalArgumentException
	 */
	public void deleteAllArticlesFromGroceryList(GroceryList gl) throws IllegalArgumentException;
}
