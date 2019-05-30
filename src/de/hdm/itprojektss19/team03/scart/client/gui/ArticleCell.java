package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojektss19.team03.scart.shared.bo.Article;

public class ArticleCell extends AbstractCell<Article> {

	@Override
	public void render(Context context, Article a, SafeHtmlBuilder sb) {
		// Value can be null, so do a null check..
		if (a == null) {
			return;
		}

		sb.appendHtmlConstant("<div>");
		sb.appendHtmlConstant(a.getName());
		sb.appendHtmlConstant(" ");
		sb.append(a.getQuantity());
		sb.appendHtmlConstant(" ");
		sb.appendHtmlConstant(a.getRetailerName().toString());
		sb.appendHtmlConstant(" ");
		sb.appendHtmlConstant(a.getUnit().toString());
		sb.appendHtmlConstant("</div>");
	}
}