package cz.cvut.fel.jee.labEshop.filter;

import java.io.Serializable;

/**
 * Base entity filter used by clients to filter entities.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class EntityFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer firstRow;
	private Integer maxRows;
	private SortModel sortModel;

	public Integer getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(Integer firstRow) {
		this.firstRow = firstRow;
	}

	public Integer getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(Integer maxRows) {
		this.maxRows = maxRows;
	}

	public SortModel getSortModel() {
		return sortModel;
	}

	public void setSortModel(SortModel sortModel) {
		this.sortModel = sortModel;
	}

	/**
	 * Name of the filter.
	 * 
	 * @return filter name
	 */
	public String getName() {
		return getClass().getSimpleName();
	}

}
