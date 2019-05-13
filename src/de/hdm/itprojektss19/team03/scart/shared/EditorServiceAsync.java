package de.hdm.itprojektss19.team03.scart.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public interface EditorServiceAsync {

	void getGroceryListByOwner(User u, AsyncCallback<Vector<GroceryList>> asyncCallback);

	void statusSharingGroceryList(Vector<GroceryList> result, AsyncCallback<Vector<GroceryList>> asyncCallback);

	void getGroceryListById(Object groceryListId, AsyncCallback<GroceryList> asyncCallback);

	void getGroupByGroceryList(int groceryListId, AsyncCallback<Vector<Group>> asyncCallback);

	void statusSharingGroup(Vector<Group> result, AsyncCallback<Vector<Group>> asyncCallback);

}
