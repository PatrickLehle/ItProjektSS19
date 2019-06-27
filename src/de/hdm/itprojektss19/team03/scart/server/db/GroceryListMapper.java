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

/**
 * 
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author Marco Dell'Oso, PatrickLehle
 * @author Thies
 */
public class GroceryListMapper {

	/**
	 * Singleton Klasse -> es gibt immer nur eine Instanz dieser Klasse.
	 */
	private static GroceryListMapper groceryListMapper = null;

	/**
	 * Protected Construcor
	 */
	protected GroceryListMapper() {

	}

	/**
	 * Statische Methode, die die Single Eingenschaft sicherstellt, indem sie nur
	 * eine neue Instanz erstellt, wenn keine vorhanden ist.
	 * 
	 * @return GroceryListMapper-Objekt
	 */
	public static GroceryListMapper groceryListMapper() {
		if (groceryListMapper == null) {
			groceryListMapper = new GroceryListMapper();
		}

		return groceryListMapper;
	}

	/**
	 * 
	 * Sucht eine GroceryList anhand ihrer ID
	 * 
	 * @param zu
	 *            Suchende id
	 * @return Das GroceryList-Objekt, falls ein passendes gefunden wurde.
	 * @throws DatabaseException
	 */
	public GroceryList findByKey(int id) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM grocerylist WHERE id=" + id);

			// Ergebnis wird nur ein Objekt sein
			if (rs.next()) {
				GroceryList gl = new GroceryList();
				gl.setId(rs.getInt("id"));
				gl.setGroceryListName(rs.getString("name"));
				gl.setCreationDat(rs.getTimestamp("creationDat"));
				gl.setModDat(rs.getTimestamp("modDat"));
				gl.setGroupId(rs.getInt("groupId"));
				return gl;
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return null;
	}

	/**
	 * Sucht alle groceryLists
	 * 
	 * @return Vector mit allen gefundenen groceryLists
	 * @throws DatabaseException
	 */
	public Vector<GroceryList> findAll() throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<GroceryList> groceryLists = new Vector<GroceryList>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM grocerylist");

			// Fuer jede gefundene GL wird ein neues Objekt erstellt
			while (rs.next()) {
				GroceryList gl = new GroceryList();
				gl.setId(rs.getInt("id"));
				gl.setGroceryListName(rs.getString("name"));
				gl.setCreationDat(rs.getTimestamp("creationDat"));
				gl.setModDat(rs.getTimestamp("modDat"));
				gl.setGroupId(rs.getInt("groupId"));

				groceryLists.addElement(gl);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return groceryLists;
	}

	/**
	 * Fuegt in der Datenbank eine neue Einkaufsliste ein
	 * 
	 * @param GL-Objekt
	 *            das in die DB eingefuegt werden soll
	 * @return Die Eingefuegte GL mit aktueller ID
	 * @throws DatabaseException
	 */
	public GroceryList insert(GroceryList gl) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String maxId = "SELECT MAX(id) AS maxid FROM grocerylist";

		String insert = "INSERT INTO grocerylist (id, name, creationDat, modDat,ownerId, groupId) VALUES (?,?,?,?,?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				gl.setId(rs.getInt("maxid") + 1);
			}
			stmt = con.prepareStatement(insert);

			stmt.setInt(1, gl.getId());
			stmt.setString(2, gl.getGroceryListName());
			stmt.setTimestamp(3, gl.getCreationDat());
			stmt.setTimestamp(4, gl.getModDat());
			stmt.setInt(5, gl.getOwnerId());
			stmt.setInt(6, gl.getGroupId());

			stmt.executeUpdate();

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return gl;
	}

	/**
	 * Aendert eine Einkaufsliste in der Datenbank
	 * 
	 * @param Zu
	 *            aendernde GL
	 * @return Geaenderte GL
	 * @throws DatabaseException
	 */
	public GroceryList update(GroceryList gl) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String updateSQL = "UPDATE grocerylist SET name=?, modDat=? WHERE id=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, gl.getGroceryListName());
			stmt.setTimestamp(2, gl.getModDat());
			stmt.setInt(3, gl.getId());

			stmt.executeUpdate();
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return gl;
	}

	/**
	 * Loescht eine GL aus der Datenbank
	 * 
	 * @param Zu
	 *            loeschende GL
	 * @throws DatabaseException
	 */
	public void delete(GroceryList gl) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM groceryList " + "WHERE id=" + gl.getId());

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

	public Vector<GroceryList> findGroceryListByName(String name, GroceryList gl) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String select = "SELECT * FROM grocerylist WHERE name=?";

		Vector<GroceryList> result = new Vector<GroceryList>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(select);
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				GroceryList groceryList = new GroceryList();
				groceryList.setId(rs.getInt("id"));
				groceryList.setGroceryListName(rs.getString("name"));
				groceryList.setCreationDat(rs.getTimestamp("creationDat"));
				groceryList.setModDat(rs.getTimestamp("modDat"));
				groceryList.setGroupId(rs.getInt("groupId"));

				result.addElement(gl);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	public Vector<GroceryList> findAllGroceryListByGroupId(int id) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen der Tupel aus der DB
		String selectByKey = "SELECT * FROM grocerylist WHERE groupId= " + id;

		Vector<GroceryList> result = new Vector<GroceryList>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				GroceryList gl = new GroceryList();
				gl.setId(rs.getInt("id"));
				gl.setGroceryListName(rs.getString("name"));
				gl.setCreationDat(rs.getTimestamp("creationDat"));
				gl.setModDat(rs.getTimestamp("modDat"));
				result.addElement(gl);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	public Vector<GroceryList> findAllGroceryListsByUserId(int userId) throws DatabaseException {
		Connection con;
		PreparedStatement stmt;

		String selectByKey = "SELECT `grocerylist`.*, `groups`.* FROM `grocerylist`, `groups` WHERE `groups`.`id` = `grocerylist`.`groupId` AND `grocerylist`.`ownerId` = "
				+ userId;

		Vector<GroceryList> groceryList = new Vector<GroceryList>();
		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				GroceryList gl = new GroceryList();
				gl.setId(rs.getInt(1));
				gl.setGroceryListName(rs.getString(2));
				gl.setCreationDat(rs.getTimestamp(3));
				gl.setModDat(rs.getTimestamp(4));
				gl.setOwnerId(rs.getInt(5));
				gl.setGroupId(rs.getInt(6));
				gl.setGroupName(rs.getString(8));
				groceryList.addElement(gl);
			}
			return groceryList;
		} catch (SQLException e) {
			ServersideSettings.getLogger().severe(e.getMessage());
			throw new DatabaseException(e);
		}

	}
}
