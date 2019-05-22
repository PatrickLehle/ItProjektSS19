package de.hdm.itprojektss19.team03.scart.server;

import java.util.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl;
import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.ReportGenerator;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;

/**
 * 
 * @author bastiantilk
 *
 */
public class ReportGeneratorImpl extends RemoteServiceServlet implements
ReportGenerator{
	
	private static final long serialVersionUID = 1L;
	private EditorService editorService = null;
	
	/**
	 * *************************************************************************
	 * INITIALISIERUNG
	 * *************************************************************************
	 */

	public ReportGeneratorImpl() throws IllegalArgumentException{
		
		EditorServiceImpl impl = new EditorServiceImpl();
		impl.init();
		this.editorService = impl;
	}
	protected EditorService getEditorService() {
		return this.editorService;
	}
	
	public void init() {
		
	}
	
	public ArrayList<Article> getStatistic(int UID) {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	}
	
	public ArrayList<Article> getStatistic(int UID, Date start, Date end) {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	} 
	
	public ArrayList<Article> getStatistic(int UID, Retailer retailer) {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	}
	
	public ArrayList<Article> getStatistic(int UID, Date start, Date end, Retailer retailer) {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	} 
}
