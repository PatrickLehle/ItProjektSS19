package de.hdm.itprojektss19.team03.scart.server;

import java.util.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.shared.ReportGenerator;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;


public class ReportGeneratorImpl extends RemoteServiceServlet implements
ReportGenerator{

	public ReportGeneratorImpl() {
		
	}
	
	public void init() {
		
	}
	
	public ArrayList<Article> getStatistic(int UID) {
		
	}
	
	public ArrayList<Article> getStatistic(int UID, Date start, Date end) {
		
	} 
	
	public ArrayList<Article> getStatistic(int UID, Retailer retailer) {
		
	}
	
	public ArrayList<Article> getStatistic(int UID, Date start, Date end, Retailer retailer) {
		
	} 
}
