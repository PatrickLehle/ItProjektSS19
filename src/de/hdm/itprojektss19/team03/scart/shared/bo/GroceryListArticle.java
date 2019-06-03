package de.hdm.itprojektss19.team03.scart.shared.bo;

import de.hdm.itprojektss19.team03.scart.shared.bo.BusinessObject;

/**
 * Realisierung einer exemplarischen <Code>GroceryListArticle</code>-Klasse,
 * welche dafuer zustaendig ist, <code>Article</code>- und
 * <code>GroceryList</code>-Objekte zusammen zu fuehren. Dadurch wird
 * jede neue Zuweisung eines Articles einer GropceryList durch ein 
 * <Code>GroceryListArticle</code>-Objekt erstellt.
 * 
 * @see <code>BusinessObject</code>
 * @see <code>GroceryList</code>
 * @see <code>Article</code>
 *
 */
public class GroceryListArticle extends BusinessObject {
	
		private static final long serialVersionUID = 1L;

		/**
		 * Instanzvariablen 
		 */
		private int groceryListId = 0; 
		private int articleId = 0;
		
		
		/**
		 * Auslesen der groceryListId
		 * @return groceryListId -Eindeutige Id der GroceryList
		 */
		public int getGroceryListId() {
			return groceryListId;
		}

		/**
		 * Setzten der groceryListId
		 * @param groceryListId - Eindeutige Id der GroceryList
		 */
		public void setGroceryListId(int groceryListId) {
			this.groceryListId = groceryListId;
		}

		/**
		 * Auslesen der articleId
		 * @return articleId - Eindeutige Id unseres Articles
		 */
		public int getArticleId() {
			return articleId;
		}
		
		/**
		 * Setzten der articleId
		 * @param articleId - Eindeutige Id unseres Articles
		 */
		public void setArticleId(int articleId) {
			this.articleId = articleId;
		}
}
