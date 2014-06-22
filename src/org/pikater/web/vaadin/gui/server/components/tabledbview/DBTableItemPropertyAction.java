package org.pikater.web.vaadin.gui.server.components.tabledbview;

import org.pikater.shared.database.views.jirka.abstractview.AbstractTableRowDBView;
import org.pikater.shared.database.views.jirka.abstractview.IColumn;
import org.pikater.shared.database.views.jirka.abstractview.values.ActionDBViewValue;

import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class DBTableItemPropertyAction implements Property<Button>
{
	private static final long serialVersionUID = -4326743856484935440L;
	
	private final Button btn;
	
	public DBTableItemPropertyAction(final DBTableContainer container, final IColumn column, final AbstractTableRowDBView row, final ActionDBViewValue valueWrapper)
	{
		this.btn = new Button(valueWrapper.getValue(), new Button.ClickListener()
		{
			private static final long serialVersionUID = 1829748841851811252L;

			@Override
			public void buttonClick(ClickEvent event)
			{
				container.getParentTable().getViewRoot().dbViewActionCalled(column, row, valueWrapper);
			}
		});
	}

	@Override
	public Button getValue()
	{
		return btn;
	}

	@Override
	public void setValue(Button newValue) throws com.vaadin.data.Property.ReadOnlyException
	{
	}

	@Override
	public Class<? extends Button> getType()
	{
		return Button.class;
	}

	@Override
	public boolean isReadOnly()
	{
		return true;
	}

	@Override
	public void setReadOnly(boolean newStatus)
	{
		throw new UnsupportedOperationException();
	}
}