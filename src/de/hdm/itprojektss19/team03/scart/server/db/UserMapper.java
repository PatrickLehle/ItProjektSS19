package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.db.DBConnection;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author JulianHofer, PatrickLehle
 * @author Thies
 * 
 */

public class UserMapper {

	private static UserMapper userMapper = null;
	
	/**
	 * Beugt mehrfach Instanzierung vor.
	 */
	protected UserMapper() {
	};

	public static UserMapper userMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}

	/**
	 * Sucht alle User
	 * 
	 * @return Vector mit allen gefundenen Usern
	 */
	public Vector<User> findAll() {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		Vector<User> users = new Vector<User>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM user");
			// Neues user Objekt f�r jede gefundene ID
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				users.addElement(user);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return users;
	}
	/**
	 * Gibt einen Vecotr aller User mit dem selben Namen zurueck
	 * @param name
	 * @param u
	 * @return Vecotr mit allen Usern die den selben Namen tragen
	 */
	public Vector<User> findUserByName(String name, User u){
		Connection con = null;
		PreparedStatement stmt = null;

		String select = "SELECT * FROM user WHERE name=?";

		Vector<User> result = new Vector<User>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(select);
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setEmail(rs.getString("email"));

				result.addElement(u);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return result;
	}
	
	/**
	 * Sucht einen User anhand der eindeutigen ID
	 * 
	 * @param Die ID des Users
	 * @return User mit der entsprechenden ID
	 */
	public User findbyUserId(int userId) {
		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen des Nutzertupels aus der DB
		String selectByKey = "SELECT * FROM user WHERE id=?";

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			// Aufbereitung des vorbereitenden Statements
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, userId);

			// Ausfuehren des SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				User u = new User();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				u.setId(rs.getInt(1));
				u.setEmail(rs.getString(2));
				u.setUsername(rs.getString(3));

				return u;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	/**
	 * Sucht alle User anhand der Email
	 * 
	 * @param Die Email des Users
	 * @return Vector mit allen gefunden Usern mit entsprechender Email
	 */
	public User findUserByEmail(String userEmail) {
		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen des Nutzertupels aus der DB
		String key = "SELECT * FROM user WHERE email=?";

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			// Aufbereitung des vorbereitenden Statements
			stmt = con.prepareStatement(key);
			stmt.setString(1, userEmail);

			// Ausfuehren des SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				User u = new User();
				u.setId(rs.getInt(1));
				u.setEmail(rs.getString(2));

				con.close();
				return u;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * F�gt in der Datenbank einen neuen User ein
	 * 
	 * @param User-Objekt das in die DB eingef�gt werden soll
	 * @return Der Eingef�gte User mit aktueller ID
	 */
	public User insert(User user) {
		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer die Abfrage der hoechsten ID (Primaerschluessel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM user";

		// SQL-Anweisung zum Einfuegen des neuen Nutzertupels in die DB
		String insertSQL = "INSERT INTO user (id, email, name) VALUES (?,?,?)";

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxIdSQL);

			// MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();

			// Damit dieser daraufhin um 1 inkrementiert der ID des BO zugewiesen wird
			if (rs.next()) {
				user.setId(rs.getInt("maxid") + 1);
			}

			// Jetzt erfolgt das Einfuegen des Objekts
			stmt = con.prepareStatement(insertSQL);

			// Setzen der ? als Platzhalter fuer den Wert
			stmt.setInt(1, user.getId());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getUsername());

			// Ausfuehren des SQL Statement
			stmt.executeUpdate();

			// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return user;
	}
	/**
	 * Ein user wird in der DB nachträglich auf den neusten Stand gebracht
	 * 
	 * @param Zu �ndernder User
	 * @return Ge�nderter User
	 */
	public User update(User user) {
		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Einfuegen des Tupels in die DB
		String updateSQL = "UPDATE user SET email=?, name=? WHERE id=?";

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getUsername());
			stmt.setInt(3, user.getId());
	
			// Ausfuehren des SQL-Statements
			stmt.executeUpdate();
		}
		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return user;
	}
	
	/**
	 * L�scht einen User aus der Datenbank
	 * 
	 * @param Zu l�schender User
	 */
	public void delete(User user) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM user " + "WHERE id=" + user.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	

}
