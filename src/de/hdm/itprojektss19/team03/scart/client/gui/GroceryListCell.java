package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Set;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.cell.client.Cell.Context;

import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;

public class GroceryListCell extends AbstractCell<GroceryList> {

	@Override
	public boolean dependsOnSelection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> getConsumedEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean handlesSelection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEditing(Context arg0, Element arg1, GroceryList arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onBrowserEvent(Context arg0, Element arg1, GroceryList arg2, NativeEvent arg3,
			ValueUpdater<GroceryList> arg4) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Context context, GroceryList gl, SafeHtmlBuilder sb) {
		if (gl == null) {
			return;
		}
		sb.appendHtmlConstant("<div>");
		sb.append(gl.getGroupId());
		sb.appendHtmlConstant(" ");
		sb.appendHtmlConstant(gl.getGroceryListName());
		/**
		 * Wenn Liste gefiltert wird nach Retailer. 
		 * sb.appendHtmlConstant(">");
		 * sb.appendHtmlConstant(gl.getArticles().iterator().next().getRetailer().toString());
		 * 
		 */
		sb.appendHtmlConstant("<table>");
		while (gl.getArticles().iterator().hasNext()) {
			sb.appendHtmlConstant("<tr>");
			sb.appendHtmlConstant("<td>");
			sb.appendHtmlConstant(gl.getArticles().iterator().next().getName());
			sb.appendHtmlConstant("</td>");
			sb.appendHtmlConstant("<td>");
			sb.append(gl.getArticles().iterator().next().getQuantity());
			sb.appendHtmlConstant("</td>");
			sb.appendHtmlConstant("<td>");
			sb.appendHtmlConstant(gl.getArticles().iterator().next().getUnit());
			sb.appendHtmlConstant("</td>");
			sb.appendHtmlConstant("</tr>");
			sb.appendHtmlConstant("</div>");
		}
	}

	@Override
	public boolean resetFocus(Context arg0, Element arg1, GroceryList arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValue(Context arg0, Element arg1, GroceryList arg2) {
		// TODO Auto-generated method stub

	}

}
