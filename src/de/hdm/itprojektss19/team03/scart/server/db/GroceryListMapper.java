package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.IntStream;

import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;

/**
 * 
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author Marco Dell'Oso, Thies
 *
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

			// Es darf nur ein Ergebinis gefunden werden, da id der Primärschlüssel ist
			if (rs.next()) {
				GroceryList groceryList = new GroceryList();
				groceryList.setId(rs.getInt("id"));
				groceryList.setGroceryListName(rs.getString("name"));
				Array array = rs.getArray("articles");
				Article[] articles = (Article[]) array.getArray();
				groceryList.setArticles(articles);
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
			ResultSet rs = statement.executeQuery("SELECT id, name, groupId, articleIds FROM GroceryLists");

			// Neues groceryList Objekt für jede gefundene ID
			while (rs.next()) {
				GroceryList groceryList = new GroceryList();
				groceryList.setId(rs.getInt("id"));
				groceryList.setGroceryListName(rs.getString("name"));
				Array array = rs.getArray("articles");
				Article[] articles = (Article[]) array.getArray();
				groceryList.setArticles(articles);
				
				groceryLists.addElement(groceryList);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return groceryLists;
	}
}
