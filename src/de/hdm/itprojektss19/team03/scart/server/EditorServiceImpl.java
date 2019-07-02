package de.hdm.itprojektss19.team03.scart.server;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Vector;

import javax.imageio.ImageIO;

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

	/**
	 * Generates Identicon
	 * 
	 * @see <a href="https://stackoverflow.com/questions/40697056/how-can-i-create-identicons-using-java-or-android">https://stackoverflow.com</a>
	 * 
	 */
	public String generateIdenticons(String text, int image_width, int image_height) {
		int width = 5, height = 5;

		byte[] hash = text.getBytes();

		BufferedImage identicon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = identicon.getRaster();

		int[] background = new int[] { 255, 255, 255, 0 };
		int[] foreground = new int[] { hash[0] & 255, hash[1] & 255, hash[2] & 255, 255 };

		for (int x = 0; x < width; x++) {
			// Enforce horizontal symmetry
			int i = x < 3 ? x : 4 - x;
			for (int y = 0; y < height; y++) {
				int[] pixelColor;
				// toggle pixels based on bit being on/off
				if ((hash[i] >> y & 1) == 1)
					pixelColor = foreground;
				else
					pixelColor = background;
				raster.setPixel(x, y, pixelColor);
			}
		}

		BufferedImage finalImage = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_ARGB);

		// Scale image to the size you want
		AffineTransform at = new AffineTransform();
		at.scale(image_width / width, image_height / height);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		finalImage = op.filter(identicon, finalImage);
		try {
			return encodeToString(finalImage, "png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String encodeToString(BufferedImage image, String type) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "png", outputStream);
		String encodedImage = Base64.getEncoder().encodeToString(outputStream.toByteArray());
		return encodedImage;
	}

	// SERIALIZATION===========================================================================

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

	/**
	 * Vollstaendiger Satz aller Mapper mit deren Hilfe die Administrationsklasse
	 * EditorServiceImpl-Klasse mit der DB kommunizieren kann.
	 */
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

	/**
	 * Erzeugen eines User Objekts. Der angelegte User 
	 * wird anschliessend in der DB hinterlegt.
	 * 
	 * @param u beschreibt ein User Objekt
	 * @return gitb den User zurueck
	 */
	public User createUser(User u) throws Exception {
		if (u != null) {
			User user = uMapper.insert(u);
			return user;
		} else {
			return null;
		}

	}

	/**
	 * Erzeugen eines neuen Users im System via email Adresse
	 */
	public User createUser(String emailAdress) throws IllegalArgumentException, DatabaseException {

		User user = new User();
		user.setEmail(emailAdress);
		user.setId(1);
		return this.uMapper.insert(user);
	}

	/**
	 * User wird aus dem System entfernt
	 */
	public void deleteUser(User u) throws IllegalArgumentException {

		try {
			uMapper.delete(u);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Gibt einen User via der Eindeutigen Id wieder
	 * 
	 * @param userId beschreibt die Eindeutigkeit eines Users via id
	 * @return gibt ein User Objekt zurueck
	 */
	public User getUserById(int userId) throws IllegalArgumentException {
		try {
			User foundUser = uMapper.getUserById(userId);
			return foundUser;

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Gibt einen User via der Email Adresse zurueck
	 * 
	 * @param email beschreibt einen User via einer eindeutigen Email Adresse
	 * @return gibt ein User Objekt zurueck
	 */
	public User getUserByGMail(String email) throws IllegalArgumentException {
		try {
			return uMapper.findUserByEmail(email);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * Gibt ein Profil eines User Objekts zurueck
	 * 
	 * @param user beschreibt ein User Objekt
	 * @return gibt einen User zurueck fuers aufrufen des Profils
	 */
	public User getOwnProfile(User user) throws IllegalArgumentException {
		try {

			return this.uMapper.getUserById(user.getId());

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// GROUP===========================================================================

	/**
	 * Gruppe wird erstellt und in der Datenbank hinterlegt
	 * 
	 * @param g beschreibt ein Gruppen Objekt
	 * @return gibt ein erstelltes Gruppen Objekt zurueck
	 */
	public Group createGroup(Group g) throws IllegalArgumentException {
		try {
			return this.gMapper.insert(g);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);

		}
	}

	/**
	 * Ein im nachhinein bearbeitetes Group Objekt wird erstellt
	 * 
	 * @param g beschreibt ein Group Objekt
	 */
	public void saveGroup(Group g) throws IllegalArgumentException {
		try {

			this.gMapper.update(g);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Loescht ein Gruppen Objekt aus der Datenbank
	 * 
	 * @param g beschreibt ein Gruppen Objekt
	 */
	public void deleteGroup(Group g) throws IllegalArgumentException {
		try {

			this.gMapper.delete(g);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen eines Gruppen Objekts via Id
	 * 
	 * @param groupId beschreibt ein Eindeutiges Gruppen Objekt via Id
	 * @return gibt ein Groppen Objekt zurueck
	 */
	public Group getGroupById(int groupId) throws IllegalArgumentException {
		try {

			return this.gMapper.findByGroupId(groupId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * Auslesen aller Gruppen eines Users
	 * 
	 * @param u beschreibt ein User Objekt
	 * @return gibt einen Vector von allen Gruppen des Users zurueck
	 */
	public Vector<Group> getAllGroupsByUser(User u) throws IllegalArgumentException {
		try {

			return this.gMapper.findAll();

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Hinzufuegen eines User Objekts in eine Gruppe
	 * 
	 * @param user beschreibt ein User Objekt
	 * @param group beschreibt ein Group Objekt
	 * @return gibt ein GroupUser Objekt zurueck
	 */
	public GroupUser addUserToGroup(User user, Group group) throws IllegalArgumentException {
		try {

			return this.guMapper.addUserToGroup(user, group);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * User aus einer Gruppe entfernen
	 * 
	 * @param u beschreibt ein User Objekt
	 * @param g beschreibt ein Gruppen Objekt
	 */
	public void leaveGroup(User u, Group g) throws IllegalArgumentException {
		try {

			this.gMapper.update(g);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen aller Gruppen aus der Datenbank
	 * 
	 * @return gibt einen Vector aller Gruppen aus der Datenbank zurueck
	 */
	public Vector<Group> findAllGroups() throws IllegalArgumentException, DatabaseException {
		return gMapper.findAll();
	}

	/**
	 * Beschreibt den Status einer Gruppe
	 * 
	 */
	public Vector<Group> statusSharingGroup(Vector<Group> result) {
		// TODO Auto-generated method stub
		return null;
	}

	// GROUP-USER============================================================================

	/**
	 * Auslesen aller Gruppen in denen ein user sich befindet
	 * 
	 * @param id beschreibt die Eindeutigkeit eines User Objekts via id
	 * @return gibt einen Vector von Gruppen eines Users zurueck in der er Mitglied ist
	 */
	public Vector<Group> findAllGroupsByUserId(int id) throws IllegalArgumentException {
		try {
			return guMapper.findAllGroupsByUserId(id);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Auslesen aller User in einer Gruppe via der groupId
	 * 
	 * @param id beschreibt die Eindeutigkeit einer Gruppe via id
	 * @return gibt alle User zurueck die in der gesuchten Gruppe sich befinden
	 */
	public Vector<User> getAllUserByGroupId(int id) throws IllegalArgumentException {
		try {
			return guMapper.getAllUserByGroupId(id);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Entfernt einen User aus der ausgewaehlten Gruppe
	 * 
	 * @param u beschreibt ein User Objekt
	 * @param g beschreib ein Gruppen Objekt
	 */
	public void removeUserFromGroup(User u, Group g) throws IllegalArgumentException {
		try {

			guMapper.removeUserFromGroup(u, g);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	// GROCERYLIST===========================================================================

	/**
	 * Erstellt eine GroceryList
	 * 
	 * @param gl beschreibt ein GroceryList Objekt
	 * @return gibt ein erstelltes GroceryList Objekt zurueck
	 */
	public GroceryList createGroceryList(GroceryList gl) throws IllegalArgumentException {
		try {

			return this.glMapper.insert(gl);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Speichert eine GroceryList die Neuerungen enthaelt
	 * 
	 * @param gl beschreibt ein GroceryList Objekt
	 * @return gibt ein gespeichertes GroceryList Objekt zurueck
	 */
	public GroceryList saveGroceryList(GroceryList gl) throws IllegalArgumentException {
		try {

			this.glMapper.update(gl);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return gl;
	}

	/**
	 * Entfernt eine GroceryList aus der Datenbank
	 * 
	 * @param gl beschreibt ein GroceryList Objekt
	 * @return gibt ein geloeschtes GrocceryList Objekt zurueck
	 */
	public GroceryList deleteGroceryList(GroceryList gl) throws IllegalArgumentException {
		try {

			this.glMapper.delete(gl);

		} catch (IllegalArgumentException | DatabaseException e) {
			throw new IllegalArgumentException(e);
		}
		return gl;
	}

	/**
	 * Auslesen des Owners einer GroceryList via eines User Objekts
	 * 
	 * @param u beschreibt ein User Objekt
	 * @return gibt ein  Vector GroceryList Objekt des Users zurueck
	 */
	public Vector<GroceryList> getGroceryListByOwner(User u) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Auslesen einer Grocerylist via Id
	 * 
	 * @param groceryListId beschreibt die Eindeutigkeit eines GroceryList Objekts via id
	 * @return gibt ein GroceryList Objekt zurueck
	 */
	public GroceryList getGroceryListById(int groceryListId) throws IllegalArgumentException {
		try {

			return this.glMapper.findByKey(groceryListId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen aller GroceryList Objekte via GroupId
	 * 
	 * @param id beschreibt die Eindeutigkeit eines Gruppen Objekt via id
	 * @return gibt ein GroceryList Vector Objekt zurueck
	 */
	public Vector<GroceryList> findAllGroceryListByGroupId(int id) throws IllegalArgumentException {
		try {
			return glMapper.findAllGroceryListByGroupId(id);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen aller GroceryList Objekte die in der Datenbank liegen
	 * 
	 * @return gibt alle GroceryList Objekte in der Datenbank zurueck
	 */
	public Vector<GroceryList> findAllGroceryLists() throws IllegalArgumentException {
		try {
			return glMapper.findAll();
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Gibt den Status einer GroceryList zurueck
	 * 
	 * @param result beschreibt ein Vector GroceryList Objekt
	 * @return gibt einen Vector GroceryList Objekt zurueck
	 */
	public Vector<GroceryList> statusSharingGroceryList(Vector<GroceryList> result) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Auslesen einer GroceryList einer Gruppe
	 * 
	 * @param groceryListId beschreibt die EIndeutigkeit eines GroceryList Objekts via id
	 * @return gibt eine Gruppe zurueck die zur uebergebenen GroceryList gehoert
	 */
	public Group getGroupByGroceryList(int groceryListId) throws IllegalArgumentException {

		try {

			return gMapper.findByGroupId(glMapper.findByKey(groceryListId).getGroupId());

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen einer GroceryList via eines Group Vectors
	 * 
	 * @param g beschreibt einen Vector voller Gruppen
	 * @return gibt einen Vector der dazugehoerigen GroceryList Objekte zurueck
	 */
	public Vector<GroceryList> getAllGroceryListsByGroupVector(Vector<Group> g) throws IllegalArgumentException {
		try {
			Vector<GroceryList> tempGrocery = new Vector<GroceryList>();
			for (int i = 0; i < g.size(); i++) {

				tempGrocery.addAll(glMapper.findAllGroceryListByGroupId(g.elementAt(i).getId()));

			}
			return tempGrocery;
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * Auslesen aller GroceryList via User Id
	 * 
	 * @param userId beschreibt die Eindeutigkeit eines user Objekts via id
	 * @return gibt alle GroceryList Objekte des uebergebenen Users zurueck
	 */
	public Vector<GroceryList> findAllGroceryListByUserId(int userId) throws IllegalArgumentException {
		try {
			return glMapper.findAllGroceryListsByUserId(userId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// GROCERYLIST-ARTICLE===============================================================

	/**
	 * Auslesen aller Article Objekte eines GroceryList Objekts via id
	 * 
	 * @param id beschreibt die Eindeutigkeit eines GroceryList Objekts via id
	 * @return gibt alle Article Objekte eines GroceryList Objekt zurueck
	 */
	public Vector<Article> findAllArticleByGroceryListId(int id) throws IllegalArgumentException {
		try {
			return this.glaMapper.findAllArticleByGroceryListId(id);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// ARTICLE===========================================================================

	/**
	 * Erstellt einen Article in der Datenbank
	 * 
	 * @param a beschreibt ein Article Objekt
	 * @return gibt ein erstelltes Article Objekt zurueck
	 */
	public Article createArticle(Article a) throws IllegalArgumentException {
		// Input fuer Article Attribute muss noch erledigt werden

		try {
			return this.aMapper.insert(a);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Speichern eines zuvor erstellten Article Objekts
	 * 
	 * @param a beschreibt ein Article Objekt
	 * @return gibt ein Article Objekt zurueck
	 */
	public Article saveArticle(Article a) throws IllegalArgumentException {
		try {

			this.aMapper.update(a);
			return a;
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * Entfernen eines Article Objekt aus der Datenbank
	 * 
	 * @param a beschreibt ein Article Objekt
	 * @return gibt ein geloeschtes Article Objekt zurueck
	 */
	public Article deleteArticle(Article a) throws IllegalArgumentException {
		try {
			aMapper.delete(a);
			// Erfolgts Message fuer erfolgreiches Loeschen
			return a;

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen eines Articles via der eindeutigen Id des Article Objekts
	 * 
	 * @param articleId beschreibt die Eindeutigkeit eines Article Objekts via id
	 * @return gibt einen Article via der articleId zurueck
	 */
	public Article getArticleById(int articleId) throws IllegalArgumentException {
		try {

			return this.aMapper.findByKey(articleId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen eines Articles via der eindeutigen Id des Article Objekts
	 * 
	 * @param id beschreibt die Eindeutigkeit eines Article Objekts via id
	 * @return gibt einen Article via der id zurueck
	 */
	public Article getArticleByArticleId(int id) throws IllegalArgumentException {
		try {

			return this.aMapper.findById(id);

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

	/**
	 * Auslesen aller Article in der Datenbank
	 * 
	 * @return gibt alle Article in der Datenbank zurueck
	 */
	public Vector<Article> findAllArticle() throws IllegalArgumentException {
		try {
			return this.aMapper.findAll();
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen aller Article via groupId zur Filterung im Report
	 * 
	 * @param groupId beschreibt ein eindeutiges Group Objekt via id
	 * @return gibt alle Article einer Gruppe zurueck die zur groupId passen.
	 */
	public Vector<Article> findAllArticleByGroupIdReport(int groupId) throws IllegalArgumentException {
		try {
			return this.aMapper.findAllArticleByGroupId(groupId);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen aller Article eines Owners via id
	 * 
	 * @param ownerId beschreibt einen Owner Objekt via id
	 * @return gibt einen Vector mit Article Objekten zurueck
	 */
	public Vector<Article> findAllArticleByOwnerId(int ownerId) throws IllegalArgumentException {
		try {
			return this.aMapper.findAllArticleByOwnerId(ownerId);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	//REPORT======================================================================================================
	/**
	 * AUslesen aller Article die als Favourite markiert sind
	 * 
	 * @param groups Vector der Gruppen Objekte die inm Report ausgewaehlt wurden
	 * @return gibt alle Article Objkete der selektierten Gruppen zurueck
	 */
	public Vector<Article> findAllArticleByFavouriteTRUE(Vector<Group> groups) throws IllegalArgumentException {
		try {
			return this.aMapper.findAllArticleByFavouriteTRUE(groups);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	//REPORT======================================================================================================
	/**
	 * Auslesen aller Article Objekte die als Favourite markiert sind und den selektierten
	 * Retailer beinhalten
	 * 
	 * @param groups Vector der Gruppen Objekte enthaelt, die im Report ausgewaehlt wurden
	 * @param retailers Vector der Retailer Objekte enthaelt, die im Report ausgewaehlt wurden
	 * @return gibt alle Article Objkete der selektierten Gruppen sowie dazugehoerigen Retailer zurueck 
	 */
	public Vector<Article> findAllArticleByRetailerFavouriteTRUE(Vector<Group> groups, Vector<Retailer> retailers) throws IllegalArgumentException {
		try {
			return this.aMapper.findAllArticleByRetailerFavouriteTRUE(groups,retailers);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	
	//REPORT======================================================================================================
	/**
	 * Auslesen aller Article Objekte die als Favourite markiert sind und den selektierten
	 * Zeitraum beinhalten 
	 * 
	 * @param groups Vector der Gruppen Objekte enthaelt, die im Report ausgewaehlt wurden
	 * @param start Datums Zeitraum der im Report ausgewahelt wurde
	 * @param end Datums Zeitraum der im Report ausgewahelt wurde
	 * @return gibt alle Article Objkete der selektierten Gruppen sowie dazugehoerigen Zeitraum zurueck 
	 */
	public Vector<Article> findAllArticleByDateFavouriteTRUE(Vector<Group> groups, Timestamp start, Timestamp end) throws IllegalArgumentException {
		try {
			return this.aMapper.findAllArticleByDateFavouriteTRUE(groups, start, end);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	
	//REPORT======================================================================================================
	
	/**
 	 * Auslesen aller Article Objekte die als Favourite markiert sind, einen selektierten
	 * Zeitraum besitzen und einen dazugehoerigen Retailer beinhalten 
	 * 
	 * @param groups Vector der Gruppen Objekte enthaelt, die im Report ausgewaehlt wurden
	 * @param start Datums Zeitraum der im Report ausgewahelt wurde
	 * @param end Datums Zeitraum der im Report ausgewahelt wurde
	 * @return gibt alle Article Objkete der selektierten Gruppen sowie dazugehoerigen Zeitraum zurueck 
	 */
	public Vector<Article> findAllArticleByDateRetailerFavouriteTRUE(Vector<Group> groups, Vector<Retailer> retailers, Timestamp start, Timestamp end) throws IllegalArgumentException {
		try {
			return this.aMapper.findAllArticleByDateRetailerFavouriteTRUE(groups, retailers, start, end);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// RETAILER===========================================================================

	/**
	 * Erstellen eines Retailer Objekts und abspeichern in der Datenbank
	 * 
	 * @param r beschreibt ein Retailer Objekt
	 * @return gibt einen erstellten Retailer in der Datenbank zurueck
	 */
	public Retailer createRetailer(Retailer r) throws IllegalArgumentException {
		try {

			return this.rMapper.insert(r); // Retailer Objekt in der DB speichern

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Speichern eines upgedateten Retailer Objekts in der Datenbnk
	 * 
	 * @param r beschreibt ein Retailer Objekt
	 * @return gibt ein ueberarbeitetes Retailer Objekt zurueck
	 */
	public Retailer saveRetailer(Retailer r) throws IllegalArgumentException {
		try {

			this.rMapper.update(r); 

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return r;
	}

	/**
	 * Entfernen eines Retailer Objekts in der Datenbank
	 * 
	 * @param r beschreibt ein Retailer Objekt
	 * @return gibt ein geloeschtes Retailer Objekt zurueck
	 */
	public Retailer deleteRetailer(Retailer r) throws IllegalArgumentException {
		try {

			this.rMapper.delete(r); 

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return r;
	}

	/**
	 * Auslesen aller Retailer Objekte einer Groupe via der eindeutigen Group Id
	 * 
	 * @param groupId beschreibt die Eindeutigkeit eines gruppen Objekts via id
	 * @return gibt alle Retailer Objekte zurueck die in der Gruppe sind.
	 */
	public Vector<Retailer> getAllRetailerByGroupId(int groupId) throws IllegalArgumentException {
		try {
			return this.rMapper.getAllRetailersByGroupId(groupId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen aller Article Objekte eines uebergebenen Retailer Objekts
	 * 
	 * @param r beschreibt ein Retailer Objekt
	 * @return gibt alle Article Objekte zurueck die den uebergebenen Retailer besitzen
	 */
	public Vector<Article> getAllArticleByRetailer(Retailer r) throws IllegalArgumentException {
		try {

			return this.aMapper.findArticleByRetailerId(r.getRetailerId());

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * Auslesen aller Article Objekte die im angegebenen Zeitraum liegen
	 * 
	 * @param start Datums Zeitraum der im Report ausgewahelt wurde
	 * @param end Datums Zeitraum der im Report ausgewahelt wurde
	 * @return gibt alle Article Objekte zurueck die im angegebenen Zeitraum liegen
	 */
	public Vector<Article> getAllArticleByDate(Timestamp start, Timestamp end) throws IllegalArgumentException {
		try {

			return this.aMapper.findAllArticleByDate(start, end);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * 
	 * @param id beschreibt die Eindeutigkeit eines Retailer Objekts via id
	 * @param start Datums Zeitraum der im Report ausgewahelt wurde
	 * @param end Datums Zeitraum der im Report ausgewahelt wurde
	 * @param r beschreibt ein Retailer Objekt
	 * @return gibt alle Article Objekte zurueckd die im angegebenen Zeitraum liegen
	 * @throws IllegalArgumentException Entsteht bei der Uebergabe eines nicht erlaubten Arguments.
	 */
	public Vector<Article> getAllArticleByDateRetailer(int id, Timestamp start, Timestamp end, Retailer r)
			throws IllegalArgumentException {
		try {
			return this.aMapper.findAllArticleByDateRetailer(id, start, end);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * Auslesen aller Retailer Objekte via der eindeutigen Id
	 * 
	 * @param retailerId beschreibt die Eindeutigkeit eines Retailer Objekts via id
	 * @return gibt ein Retailer Objekt zurueck das zur uebergegebenen retailer Id passt.
	 */
	public Retailer getRetailerById(int retailerId) throws IllegalArgumentException {
		try {

			return this.rMapper.findById(retailerId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * Auslesen aller Retailer Objekte in der Datenbank
	 * 
	 * @return gibt alle Retailer Objekte in der Datenbank zurueck
	 */
	public Vector<Retailer> findAllRetailer() {
		try {
			return this.rMapper.findAll();
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	// GroceryListArticle===========================================================================
	
	/**
	 * Ein Article Objekt eines GroceryList Objekts hinzufuegen
	 * 
	 * @param gl beschreibt ein GroceryList Objekt 
	 * @param a beschreibt ein Article Objekt
	 * @return gibt ein GroceryListArticle Objekt zurueck
	 */
	public GroceryListArticle addArticleToGroceryList(GroceryList gl, Article a) {
		try {
			return this.glaMapper.addArticleToGroceryList(gl, a);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Loeschen eines Article Objekt aus eines GroceryList Objekts
	 * 
	 * @param gl beschreibt ein GroceryList Objekt
	 * @param a beschreibt ein Article Objekt
	 */
	public void removeArticleFromGroceryList(GroceryList gl, Article a) {
		try {
			this.glaMapper.removeArticleFromGroceryList(gl, a);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Auslesen aller Article Objekte eines GroceryList Objekts
	 * 
	 * @param groceryList beschreibt ein GroceryList Objekt
	 * @return gibt einen Article Objekt Vector zurueck mit allen Article Objekten in einer GroceryList
	 */
	public Vector<Article> findAllArticleByGroceryList(GroceryList groceryList) {
		try {
			return this.glaMapper.findAllArticleByGroceryListId(groceryList.getId());

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * Loeschen eines Article Objekts aus der Datenbank
	 * 
	 * @param a beschreibt ein Article Objekt
	 */
	public void deleteArticleFromAllLists(Article a) {
		try {
			this.glaMapper.deleteArticleFromAllLists(a);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

}
