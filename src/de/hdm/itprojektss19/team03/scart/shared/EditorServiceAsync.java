package de.hdm.itprojektss19.team03.scart.shared;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;


import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Entry;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.Unit;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public interface EditorServiceAsync {
	
	void init(AsyncCallback<Void> callback);
	
//USER===========================================================================
	
	void createUser(String username, String emailAdress, AsyncCallback <User> asyncCallback);
	
	void createUser(String emailAdress, AsyncCallback <User> asyncCallback);
	
	void deleteUser(User u, AsyncCallback<Void> asyncCallback);
	
	void getUserById(int userId, AsyncCallback<User> callback);
	
	void getUserByGMail(String email, AsyncCallback<User> callback);

//GROUP===========================================================================
	
	void createGroup(Group g, AsyncCallback <Group> asyncCallback);
	
	void saveGroup(Group g, AsyncCallback<Void> asyncCallback);
	
	void deleteGroup(Group g, AsyncCallback<Void> asyncCallback);
	
	void getGrouById(int groupId, AsyncCallback<Group> asyncCallback);
	
	void getAllGroupsByUser(User u, AsyncCallback<Vector<Group>> asyncCallback);

	void addUserToGroup(User u, Group g, AsyncCallback<Void> asyncCallback);
	
	void leaveGroup(User u, Group g, AsyncCallback <Void> asyncCallback);
	
	void statusSharingGroup(Vector<Group> result, AsyncCallback<Vector<Group>> asyncCallback);

//GROCERYLIST===========================================================================
	
	void createGroceryList(String name, GroceryList gl, AsyncCallback <GroceryList> asyncCallback);
	
	void saveGroceryList(GroceryList gl, AsyncCallback <Void> asyncCallback);
	
	void deleteGroceryList(GroceryList gl, AsyncCallback<Void> asyncCallback);
	
	void getGroceryListByOwner(User u, AsyncCallback<Vector<GroceryList>> asyncCallback);

	void getGroceryListById(int groceryListId, AsyncCallback<GroceryList> asyncCallback);

	void getGroupByGroceryList(int groceryListId, AsyncCallback<Vector<Group>> asyncCallback);
	
	void statusSharingGroceryList(Vector<GroceryList> result, AsyncCallback<Vector<GroceryList>> asyncCallback);


//ARTICLE===========================================================================	
	
	void createArticle(Article a, AsyncCallback<Article> asyncCallback);
	
	void saveArticle(Article a, AsyncCallback<Void> asyncCallback);
	
	void deleteArticle(Article a, AsyncCallback<Void> asyncCallback);
	
	void getArticleById(int articleId, AsyncCallback<Article> asyncCallback);
	
//RETAILER===========================================================================
	
	void createRetailer(Retailer r, AsyncCallback<Retailer> asyncCallback);
	
	void saveRetailer(Retailer r, AsyncCallback<Void> asyncCallback);
	
	void deleteRetailer(Retailer r, AsyncCallback<Void> asyncCallback);
	
	void getArticleByRetailer(Retailer r, AsyncCallback<Article> asyncCallback);
	
	void getArticleByDate(Date start, Date end, AsyncCallback<Vector<Article>> asyncCallback);
	
	void getArticleByDateRetailer(Date start, Date end, Retailer r, AsyncCallback<Vector<Article>> asyncCallback);
	
	void getRetailerById(int retailerId, AsyncCallback<Retailer> asyncCallback);

//UNIT===========================================================================
	
	void createUnit(Unit u, AsyncCallback <Unit> asyncCallback);
	
	void saveUnit(Unit u, AsyncCallback<Void> asyncCallback);
	
	void deleteUnit(Unit u, AsyncCallback<Void> asyncCallback);
	
	void getUnitById(int unitId, AsyncCallback<Unit> asyncCallback);
	
	void getUnitByName(String unitName, AsyncCallback<Unit> asyncCallback);

//ENTRY===========================================================================
	
	void createEntry(Entry e, AsyncCallback<Entry> asyncCallback);
	
	void saveEntry(Entry e, AsyncCallback<Void> asyncCallback);
	
	void deleteEntry(Entry e, AsyncCallback<Void> asyncCallback);

	
	

}
