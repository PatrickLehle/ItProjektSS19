package de.hdm.itprojektss19.team03.scart.shared;

import java.sql.Timestamp;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryListArticle;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroupUser;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
//import de.hdm.itprojektss19.team03.scart.shared.bo.Unit;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public interface EditorServiceAsync {
	void generateIdenticons(User u, int image_width, int image_height, AsyncCallback<String> asyncCallback);

	void init(AsyncCallback<Void> callback);

	// USER===========================================================================

	void createUser(User u, AsyncCallback<User> asyncCallback);

	void createUser(String emailAdress, AsyncCallback<User> asyncCallback);

	void deleteUser(User u, AsyncCallback<Void> asyncCallback);

	void updateUser(User u, AsyncCallback<User> asyncCallback);

	void getUserById(int userId, AsyncCallback<User> asyncCallback);

	void getUserByGMail(String email, AsyncCallback<User> callback);

	void getOwnProfile(User u, AsyncCallback<User> callback);

	// GROUP===========================================================================

	void createGroup(Group g, AsyncCallback<Group> asyncCallback);

	void saveGroup(Group g, AsyncCallback<Void> asyncCallback);

	void deleteGroup(Group g, AsyncCallback<Void> asyncCallback);

	void getGroupById(int groupId, AsyncCallback<Group> asyncCallback);

	void getAllGroupsByUser(User u, AsyncCallback<Vector<Group>> asyncCallback);

	void addUserToGroup(User user, Group group, AsyncCallback<GroupUser> asyncCallback);

	void leaveGroup(User u, Group g, AsyncCallback<Void> asyncCallback);

	void findAllGroups(AsyncCallback<Vector<Group>> asyncCallback);

	void statusSharingGroup(Vector<Group> result, AsyncCallback<Vector<Group>> asyncCallback);

	// GROUP-USER========================================================================

	void findAllGroupsByUserId(int id, AsyncCallback<Vector<Group>> asyncCallback);

	void getAllUserByGroupId(int id, AsyncCallback<Vector<User>> asyncCallback);

	void removeUserFromGroup(User u, Group g, AsyncCallback<Void> asyncCallback);

	// GROCERYLIST===========================================================================

	void createGroceryList(GroceryList gl, AsyncCallback<GroceryList> asyncCallback);

	void saveGroceryList(GroceryList gl, AsyncCallback<GroceryList> asyncCallback);

	void deleteGroceryList(GroceryList gl, AsyncCallback<GroceryList> asyncCallback);

	void getGroceryListByOwner(User u, AsyncCallback<Vector<GroceryList>> asyncCallback);

	void getGroceryListById(int groceryListId, AsyncCallback<GroceryList> asyncCallback);

	void getGroupByGroceryList(int groceryListId, AsyncCallback<Group> asyncCallback);

	void statusSharingGroceryList(Vector<GroceryList> result, AsyncCallback<Vector<GroceryList>> asyncCallback);

	void findAllGroceryListByGroupId(int id, AsyncCallback<Vector<GroceryList>> asyncCallback);

	void findAllGroceryLists(AsyncCallback<Vector<GroceryList>> asyncCallback);

	void findAllGroceryListByUserId(int userId, AsyncCallback<Vector<GroceryList>> asyncCallback);

	void getAllGroceryListsByGroupVector(Vector<Group> g, AsyncCallback<Vector<GroceryList>> asyncCallback);

	// GROCERYLIST-ARTICLE=================================================================

	void findAllArticleByGroceryListId(int id, AsyncCallback<Vector<Article>> asyncCallback);

	void findAllFavArticleByGroup(Group g, AsyncCallback<Vector<Article>> asyncCallback);

	void findAllArticleByGroceryListIdAndRetailerId(int groceryListId, int retailerId,
			AsyncCallback<Vector<Article>> asyncCallback);

	// ARTICLE===========================================================================

	void createArticle(Article a, AsyncCallback<Article> asyncCallback);

	void saveArticle(Article a, AsyncCallback<Article> asyncCallback);

	void deleteArticle(Article a, AsyncCallback<Article> asyncCallback);

	void getArticleById(int articleId, AsyncCallback<Article> asyncCallback);

	void getArticleByArticleId(int id, AsyncCallback<Article> asyncCallback);

	void findAllArticle(AsyncCallback<Vector<Article>> asyncCallback);

	void findAllArticleByGroupIdReport(int groupId, AsyncCallback<Vector<Article>> asyncCallback);

	void findAllArticleByOwnerId(int ownerId, AsyncCallback<Vector<Article>> asyncCallback);

	void getAllArticleByName(String name, int groupId, AsyncCallback<Vector<Article>> asyncCallback);

	// ARTICLE-REPORT==============================================================================

	void findAllArticleByFavouriteTRUE(Vector<Group> groups, AsyncCallback<Vector<Article>> asyncCallback);

	void findAllArticleByRetailerFavouriteTRUE(Vector<Group> groups, Vector<Retailer> retailers,
			AsyncCallback<Vector<Article>> asyncCallback);

	void findAllArticleByDateFavouriteTRUE(Vector<Group> groups, Timestamp start, Timestamp end,
			AsyncCallback<Vector<Article>> asyncCallback);

	void findAllArticleByDateRetailerFavouriteTRUE(Vector<Group> groups, Vector<Retailer> retailers, Timestamp start,
			Timestamp end, AsyncCallback<Vector<Article>> asyncCallback);

	// RETAILER===========================================================================

	void createRetailer(Retailer r, AsyncCallback<Retailer> asyncCallback);

	void saveRetailer(Retailer r, AsyncCallback<Retailer> asyncCallback);

	void deleteRetailer(Retailer r, AsyncCallback<Retailer> asyncCallback);

	void findAllRetailer(AsyncCallback<Vector<Retailer>> asyncCallback);

	void findAllRetailerReport(AsyncCallback<Vector<Retailer>> asyncCallback);

	void getAllRetailerByGroupId(int groupId, AsyncCallback<Vector<Retailer>> asyncCallback);

	void getAllArticleByRetailer(Retailer r, AsyncCallback<Vector<Article>> asyncCallback);

	void getAllArticleByDate(Timestamp start, Timestamp end, AsyncCallback<Vector<Article>> asyncCallback);

	void getAllArticleByDateRetailer(int id, Timestamp start, Timestamp end,
			AsyncCallback<Vector<Article>> asyncCallback);

	void getRetailerById(int retailerId, AsyncCallback<Retailer> asyncCallback);

	void getAllDistinctRetailerByGroupAndGroceryList(Group group, GroceryList gl,
			AsyncCallback<Vector<Retailer>> asyncCallback);

	void getAllDistinctRetailerByGroceryList(GroceryList gl, AsyncCallback<Vector<Retailer>> asyncCallback);

	// GroceryListArticle===========================================================================

	void addArticleToGroceryList(GroceryList gl, Article a, AsyncCallback<GroceryListArticle> asyncCallback);

	void removeArticleFromGroceryList(GroceryList gl, Article a, AsyncCallback<Void> asyncCallback);

	void findAllArticleByGroceryList(GroceryList groceryList, AsyncCallback<Vector<Article>> asyncCallback);

	void deleteArticleFromAllLists(Article a, AsyncCallback<Void> asyncCallback);

	void deleteAllArticlesFromGroceryList(GroceryList gl, AsyncCallback<Void> asyncCallback);
}
