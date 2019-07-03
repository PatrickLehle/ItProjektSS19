package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.ServersideSettings;
import de.hdm.itprojektss19.team03.scart.shared.DatabaseException;
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
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *                           Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Group> findAll() throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Group> groups = new Vector<Group>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM groups");

			// fuer jede Gruppe die gefunden wird, wird ein neues G-Object erstellt
			while (rs.next()) {
				Group group = new Group();
				group.setId(rs.getInt("groupId"));
				group.setGroupName(rs.getString("groupName"));

				groups.addElement(group);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return groups;
	}

	/**
	 * Sucht alle Groups anhand der ID
	 * 
	 * @param groupId beschreibt die Eindeutigkeit einer Gruppe via Id
	 * @return Vector mit allen gefunden Groups mit entsprechender Id
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *                           Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Group findByGroupId(int groupId) throws DatabaseException {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM groups WHERE groupId=" + groupId);
			// Neues Group Objekt fuer gefundene ID
			while (rs.next()) {
				Group group = new Group();
				group.setId(rs.getInt("groupId"));
				group.setGroupName(rs.getString("groupName"));
				return group;
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return null;
	}

	public Vector<Group> findGroupByName(String name, Group g) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;

		String select = "SELECT * FROM groups WHERE groupName=?";

		Vector<Group> result = new Vector<Group>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(select);
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Group group = new Group();
				group.setId(rs.getInt("groupId"));
				group.setGroupName(rs.getString("groupName"));

				result.addElement(g);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	/**
	 * Fuegt in der Datenbank eine neue Gruppe ein
	 * 
	 * @param group beschreibt ein Gruppen Objekt
	 * @return Die Eingefuegte Gruppe mit aktueller ID
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *                           Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Group insert(Group group) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;

		String maxId = "SELECT MAX(groupId) AS maxid FROM groups";
		String insert = "INSERT INTO groups (groupId, groupName) VALUES (?,?)";

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
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return group;
	}

	/**
	 * Aendert einen Geruppe in der Datenbank
	 * 
	 * @param group beschreibt ein Gruppen Objekt
	 * @return Geaenderte Gruppe
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *                           Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Group update(Group group) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String updateSQL = "UPDATE groups SET groupName=? WHERE groupId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, group.getGroupName());
			stmt.setInt(2, group.getId());

			stmt.executeUpdate();
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return group;
	}

	/**
	 * Loescht eine Gruppe aus der Datenbank
	 * 
	 * @param group beschreibt ein Gruppen Objekt
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *                           Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public void delete(Group group) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String delete = "DELETE FROM groups WHERE groupId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(delete);
			stmt.setInt(1, group.getId());

			stmt.executeUpdate();
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

}
