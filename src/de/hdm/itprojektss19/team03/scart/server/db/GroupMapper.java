package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;


/**
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author JulianHofer
 * @author Thies
 * 
 */

public class GroupMapper {

	private static GroupMapper groupMapper = null;
	
	public static GroupMapper groupMapper() {
		if (groupMapper == null) {
			groupMapper = new GroupMapper();
		}
		return groupMapper;
	}
	
	/**
	 * Sucht alle Gruppen
	 * 
	 * @return Vector mit allen gefundenen Gruppen
	 */
	public Vector<Group> findAll() {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		Vector<Group> groups = new Vector<Group>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name FROM groups");
			// Neues Group Objekt fuer jede gefundene ID
			while (rs.next()) {
				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setGroupName(rs.getString("name"));

				groups.addElement(group);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return groups;
	}
	
	/**
	 * Sucht alle Groups anhand der ID
	 * 
	 * @param Die ID der Group
	 * @return Vector mit allen gefunden Groups mit entsprechender Id
	 */
	
	public Group findbyGroupId(int groupId) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name FROM groups WHERE id=" + groupId);
			// Neues Group Objekt fuer gefundene ID
			while (rs.next()) {
				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setGroupName(rs.getString("name"));
				return group;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;	
	}
	
	public Group findbyGroupName(int name) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name FROM groups WHERE name=" + name);
			// Neues Group Objekt fuer gefundene ID
			while (rs.next()) {
				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setGroupName(rs.getString("name"));
				return group;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}
		
	/**
	 * Fuegt in der Datenbank eine neue Gruppe ein
	 * 
	 * @param Group-Objekt das in die DB eingefuegt werden soll
	 * @return Die Eingefuegte Gruppe mit aktueller ID
	 */
	public Group insert(Group group) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();

			// Suche die aktuell hoechsten ID
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM groups ");

			if (rs.next()) {
				// Hoechste ID um 1 erhoehen, um naechste ID zu erhalten
				group.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO groups (id, name, userId) " + "VALUES (" + group.getId() + ",'" 
				+ group.getGroupName() + "'),'" + UserMapper.userMapper().findbyUserId(group.getUsers().get(0).getId()) + "')");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return group;
	}			
		
	/**
	 * Aendert einen Geruppe in der Datenbank
	 * 
	 * @param Zu aendernde Gruppe
	 * @return Geaenderte Gruppe
	 */
	public Group update(Group group) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE groups " + "SET name=" + group.getGroupName() + "userId=" + group.getUsers() + "WHERE id=" + group.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return group;
	}
	
	/**
	 * Loescht eine Gruppe aus der Datenbank
	 * 
	 * @param Zu loeschende Gruppe
	 */
	public void delete(Group group) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM groups " + "WHERE id=" + group.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
}
