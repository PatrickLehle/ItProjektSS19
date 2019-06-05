package de.hdm.itprojektss19.team03.scart.shared.bo;

/**
 * Realisierung einer exemplarischen <Code>GroupUser</code>-Klasse.
 * Diese Klasse dient dazu, die Klassen <code>Group</code> und <code>User</code>
 * zusammenzufuehren. Es wird fuer jede Groupe mit Usern ein GroupUser Objekt erstellt.
 * 
 * @see <code>BusinessObject</code>
 * @see <code>Group</code>
 * @see <code>User</code>
 *
 */
public class GroupUser extends BusinessObject {
	
//INSTANTIATION===================================================================
	/**
	 * Eindeutige Identifikation der Version einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvatiablen der Klasse GroupUser
	 */
	private int groupId = 0; 
	private int userId = 0;
	

//METHODS=========================================================================
  //GETTER========================================================================
	/**
	 * Holen der groupId einer Gruppe
	 * @return  groupId - eindeutige id einer Gruppe
	 */
	public int getGroupId() {
		return groupId;
	}
	
	/**
	 * Holen der userId eines Users
	 * @return
	 */
	public int getUserId() {
		return userId;
	}

  //SETTER========================================================================
	/**
	 * Hier wird die Id einer Gruppe gesetzt
	 * @param groupId
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	/**
	 * Hier wird die Id eines Users gesetzt
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}