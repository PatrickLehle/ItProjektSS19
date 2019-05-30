package de.hdm.itprojektss19.team03.scart.client.gui;

import de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl;
import de.hdm.itprojektss19.team03.scart.server.db.ArticleMapper;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;

public class Test {
	
	public static void main (String[]args) {
	
	 
	 Article a = ArticleMapper.articleMapper().findByKey(1);
	 
		
		System.out.println(a.getName());
	
	}
	
	
	
	
	

}
