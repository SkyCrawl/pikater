package org.pikater.web.vaadin.gui.server.components.dbviews.base.tableview;

import org.pikater.shared.database.views.base.query.QueryConstraints;

public interface IDBTableContainerContext
{
	QueryConstraints getQuery();
}