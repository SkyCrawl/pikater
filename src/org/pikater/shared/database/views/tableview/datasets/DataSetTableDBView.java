package org.pikater.shared.database.views.tableview.datasets;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.pikater.shared.database.jpa.JPADataSetLO;
import org.pikater.shared.database.jpa.JPAUser;
import org.pikater.shared.database.jpa.daos.DAOs;
import org.pikater.shared.database.jpa.daos.DataSetDAO;
import org.pikater.shared.database.views.base.ITableColumn;
import org.pikater.shared.database.views.base.query.QueryConstraints;
import org.pikater.shared.database.views.base.query.QueryResult;
import org.pikater.shared.database.views.base.values.DBViewValueType;
import org.pikater.shared.database.views.tableview.AbstractTableDBView;

/**
 * A generic view for tables displaying dataset information.  
 */
public class DataSetTableDBView extends AbstractTableDBView {
	private JPAUser owner;

	/**
	 * Creates an admin mode view. All datasets of all users will be viewed.
	 */
	public DataSetTableDBView() {
		this.owner = null;
	}

	/**
	 * Creates a user mode view. 
	 * @param owner the user whose datasets to display
	 */
	public DataSetTableDBView(JPAUser owner) {
		this.owner = owner;
	}

	protected boolean adminMode() {
		return this.owner == null;
	}

	/**
	 * Table headers will be presented in the order defined here, so
	 * make sure to order them right :). 
	 * <p>
	 * This enum is used for create Criteria API query in functions
	 * {@link DataSetDAO#getAllUserUpload(int, int, ITableColumn, org.pikater.shared.database.views.base.SortOrder)} and 
	 * {@link DataSetDAO#getByOwner(JPAUser, int, int, ITableColumn, org.pikater.shared.database.views.base.SortOrder)}
	 * <p>
	 * If you want to change column names you can redefine function {@link Column#getDisplayName()}
	 */
	public enum Column implements ITableColumn {
		/*
		 * First the read-only properties.
		 */
		OWNER, // owner is expected to be declared first in the {@link #getColumns()} method
		CREATED, SIZE, DEFAULT_TASK_TYPE, NUMBER_OF_INSTANCES, FILENAME, DESCRIPTION, APPROVED, VISUALIZE, COMPARE, DOWNLOAD, DELETE;

		@Override
		public String getDisplayName() {
			switch (this) {
			case NUMBER_OF_INSTANCES:
				return "INSTANCES";
			case DEFAULT_TASK_TYPE:
				return "TASK_TYPE";
			default:
				return this.name();
			}
		}

		@Override
		public DBViewValueType getColumnType() {
			switch (this) {
			case OWNER:
			case CREATED:
			case NUMBER_OF_INSTANCES:
			case DEFAULT_TASK_TYPE:
			case SIZE:
			case FILENAME:
			case DESCRIPTION:
				return DBViewValueType.STRING;

			case APPROVED:
				return DBViewValueType.BOOLEAN;

			case VISUALIZE:
			case COMPARE:
			case DOWNLOAD:
			case DELETE:
				return DBViewValueType.NAMED_ACTION;

			default:
				throw new IllegalStateException("Unknown state: " + name());
			}
		}

		public static Set<Column> getColumns(boolean adminMode) {
			if (adminMode) {
				return EnumSet.allOf(Column.class);
			} else {
				return EnumSet.complementOf(EnumSet.of(Column.OWNER, Column.APPROVED));
			}
		}
	}

	@Override
	public Set<ITableColumn> getAllColumns() {
		return new LinkedHashSet<ITableColumn>(Column.getColumns(adminMode()));
	}

	@Override
	public Set<ITableColumn> getDefaultColumns() {
		Set<ITableColumn> result = getAllColumns();
		result.remove(Column.NUMBER_OF_INSTANCES);
		result.remove(Column.DEFAULT_TASK_TYPE);
		if (adminMode()) {
			result.remove(Column.VISUALIZE);
			result.remove(Column.COMPARE);
		} else {
			result.remove(Column.OWNER);
			result.remove(Column.APPROVED);
			result.remove(Column.DOWNLOAD);
		}
		return result;
	}

	@Override
	public ITableColumn getDefaultSortOrder() {
		return adminMode() ? Column.OWNER : Column.CREATED;
	}

	@Override
	public QueryResult queryUninitializedRows(QueryConstraints constraints) {
		List<JPADataSetLO> allDatasets;
		int allDatasetCount = 0;
		if (this.adminMode()) {
			allDatasets = DAOs.dataSetDAO.getUserUploadVisible(constraints.getOffset(), constraints.getMaxResults(), constraints.getSortColumn(), constraints.getSortOrder());
			allDatasetCount = DAOs.dataSetDAO.getUserUploadVisibleCount();
		} else {
			allDatasets = DAOs.dataSetDAO.getByOwnerUserUploadVisible(owner, constraints.getOffset(), constraints.getMaxResults(), constraints.getSortColumn(), constraints.getSortOrder());
			allDatasetCount = DAOs.dataSetDAO.getByOwnerUserUploadVisibleCount(owner);
		}

		List<DataSetTableDBRow> resultRows = new ArrayList<DataSetTableDBRow>();
		for (JPADataSetLO dslo : allDatasets) {
			resultRows.add(new DataSetTableDBRow(dslo));
		}
		return new QueryResult(resultRows, allDatasetCount);
	}
}