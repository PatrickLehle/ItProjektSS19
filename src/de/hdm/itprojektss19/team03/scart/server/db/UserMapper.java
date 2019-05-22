package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author JulianHofer
 * @author Thies
 * 
 */

public class UserMapper {

	private static UserMapper userMapper = null;

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
			ResultSet rs = statement.executeQuery("SELECT id, name, email FROM users");
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
	 * Sucht alle User anhand der ID
	 * 
	 * @param Die ID des Users
	 * @return Vector mit allen gefunden Usern mit entsprechender Id
	 */
	public User findbyUserName(int username) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name, email FROM users" + "WHERE name=" + username);
			// Neues user Objekt f�r jede gefundene ID
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setEmail(rs.getString("email"));

				return user;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * Sucht alle User anhand der ID
	 * 
	 * @param Die ID des Users
	 * @return Vector mit allen gefunden Usern mit entsprechender Id
	 */
	public User findbyUserId(int userId) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name, email FROM users" + "WHERE users=" + userId);
			// Neues user Objekt f�r jede gefundene ID
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setEmail(rs.getString("email"));

				return user;
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
	public User findByUserEmail(String userEmail) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name, email FROM users" + "WHERE email=" + userEmail);
			// Neues user Objekt f�r jede gefundene ID
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setEmail(rs.getString("email"));

				return user;
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
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();

			// Suche die aktuell h�chsten ID
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM users ");

			if (rs.next()) {
				// H�chste ID um 1 erh�hen, um n�chste ID zu erhalten
				user.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO users (id, name, email) " + "VALUES (" + user.getId() + ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return user;
	}
	/**
	 * �ndert einen User in der Datenbank
	 * 
	 * @param Zu �ndernder User
	 * @return Ge�nderter User
	 */
	public User update(User user) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE users " + "SET name=\"" + user.getUsername()  + "\" " + "SET email=\"" + user.getEmail() + "\" "
					+ "WHERE id=" + user.getId());

		} catch (SQLException e2) {
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

			stmt.executeUpdate("DELETE FROM users " + "WHERE id=" + user.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	

}
