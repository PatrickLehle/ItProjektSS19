package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Set;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojektss19.team03.scart.shared.bo.Group;

public class GroupCell extends AbstractCell<Group> {

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
	public boolean isEditing(Context arg0, Element arg1, Group arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onBrowserEvent(Context arg0, Element arg1, Group arg2, NativeEvent arg3, ValueUpdater<Group> arg4) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Context arg0, Group arg1, SafeHtmlBuilder arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean resetFocus(Context arg0, Element arg1, Group arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValue(Context arg0, Element arg1, Group arg2) {
		// TODO Auto-generated method stub

	}

}
