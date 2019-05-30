package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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
	 * @param zu Suchende id
	 * @return Das GroceryList-Objekt, falls ein passendes gefunden wurde.
	 */
	public GroceryList findByKey(int id) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT id, name, groupId, articleIds FROM GroceryLists WHERE id=" + id);

			// Es darf nur ein Ergebinis gefunden werden, da id der Prim�rschl�ssel ist
			if (rs.next()) {
				GroceryList groceryList = new GroceryList();
				groceryList.setId(rs.getInt("id"));
				groceryList.setGroceryListName(rs.getString("name"));
				return groceryList;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Sucht alle groceryLists
	 * 
	 * @return Vector mit allen gefundenen groceryLists
	 */
	public Vector<GroceryList> findAll() {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		Vector<GroceryList> groceryLists = new Vector<GroceryList>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM GroceryList");

			// Neues groceryList Objekt f�r jede gefundene ID
			while (rs.next()) {
				GroceryList groceryList = new GroceryList();
				groceryList.setId(rs.getInt("id"));
				groceryList.setGroceryListName(rs.getString("name"));
				groceryLists.addElement(groceryList);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return groceryLists;
	}
	
	/**
	 * Fuegt in der Datenbank eine neue Einkaufsliste ein
	 * 
	 * @param GL-Objekt das in die DB eingefuegt werden soll
	 * @return Die Eingefuegte GL mit aktueller ID
	 */
	public GroceryList insert(GroceryList gl) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();

			// Suche die aktuell hoechsten ID
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM groceryLists ");

			if (rs.next()) {
				// Hoechste ID um 1 erhoehen, um naechste ID zu erhalten
				gl.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO groceryLists (id, name) " + "VALUES (" + gl.getId() + ",'" + gl.getGroceryListName() + "')");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return gl;
	}			
		
	/**
	 * Aendert eine Einkaufsliste in der Datenbank
	 * 
	 * @param Zu aendernde GL
	 * @return Geaenderte GL
	 */
	public GroceryList update(GroceryList gl) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE groups " + "SET name=\"" + gl.getGroceryListName() + "WHERE id=" + gl.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return gl;
	}
	
	/**
	 * Loescht eine GL aus der Datenbank
	 * 
	 * @param Zu loeschende GL
	 */
	public void delete(GroceryList gl) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM groceryLists " + "WHERE id=" + gl.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
}
