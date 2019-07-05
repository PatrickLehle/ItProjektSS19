package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.ServersideSettings;
import de.hdm.itprojektss19.team03.scart.shared.DatabaseException;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author Marco Dell'Oso, Patrick Lehle
 * @author Thies
 *
 */
public class RetailerMapper {

	/**
	 * Singleton Klasse -> es gibt immer nur eine Instanz dieser Klasse.
	 */
	private static RetailerMapper retailerMapper = null;

	/**
	 * Protected Construcor
	 */
	protected RetailerMapper() {
	}

	/**
	 * Statische Methode, die die Single Eingenschaft sicherstellt, indem sie nur
	 * eine neue Instanz erstellt, wenn keine vorhanden ist.
	 * 
	 * @return RetailerMapper-Objekt
	 */
	public static RetailerMapper retailerMapper() {
		if (retailerMapper == null) {
			retailerMapper = new RetailerMapper();
		}

		return retailerMapper;
	}

	/**
	 * Sucht eine Retailer anhand seiner ID
	 * 
	 * @param id beschreibt die Eindeutigkeit eines Retailers via id
	 * @return Das Retailer-Objekt, falls ein passendes gefunden wurde.
	 * @throws DatabaseException Falls die Datenbankabfrage Fehlschlaegt
	 */
	public Retailer findByKey(int id) throws DatabaseException {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM retailer JOIN user ON user.userId = retailer.retailerUserId JOIN groups ON groups.groupId = retailer.retailerGroupId WHERE retailerId="
							+ id);

			// Es darf nur ein Ergebinis gefunden werden, da id der Prim�rschl�ssel ist
			if (rs.next()) {
				Retailer retailer = new Retailer();
				User user = new User();
				user.setEmail(rs.getString("userEmail"));
				user.setId(rs.getInt("userId"));
				user.setUsername(rs.getString("userName"));
				Group group = new Group();
				group.setGroupName(rs.getString("groupName"));
				group.setId(rs.getInt("groupId"));
				retailer.setGroup(group);
				retailer.setUser(user);
				retailer.setId(rs.getInt("retailerId"));
				retailer.setRetailerName(rs.getString("retailerName"));
				return retailer;
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return null;
	}

	/**
	 * Gibt einen Retailer via Namen zurueck
	 * 
	 * @param name beschreibt den Namen des Retailer Objekts
	 * @param r beschreibt das Retailer Objekt
	 * @return Ergebnis Vector aller Retailer mit dem selben Namen
	 * @throws DatabaseException Falls die Datenbankabfrage Fehlschlaegt
	 */
	public Vector<Retailer> findRetailerByName(String name, Retailer r) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String select = "SELECT * FROM retailer JOIN user ON user.userId = retailer.retailerUserId JOIN groups ON groups.groupId = retailer.retailerGroupId WHERE retailerName=?";

		Vector<Retailer> result = new Vector<Retailer>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(select);
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Retailer retailer = new Retailer();
				retailer.setRetailerId(rs.getInt("retailerId"));
				retailer.setRetailerName(rs.getString("retailerName"));
				User user = new User();
				user.setEmail(rs.getString("userEmail"));
				user.setId(rs.getInt("userId"));
				user.setUsername(rs.getString("userName"));
				Group group = new Group();
				group.setGroupName(rs.getString("groupName"));
				group.setId(rs.getInt("groupId"));
				retailer.setGroup(group);
				retailer.setUser(user);
				result.addElement(retailer);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	/**
	 * Sucht alle Retailers
	 * 
	 * @return Vector mit allen gefundenen Retailers
	 * @throws DatabaseException Falls die Datenbankabfrage Fehlschlaegt
	 */
	public Vector<Retailer> findAll() throws DatabaseException {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		Vector<Retailer> retailers = new Vector<Retailer>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM retailer JOIN user ON user.userId = retailer.retailerUserId JOIN groups ON groups.groupId = retailer.retailerGroupId");

			// Neues retailer Objekt f�r jede gefundene ID
			while (rs.next()) {
				Retailer retailer = new Retailer();
				User user = new User();
				user.setEmail(rs.getString("userEmail"));
				user.setId(rs.getInt("userId"));
				user.setUsername(rs.getString("userName"));
				Group group = new Group();
				group.setGroupName(rs.getString("groupName"));
				group.setId(rs.getInt("groupId"));
				retailer.setId(rs.getInt("retailerId"));
				retailer.setRetailerName(rs.getString("retailerName"));
				retailer.setGroup(group);
				retailer.setUser(user);
				retailers.addElement(retailer);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}

		return retailers;
	}

	/**
	 * 
	 * Gibt alle Retailer einer Gruppe zurueck
	 * 
	 * @param groupId Die besagte Gruppe
	 * @return einen Vector aus gefundenen Retailers
	 * @throws DatabaseException Falls die Datenbankabfrage Fehlschlaegt
	 */
	public Vector<Retailer> getAllRetailersByGroupId(int groupId) throws DatabaseException {
		Connection con = DBConnection.connection();
		Vector<Retailer> retailers = new Vector<Retailer>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM retailer JOIN user ON user.userId = retailer.retailerUserId JOIN groups ON groups.groupId = retailer.retailerGroupId WHERE retailerGroupId="
							+ groupId);

			while (rs.next()) {
				Retailer retailer = new Retailer();
				User user = new User();
				user.setId(rs.getInt("userId"));
				user.setEmail(rs.getString("userEmail"));
				user.setUsername(rs.getString("userName"));
				Group group = new Group();
				group.setGroupName(rs.getString("groupName"));
				group.setId(rs.getInt("groupId"));
				retailer.setId(rs.getInt("retailerId"));
				retailer.setRetailerName(rs.getString("retailerName"));
				retailer.setUser(user);
				retailer.setGroup(group);
				retailers.addElement(retailer);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}

		return retailers;
	}

	/**
	 * 
	 * Gibt alle Retailer einer GroceryList zurueck
	 * 
	 * @param groupId Die besagte GroceryList
	 * @return einen Vector aus gefundenen Retailers
	 * @throws DatabaseException Falls die Datenbankabfrage fehlschlaegt
	 */
	public Vector<Retailer> getAllRetailersByGroceryListId(int groceryListId) throws DatabaseException {
		Connection con = DBConnection.connection();
		Vector<Retailer> retailers = new Vector<Retailer>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM retailer JOIN user ON user.userId = retailer.retailerUserId JOIN groups ON groups.groupId = retailer.retailerGroupId WHERE retailerGroupId="
							+ groceryListId);

			while (rs.next()) {
				Retailer retailer = new Retailer();
				User user = new User();
				user.setId(rs.getInt("userId"));
				user.setEmail(rs.getString("userEmail"));
				user.setUsername(rs.getString("userName"));
				Group group = new Group();
				group.setGroupName(rs.getString("groupName"));
				group.setId(rs.getInt("groupId"));
				retailer.setId(rs.getInt("retailerId"));
				retailer.setRetailerName(rs.getString("retailerName"));
				retailer.setUser(user);
				retailer.setGroup(group);
				retailers.addElement(retailer);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}

		return retailers;
	}

	/**
	 * Gibt alle einzigartigen Retailer nach Gruppe und GroceryList aus
	 * 
	 * @param g Group
	 * @param gr GroceryList
	 * @return Vector aus gefundenen retailern
	 * @throws DatabaseException DatabaseException Falls die Datenbankabfrage
	 *             fehlschlaegt
	 */
	public Vector<Retailer> getAllDistinctRetailerByGroupAndGroceryList(Group g, GroceryList gr)
			throws DatabaseException {
		Connection con = DBConnection.connection();
		Vector<Retailer> retailers = new Vector<Retailer>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT DISTINCT retailerId, retailerName, retailerGroupId, retailerUserId FROM retailer JOIN "
							+ "article ON retailer.retailerId = article.articleRetailerId JOIN grocerylistarticle ON "
							+ "grocerylistarticle.articleId = article.articleId WHERE grocerylistarticle.grocerylistId = "
							+ gr.getId() + " AND article.articleGroupId = " + g.getId());

			while (rs.next()) {
				Retailer retailer = new Retailer();
				User user = new User();
				user.setId(rs.getInt("retailerUserId"));
				Group group = new Group();
				group.setId(rs.getInt("retailerGroupId"));
				retailer.setId(rs.getInt("retailerId"));
				retailer.setRetailerName(rs.getString("retailerName"));
				retailer.setUser(user);
				retailer.setGroup(group);
				retailers.addElement(retailer);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}

		return retailers;
	}

	/**
	 * Fuegt in der Datenbank einen neuen Retailer ein
	 * 
	 * @param retailer beschreibt den uebergebeben Retailer
	 * @return Die Eingefuegte Retailer mit aktueller ID
	 * @throws DatabaseException Falls die Datenbankabfrage Fehlschlaegt
	 */
	public Retailer insert(Retailer retailer) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;
		String maxIdSQL = "SELECT MAX(retailerId) AS maxid FROM retailer";
		String insertSQL = "INSERT INTO retailer (retailerId, retailerName, retailerGroupId, retailerUserId) VALUES (?,?,?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxIdSQL);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				retailer.setId(rs.getInt("maxid") + 1);
				// Jetzt erfolgt das Einfuegen des Objekts
				stmt = con.prepareStatement(insertSQL);
				stmt = con.prepareStatement(insertSQL);
				stmt.setInt(1, retailer.getId());
				stmt.setString(2, retailer.getRetailerName());
				stmt.setInt(3, retailer.getGroup().getId());
				stmt.setInt(4, retailer.getUser().getId());
				stmt.executeUpdate();
				return retailer;
			} else {
				throw new DatabaseException(new SQLException("no next retailre for maxid"));
			}

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

	/**
	 * aendert einen Retailer in der Datenbank
	 * 
	 * @param retailer beschreibt den uebergebenen Retailer
	 * @return Die Rückgabe ist ein Retailer-Objekt mit seinen neuen Datensaetzen
	 * @throws DatabaseException Falls die Datenbankabfrage Fehlschlaegt
	 */
	public Retailer update(Retailer retailer) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;
		String update = "UPDATE retailer SET retailerName=?, retailerGroupId=?, retailerUserId=? WHERE retailerId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(update);
			System.out.println("retailer: " + retailer.getGroup().getId());
			stmt.setString(1, retailer.getRetailerName());
			stmt.setInt(2, retailer.getGroup().getId());
			stmt.setInt(3, retailer.getUser().getId());
			stmt.setInt(4, retailer.getId());
			stmt.executeUpdate();
			return retailer;
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

	/**
	 * Loescht einen Retailer aus der Datenbank
	 * 
	 * @param retailer beschreibt den uebergenenen Retailer
	 * @throws DatabaseException Falls die Datenbankabfrage Fehlschlaegt
	 */
	public void delete(Retailer retailer) throws DatabaseException {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM retailer WHERE retailerId=" + retailer.getId());

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

	/**
	 * Gibt den Retailer mit einer bestimmten ID zurueck
	 * 
	 * @param id nach dieser ID wird gesucht
	 * @return ein Retailer Objekt
	 * @throws DatabaseException Falls die Datenbankabfrage Fehlschlaegt
	 */
	public Retailer findById(int id) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;
		String selectByKey = "SELECT * FROM retailer JOIN user ON user.userId = retailer.retailerUserId JOIN groups ON groups.groupId = retailer.retailerGroupId WHERE retailer.retailerId="
				+ id;

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Retailer r = new Retailer();
				User user = new User();
				Group group = new Group();
				group.setGroupName(rs.getString("groupName"));
				group.setId(rs.getInt("groupId"));
				user.setId(rs.getInt("userId"));
				user.setEmail(rs.getString("userEmail"));
				user.setUsername(rs.getString("userName"));
				r.setId(rs.getInt("retailerId"));
				r.setRetailerName(rs.getString("retailerName"));
				r.setGroup(group);
				r.setUser(user);
				return r;
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return null;
	}

}
