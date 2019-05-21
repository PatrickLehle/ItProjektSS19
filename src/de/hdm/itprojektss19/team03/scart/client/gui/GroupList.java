package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public class GroupList extends VerticalPanel {

	HorizontalPanel hp = new HorizontalPanel();
	Button newGroupBtn = new Button(
			"<image src='/images/plusButton.png' width='16px' height='16px' align='center'/>");

	public GroupList(final User user) {

		ScrollPanel sc = new ScrollPanel();
		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);

		newGroupBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(new GroupForm(user));
			}
		});
		
		newGroupBtn.setPixelSize(100, 60);
		newGroupBtn.setStyleName("button1");
		newGroupBtn.setTitle("create new Group");
		
		hp.add(newGroupBtn);
		this.add(hp);
		this.add(sc);
	}
}