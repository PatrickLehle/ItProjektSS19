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
	 *  Sucht eine GroceryList anhand ihrer ID
	 * @param id beschreibt die Eindeutigkeit einer grocerylist via id
	 * @return Das GroceryList-Objekt, falls ein passendes gefunden wurde.
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public GroceryList findByKey(int id) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM grocerylist WHERE groceryListId=" + id);

			// Ergebnis wird nur ein Objekt sein
			if (rs.next()) {
				GroceryList gl = new GroceryList();
				gl.setId(rs.getInt("groceryListId"));
				gl.setGroceryListName(rs.getString("groceryListName"));
				gl.setCreationDat(rs.getTimestamp("groceryListCreationDat"));
				gl.setModDat(rs.getTimestamp("groceryListModDat"));
				gl.setGroupId(rs.getInt("groceryListGroupId"));
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
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
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
				gl.setId(rs.getInt("groceryListId"));
				gl.setGroceryListName(rs.getString("groceryListName"));
				gl.setCreationDat(rs.getTimestamp("groceryListCreationDat"));
				gl.setModDat(rs.getTimestamp("groceryListModDat"));
				gl.setGroupId(rs.getInt("groceryListGroupId"));

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
	 * @param gl beschreibt ein GroceryList Objekt
	 * @return Die Eingefuegte GL mit aktueller ID
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public GroceryList insert(GroceryList gl) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String maxId = "SELECT MAX(groceryListId) AS maxid FROM grocerylist";

		String insert = "INSERT INTO grocerylist (groceryListId, groceryListName, groceryListCreationDat, groceryListModDat,groceryListOwnerId, groceryListGroupId) VALUES (?,?,?,?,?,?)";

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
	 * @param gl beschreibt ein groceryList Objekt
	 * @return Geaenderte GL
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public GroceryList update(GroceryList gl) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String updateSQL = "UPDATE grocerylist SET groceryListName=?, groceryListModDat=? WHERE groceryListId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, gl.getGroceryListName());
			stmt.setTimestamp(2, gl.getNewModDat());
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
	 * @param gl beschreibt ein GroceryList Objekt
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public void delete(GroceryList gl) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM groceryList " + "WHERE groceryListId=" + gl.getId());

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

	public Vector<GroceryList> findGroceryListByName(String name, GroceryList gl) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String select = "SELECT * FROM grocerylist WHERE groceryListName=?";

		Vector<GroceryList> result = new Vector<GroceryList>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(select);
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				GroceryList groceryList = new GroceryList();
				groceryList.setId(rs.getInt("groceryListId"));
				groceryList.setGroceryListName(rs.getString("groceryListName"));
				groceryList.setCreationDat(rs.getTimestamp("groceryListCreationDat"));
				groceryList.setModDat(rs.getTimestamp("groceryListModDat"));
				groceryList.setGroupId(rs.getInt("groceryListGroupId"));

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
		String selectByKey = "SELECT * FROM grocerylist WHERE groceryListGroupId= " + id;

		Vector<GroceryList> result = new Vector<GroceryList>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				GroceryList gl = new GroceryList();
				gl.setId(rs.getInt("groceryListId"));
				gl.setGroceryListName(rs.getString("groceryListName"));
				gl.setCreationDat(rs.getTimestamp("groceryListCreationDat"));
				gl.setModDat(rs.getTimestamp("groceryListModDat"));
				gl.setGroupId(rs.getInt("groceryListGroupId"));
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

		String selectByKey = "SELECT `grocerylist`.*, `groups`.* FROM `grocerylist`, `groups` WHERE `groups`.`groupId` = `grocerylist`.`groceryListGroupId` AND `grocerylist`.`groceryListOwnerId` = "
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
