package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.server.ServersideSettings;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroupUser;

public class GroupUserMapper {

//INSTANTIATION===============================================================================
	public static GroupUserMapper groupUserMapper = null;
	
	
//CONSTRUCTOR=================================================================================
	/**
	 * Der Geschuetzte Konstruktor dient zur Absicherung. 
	 * Nur eine Instanz darf erzeugt werden
	 */
	protected GroupUserMapper() {
	
	}
	
//METHODS=====================================================================================
	/**
	 * Hier wird geprueft ob eine Klasse schon vorhanden ist. 
	 * Methoden werden nur ueber diese statische Methode aufgerufen
	 * @return
	 */
	public static GroupUserMapper groupUserMapper() {
		if (groupUserMapper == null) {
			groupUserMapper = new GroupUserMapper();
		}
		return groupUserMapper;
	}
	
	
	public GroupUser addUserToGroup(User u, Group g) throws SQLException {

		Connection con = null;
		PreparedStatement stmt = null;
		String addString = "INSERT INTO groupuser (userId, groupId) VALUES (?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(addString);
			stmt.setInt(1, u.getId());
			stmt.setInt(2, g.getId());
			stmt.executeUpdate();
			
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return null;
	}


	public void removeUserFromGroup(User u, Group g) throws SQLException {

		Connection con = null;
		PreparedStatement stmt = null;

		String delete = "DELETE FROM groupuser WHERE userId=? AND groupId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(delete);
			stmt.setInt(1, u.getId());
			stmt.setInt(2, g.getId());
			stmt.executeUpdate();
		}
		catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
	}

	public Vector<User> findAllUserByGroup(int groupId) throws SQLException {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen der Tupel aus der DB
		String selectByKey = "SELECT groupuser.groupId, groups.name, user.id, user.name, "
				+ "user.email FROM groupuser "
				+ "JOIN user ON groupuser.userId = user.id " + "JOIN groups "
				+ "ON groupuser.groupId = groups.id "
				+ "WHERE groupuser.groupId= " + groupId;

		Vector<User> result = new Vector<User>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();
			User u = new User();
			u.setUsername("name");

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setEmail(rs.getString("email"));
			
				result.addElement(u);
			}
		}
		catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return result;
	}

	public void deleteUserFromAllGroups(User u) throws SQLException {

		Connection con = null;
		PreparedStatement stmt = null;
		String delete = "DELETE FROM groupuser WHERE userId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(delete);
			stmt.setInt(1, u.getId());
			stmt.executeUpdate();
		}
		catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
	}
	
	public Vector<Group> findAllGroupsByUserId(int userId) throws SQLException {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen der Tupel aus der DB
		String selectByKey = "SELECT groupuser.groupId, groups.name, user.id, user.name, "
				+ "user.email FROM groupuser, groups, user "
				+ "JOIN user ON groupuser.userId = user.id " + "JOIN groups "
				+ "ON groupuser.groupId = groups.id "
				+ "WHERE groupuser.userId= " + userId;

		Vector<Group> result = new Vector<Group>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();
			Group group = new Group();
			group.setGroupName("name");

			while (rs.next()) {
				Group g = new Group();
				g.setId(rs.getInt("id"));
				g.setGroupName(rs.getString("name"));
			
				result.addElement(g);
			}
		}
		catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return result;
	}
}
