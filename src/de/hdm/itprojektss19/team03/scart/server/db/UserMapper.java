package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.ServersideSettings;
import de.hdm.itprojektss19.team03.scart.shared.DatabaseException;
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
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<User> findAll() throws DatabaseException {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		Vector<User> users = new Vector<User>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM user");
			// Neues user Objekt f�r jede gefundene ID
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("userId"));
				user.setUsername(rs.getString("userName"));
				user.setEmail(rs.getString("userEmail"));
				users.addElement(user);
			}

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return users;
	}

	/**
	 * Gibt einen Vecotr aller User mit dem selben Namen zurueck
	 * 
	 * @param name beschreibt den Namen des Users
	 * @param u beschreibt den uebergebenen User
	 * @return Vector mit allen Usern die den selben Namen tragen
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<User> findUserByName(String name, User u) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String select = "SELECT * FROM user WHERE userName=?";

		Vector<User> result = new Vector<User>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(select);
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("userId"));
				user.setUsername(rs.getString("userName"));
				user.setEmail(rs.getString("userEmail"));

				result.addElement(u);
			}

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	/**
	 * Sucht einen User anhand der eindeutigen ID
	 * @param userId beschreibt die Eindeutigkeit eines Users via id
	 * @return  User mit der entsprechenden ID
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public User getUserById(int userId) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen des Nutzertupels aus der DB
		String selectByKey = "SELECT * FROM user WHERE userId=?";

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
				u.setId(rs.getInt("userId"));
				u.setEmail(rs.getString("userName"));
				u.setUsername(rs.getString("userEmail"));

				return u;
			}

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return null;
	}
	
	/**
	 * Sucht alle User anhand der Email
	 * @param userEmail beschreibt den user via der hinterlegten Email Adresse
	 * @return Vector mit allen gefunden Usern mit entsprechender Email
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public User findUserByEmail(String userEmail) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen des Nutzertupels aus der DB
		String selectByKey = "SELECT * FROM user WHERE userEmail=?";
		User u = new User();

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			// Aufbereitung des vorbereitenden Statements
			stmt = con.prepareStatement(selectByKey);
			stmt.setString(1, userEmail);

			// Ausfuehren des SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				u.setId(rs.getInt("userId"));
				u.setEmail(rs.getString("userName"));
				u.setUsername(rs.getString("userEmail"));

				return u;
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return null;
	}
	
	/**
	 * F�gt in der Datenbank einen neuen User ein
	 * @param user beschreibt den uebergebenen User
	 * @return User wird zurück gegeben
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public User insert(User user) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Einfuegen des neuen Nutzertupels in die DB
		String insertSQL = "INSERT INTO user (userEmail, userName) VALUES (?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(insertSQL);
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getUsername());

			stmt.executeUpdate();

			return user;
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}

	}

	
	/**
	 * User wird in der Datenbank nachträglich auf den neusten Stand gebracht
	 * @param user beschreibt den uebergebenen User
	 * @return gibt den upgedateten User zurueck
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public User update(User user) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Einfuegen des Tupels in die DB
		String updateSQL = "UPDATE user SET userEmail=?, userName=? WHERE userId=?";

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getUsername());
			stmt.setInt(3, user.getId());

			// Ausfuehren des SQL-Statements
			stmt.executeUpdate();

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return user;
	}

	/**
	 * Löschen eines Users aus der Datenbank
	 * @param user beschreibt den uebergebenen User
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public void delete(User user) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM user " + "WHERE userId=" + user.getId());

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

}
