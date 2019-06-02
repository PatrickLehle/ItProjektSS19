package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.db.DBConnection;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;


/**
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author JulianHofer, PatrickLehle
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
		Connection con = DBConnection.connection();

		Vector<Group> groups = new Vector<Group>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM groups");
		
			//fuer jede Gruppe die gefunden wird, wird ein neues G-Object erstellt
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
	
	public Group findByGroupId(int groupId) {
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
	
	public Vector<Group> findGroupByName(String name, Group g) {

		Connection con = null;
		PreparedStatement stmt = null;

		String select = "SELECT * FROM groups WHERE name=?";

		Vector<Group> result = new Vector<Group>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(select);
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setGroupName(rs.getString("name"));

				result.addElement(g);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return result;
	}
		
	/**
	 * Fuegt in der Datenbank eine neue Gruppe ein
	 * 
	 * @param Group-Objekt das in die DB eingefuegt werden soll
	 * @return Die Eingefuegte Gruppe mit aktueller ID
	 */
	public Group insert(Group group) {

		Connection con = null;
		PreparedStatement stmt = null;
		
		String maxId = "SELECT MAX(id) AS maxid FROM groups";
		String insert = "INSERT INTO groups (id, name) VALUES (?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				group.setId(rs.getInt("maxid") + 1);
			}
			stmt = con.prepareStatement(insert);
			stmt.setInt(1, group.getId());
			stmt.setString(2, group.getGroupName());
			stmt.executeUpdate();

		} catch (SQLException e2) {
			e2.printStackTrace();
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
		Connection con = null;
		PreparedStatement stmt = null;
		
		String updateSQL = "UPDATE groups SET name=? WHERE id=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, group.getGroupName());
			stmt.setInt(2, group.getId());
			
			stmt.executeUpdate();
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return group;
	}
	
	/**
	 * Loescht eine Gruppe aus der Datenbank
	 * 
	 * @param Zu loeschende Gruppe
	 */
	public void delete(Group group) {
		Connection con = null;
		PreparedStatement stmt = null;

		String delete = "DELETE FROM groups WHERE id=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(delete);
			stmt.setInt(1, group.getId());
			
			stmt.executeUpdate();
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
}
