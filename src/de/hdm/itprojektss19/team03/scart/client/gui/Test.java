package de.hdm.itprojektss19.team03.scart.client.gui;

import java.sql.Timestamp;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl;
import de.hdm.itprojektss19.team03.scart.server.db.ArticleMapper;
import de.hdm.itprojektss19.team03.scart.server.db.RetailerMapper;
import de.hdm.itprojektss19.team03.scart.server.db.UserMapper;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public class Test {
	
	public static void main (String[]args) {
//	
//	User u = new User();
//	u.setId(1);
//	u.setEmail("test@hotmail.de");
//	u.setUsername("Franz");
//	UserMapper.userMapper().insert(u);
	
	//User u2 = new User();
//	u2.setId(2);
//	u2.setEmail("test2@hotmail.de");
//	u2.setUsername("Helga");
//	UserMapper.userMapper().insert(u2);
	
//	User u3 = new User();
//	u3.setId(3);
//	u3.setEmail("test3@hotmail.de");
//	u3.setUsername("Peter");
	
	//UserMapper.userMapper().insert(u);
//	u.setId(4);
//	UserMapper.userMapper().delete(u);
	

//		System.out.println(u);
		
//	Vector<User>us = UserMapper.userMapper().findAll();
//		System.out.println(us.elementAt(2).getEmail());
//	
//	u.setId(4);
//	u.setUsername("Hans");
//
//	UserMapper.userMapper().findbyUserId(4);
//	System.out.println(u.getUsername());
//	
//	Vector<Retailer> r1 = new Vector<Retailer>();
//	Retailer re1 = new Retailer();
//	Retailer re2 = new Retailer();
//	Retailer re3 = new Retailer();
//	Retailer re4 = new Retailer();
////	
//	re1.setId(1);
//	re2.setId(2);
//	re3.setId(3);
//	re4.setId(4);
////
//	re1.setRetailerName("Edeka");
//	re2.setRetailerName("Lidl");
//	re3.setRetailerName("Aldi");
//	re4.setRetailerName("Rewe");
//	
//	r1.add(re1);
//	r1.add(re2);
//	r1.add(re3);
//	r1.add(re4);
//	
//
//	Retailer r = new Retailer();
//	r.setId(1);
//	r.setRetailerName("Aldi");
//	RetailerMapper.retailerMapper().delete(re1);
//	RetailerMapper.retailerMapper().insert(re4);
//	
//	System.out.println(r);
//	Article a1 = new Article();
//	Article a2 = new Article();
//	Article a3 = new Article();
//	Article a4 = new Article();
//	
//	//id
//	a1.setId(1);
//	a2.setId(2);
//	a3.setId(3);
//	a4.setId(4);
//	
//	//name
//	a1.setName("Cheese");
//	a2.setName("Toast");
//	a3.setName("Mayo");
//	a4.setName("Seesalt");
//	
//	//quantity
//	a1.setQuantity(2);
//	a2.setQuantity(33);
//	a3.setQuantity(1);
//	a4.setQuantity(1);
//	
//	//unit
//	a1.setUnit("g");
//	a2.setUnit("g");
//	a3.setUnit("g");
//	a4.setUnit("g");
//	
//	//retailerId
//	a1.setRetailerId(1);
//	a2.setRetailerId(2);
//	a3.setRetailerId(3);
//	a4.setRetailerId(2);
//	
//	//creationDat
//	a1.getCreationDat();
//	a2.getCreationDat();
//	a3.getCreationDat();
//	a4.getCreationDat();
//	//modDat
//	a1.getModDat();
//	a2.getModDat();
//	a3.getModDat();
//	a4.getModDat();
//
//	ArticleMapper.articleMapper().delete(a1);
//	ArticleMapper.articleMapper().insert(a1);
//	ArticleMapper.articleMapper().insert(a2);
//	ArticleMapper.articleMapper().insert(a3);
	
	
System.out.println(ArticleMapper.articleMapper().findAllArticleByDate(Timestamp.valueOf("2019-06-01 20:38:01"), Timestamp.valueOf("2019-06-01 20:40:07")));
	
	
	}
	
	
	
	
	

}
