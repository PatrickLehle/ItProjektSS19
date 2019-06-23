package de.hdm.itprojektss19.team03.scart.server;

import java.sql.Timestamp;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.server.db.ArticleMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroceryListArticleMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroceryListMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroupMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroupUserMapper;
//import de.hdm.itprojektss19.team03.scart.server.db.UnitMapper;
import de.hdm.itprojektss19.team03.scart.server.db.RetailerMapper;
import de.hdm.itprojektss19.team03.scart.server.db.UserMapper;
import de.hdm.itprojektss19.team03.scart.shared.DatabaseException;
import de.hdm.itprojektss19.team03.scart.shared.EditorService;
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
 * 
 * @author bastiantilk, PatrickLehle, MarcoDell'Oso, JulianHofer, vanduyho
 *         Serverseitiger RPC-Service fuer den Editor.
 * 
 */
public class EditorServiceImpl extends RemoteServiceServlet implements EditorService {

	public EditorServiceImpl() throws IllegalArgumentException {

	}

	// SERIALIZATION===========================================================================

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
	private GroupMapper gMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>Retailer</code>-mit der
	 * Datenbank vergleicht.
	 */
	private RetailerMapper rMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>ArticleMapper</code> mit
	 * der Datenbank vergleicht.
	 */
	private ArticleMapper aMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>GroceryList</code> und
	 * <code>User</code> mit der Datenbank vergleicht.
	 */
	private GroupUserMapper guMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>GroceryList</code> mit der
	 * Datenbank vergleicht.
	 */
	private GroceryListMapper glMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>GroceryList</code> und
	 * <code>Article</code> mit der Datenbank vergleicht.
	 */
	private GroceryListArticleMapper glaMapper = null;

	// INITIALIZATION===========================================================================

	public void init() throws IllegalArgumentException {

		this.uMapper = UserMapper.userMapper();
		this.gMapper = GroupMapper.groupMapper();
		this.glMapper = GroceryListMapper.groceryListMapper();
		this.aMapper = ArticleMapper.articleMapper();
		this.rMapper = RetailerMapper.retailerMapper();
		this.guMapper = GroupUserMapper.groupUserMapper();
		this.glaMapper = GroceryListArticleMapper.groceryListArticleMapper();

	}

	// USER====================================================================================

	public User createUser(String username, String emailAdress) throws Exception {
		// E-Mail und Username muss zunaechst ueber GUI abgefragt werden

		try {

			User result = uMapper.findUserByEmail(emailAdress);

			if (result != null) {
				return result;
				// Email bereits vorhanden
			} else {
				User newUser = new User(username, emailAdress);
				uMapper.insert(newUser);
				return newUser;
				// Ausgabe der Rueckgabe aus der insert Funktion fehlt
			}

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public User createUser(String emailAdress) throws IllegalArgumentException, DatabaseException {

		User user = new User();
		user.setEmail(emailAdress);
		user.setId(1);
		return this.uMapper.insert(user);
	}

	public void deleteUser(User u) throws IllegalArgumentException {

		try {
			uMapper.delete(u);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public User getUserById(int userId) throws IllegalArgumentException {
		try {
			User foundUser = uMapper.findbyUserId(userId);
			return foundUser;

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public User getUserByGMail(String email) throws IllegalArgumentException {
		try {
			User foundUser = uMapper.findUserByEmail(email);
			System.out.println(foundUser.getEmail());
			return foundUser;

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public User getOwnProfile(User user) throws IllegalArgumentException {
		try {

			return this.uMapper.findbyUserId(user.getId());

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// GROUP===========================================================================

	public Group createGroup(Group g) throws IllegalArgumentException {
		try {
			return this.gMapper.insert(g);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);

		}
	}

	public void saveGroup(Group g) throws IllegalArgumentException {
		try {

			this.gMapper.update(g);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public void deleteGroup(Group g) throws IllegalArgumentException {
		try {

			this.gMapper.delete(g);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Group getGroupById(int groupId) throws IllegalArgumentException {
		try {

			return this.gMapper.findByGroupId(groupId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	public Vector<Group> getAllGroupsByUser(User u) throws IllegalArgumentException {
		try {

			return this.gMapper.findAll();

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public void addUserToGroup(User user, Group group) throws IllegalArgumentException {
		try {
			
			this.guMapper.addUserToGroup(user, group);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public void leaveGroup(User u, Group g) throws IllegalArgumentException {
		try {

			this.gMapper.update(g);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Vector<Group> findAllGroups() throws IllegalArgumentException, DatabaseException {
		return gMapper.findAll();
	}

	@Override
	public Vector<Group> statusSharingGroup(Vector<Group> result) {
		// TODO Auto-generated method stub
		return null;
	}

	// GROUP-USER============================================================================

	public Vector<Group> findAllGroupsByUserId(int id) throws IllegalArgumentException {
		try {
			return guMapper.findAllGroupsByUserId(id);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	
	public void addUserToGroup(User u, Group g) throws IllegalArgumentException {
		try {

			this.guMapper.addUserToGroup(u, g);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	
	public Vector<User> getAllUsersByGroupId(int id) throws IllegalArgumentException{
		try {
			 return guMapper.getAllUsersByGroupId(id);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	// GROCERYLIST===========================================================================

	public GroceryList createGroceryList(String name, GroceryList gl) throws IllegalArgumentException {
		try {

			return this.glMapper.insert(gl);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public void saveGroceryList(GroceryList gl) throws IllegalArgumentException {
		try {

			this.glMapper.update(gl);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public void deleteGroceryList(GroceryList gl) throws IllegalArgumentException {
		try {

			this.glMapper.delete(gl);

		} catch (IllegalArgumentException | DatabaseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public Vector<GroceryList> getGroceryListByOwner(User u) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	public GroceryList getGroceryListById(int groceryListId) throws IllegalArgumentException {
		try {

			return this.glMapper.findByKey(groceryListId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Vector<GroceryList> findAllGroceryListByGroupId(int id) throws IllegalArgumentException {
		try {
			return glMapper.findAllGroceryListByGroupId(id);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Vector<GroceryList> findAllGroceryLists() throws IllegalArgumentException {
		try {
			return glMapper.findAll();
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Vector<GroceryList> statusSharingGroceryList(Vector<GroceryList> result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Group getGroupByGroceryList(int groceryListId) throws IllegalArgumentException {

		try {

			return gMapper.findByGroupId(glMapper.findByKey(groceryListId).getGroupId());

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// GROCERYLIST-ARTICLE===============================================================

	public Vector<Article> findAllArticleByGroceryListId(int id) throws IllegalArgumentException {
		try {
			return this.glaMapper.findAllArticleByGroceryListId(id);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// ARTICLE===========================================================================

	public Article createArticle(Article a) throws IllegalArgumentException {
		// Input fuer Article Attribute muss noch erledigt werden

		try {
			Article temp = new Article(); // Mit dem Input der Attribute muss hier ein neues Object mit den Attributen
											// erstellt werden
			this.aMapper.insert(temp);
			// Ausgabe der Rueckgabe aus der insert Funktion fehlt

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return a;
	}

	public Article saveArticle(Article a) throws IllegalArgumentException {
		try {

			this.aMapper.update(a);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return a;
	}

	public void deleteArticle(Article a) throws IllegalArgumentException {
		try {
			aMapper.delete(a);
			// Erfolgts Message fuer erfolgreiches Loeschen

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	public Article getArticleById(int articleId) throws IllegalArgumentException {
		try {

			return this.aMapper.findByKey(articleId); 

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Vector<Article> getAllArticleByDateRetailer(int id, Timestamp start, Timestamp end)
			throws IllegalArgumentException {
		try {
			return this.aMapper.findAllArticleByDateRetailer(id, start, end);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Vector<Article> findAllArticle() throws IllegalArgumentException {
		try {
			return this.aMapper.findAll();
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// RETAILER===========================================================================

	public Retailer createRetailer(Retailer r) throws IllegalArgumentException {
		try {

			return this.rMapper.insert(r); // Retailer Objekt in der DB speichern

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public void saveRetailer(Retailer r) throws IllegalArgumentException {
		try {

			this.rMapper.update(r); // Speichert Retailer

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public void deleteRetailer(Retailer r) throws IllegalArgumentException {
		try {

			this.rMapper.delete(r); // Löscht Retailer

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	
	public Vector<Retailer> getAllRetailersByGroupId(int groupId) throws IllegalArgumentException {
		try {
			// CHANGE
			return this.rMapper.findAll();
			
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Vector<Article> getAllArticleByRetailer(Retailer r) throws IllegalArgumentException {
		try {

			return this.aMapper.findArticleByRetailerId(r.getRetailerId());

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	public Vector<Article> getAllArticleByDate(Timestamp start, Timestamp end) throws IllegalArgumentException {
		try {

			return this.aMapper.findAllArticleByDate(start, end);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	public Vector<Article> getAllArticleByDateRetailer(int id, Timestamp start, Timestamp end, Retailer r)
			throws IllegalArgumentException {
		try {
			return this.aMapper.findAllArticleByDateRetailer(id, start, end);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	public Retailer getRetailerById(int retailerId) throws IllegalArgumentException {
		try {

			return this.rMapper.findById(retailerId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	public Vector<Retailer> findAllRetailer() {
		try {
			return this.rMapper.findAll();
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// GroceryListArticle===========================================================================
	public GroceryListArticle addArticleToGroceryList(GroceryList gl, Article a) {
		try {
			return this.glaMapper.addArticleToGroceryList(gl, a);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public void removeArticleFromGroceryList(GroceryList gl, Article a) {
		try {
			this.glaMapper.removeArticleFromGroceryList(gl, a);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Vector<Article> findAllArticleByGroceryList(int groceryListId) {
		try {
			return this.glaMapper.findAllArticleByGroceryListId(groceryListId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	public void deleteArticleFromAllLists(Article a) {
		try {
			this.glaMapper.deleteArticleFromAllLists(a);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// UNIT===========================================================================

	// public Unit createUnit(Unit u) throws IllegalArgumentException{
	// try {
	// int temp1=0;
	//
	// for(int i=0; i<= unMapper.findAll().size(); i++) {
	// if(unMapper.findAll().elementAt(i).getUnitName() == u.getUnitName()) {
	// temp1++;
	// }
	// }
	// if(temp1 ==0) {
	// unMapper.insert(u);
	// }
	// return u; //Rueckgabewert vom Mapper wird noch nicht verarbeitet
	// }catch(IllegalArgumentException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	//
	//
	//
	// public void saveUnit(Unit u) throws IllegalArgumentException{
	// this.unMapper.update(u);
	// }
	//
	// public void deleteUnit(Unit u) throws IllegalArgumentException {
	// this.unMapper.delete(u);
	// }
	//
	// public Unit getUnitById(int unitId) throws IllegalArgumentException {
	// return this.unMapper.findByKey(unitId);
	//
	// }
	//
	// public Unit getUnitByName(String unitName) throws IllegalArgumentException {
	// return this.unMapper.findUnitByName(unitName);
	//
	// }

	// ENTRY===========================================================================

	public Entry createEntry(Entry e) throws IllegalArgumentException {
		try {

			return null;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex);
		}

	}

	public void saveEntry(Entry e) throws IllegalArgumentException {
		try {

		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex);
		}
	}

	public void deleteEntry(Entry e) throws IllegalArgumentException {
		try {

		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex);
		}
	}

	/**
	 * @Override public Vector<Article> getArticleByRetailer(Retailer r) throws
	 *           IllegalArgumentException { try { return
	 *           aMapper.findArticleByRetailerId(r.getId());
	 * 
	 *           } catch(IllegalArgumentException ex) { ex.printStackTrace(); return
	 *           null; } }
	 */

	// @Override
	// public GroceryList getGroceryListById(Object groceryListId) throws
	// IllegalArgumentException {
	// // TODO Auto-generated method stub
	// return null;
	// }
}
