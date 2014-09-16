package org.pikater.web.vaadin.gui.client.kineticengine;

import org.pikater.web.vaadin.gui.shared.kineticcomponent.ClickMode;

import com.google.gwt.user.client.Element;

public interface IKineticEngineContext
{
	Element getStageDOMElement();
	KineticEngine getEngine();
	GraphItemCreator getGraphItemCreator();
	KineticUndoRedoManager getHistoryManager();
	
	ClickMode getClickMode();
}