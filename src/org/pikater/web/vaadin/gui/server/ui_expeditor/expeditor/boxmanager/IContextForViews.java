package org.pikater.web.vaadin.gui.server.ui_expeditor.expeditor.boxmanager;

import org.pikater.web.experiment.server.BoxInfoServer;
import org.pikater.web.vaadin.gui.server.ui_expeditor.expeditor.boxmanager.views.AbstractBoxManagerView;

/**
 * Context class providing all required information
 * for {@link BoxManagerToolbox box manager toolbox's}
 * views and subviews.
 * 
 * @author SkyCrawl
 */
public interface IContextForViews extends IBoxManagerToolboxContext
{
	/**
	 * Gets the currently viewed view/subview.
	 * @return
	 */
	AbstractBoxManagerView<?> getCurrentView();
	
	/**
	 * Sets the currently viewed view/subview to
	 * the given one.
	 * @param view
	 */
	void setView(AbstractBoxManagerView<?> view);
	
	/**
	 * Gets a specific registered view.
	 * @param view
	 * @return
	 */
	AbstractBoxManagerView<?> getView(BoxManagerView view);
	
	/**
	 * Sets the currently viewed view/subview to overview.
	 * @see {@link BoxManagerView}
	 */
	void resetView();
	
	/**
	 * Gets the currently selected/viewed/processed experiment
	 * box.
	 * @return
	 */
	BoxInfoServer getCurrentBoxDataSource();
}