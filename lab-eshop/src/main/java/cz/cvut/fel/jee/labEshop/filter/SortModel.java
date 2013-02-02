package cz.cvut.fel.jee.labEshop.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Sorting model for SQL select statements.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class SortModel implements Serializable, Iterable<SortModel.Property> {

	private static final long serialVersionUID = 1L;

	private List<Property> properties = new ArrayList<SortModel.Property>(2);

	public SortModel() {
	}

	public SortModel(String key) {
		addProperty(key);
	}

	public SortModel(String key, SortOrder sortOrder) {
		addProperty(key, sortOrder);
	}

	public void addProperty(String key) {
		properties.add(new Property(key));
	}

	public void addProperty(String key, SortOrder sortOrder) {
		properties.add(new Property(key, sortOrder));
	}

	public List<Property> getProperties() {
		return properties;
	}

	/**
	 * Represents single property we want to sort by.
	 * 
	 * @author Kamil Prochazka (<a
	 *         href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
	 */
	public static class Property implements Serializable {

		private static final long serialVersionUID = 1L;

		private String key;
		private SortOrder sortOrder = SortOrder.ASCENDING;

		Property(String key) {
			this.key = key;
		}

		Property(String key, SortOrder sortOrder) {
			this.key = key;
			this.sortOrder = sortOrder;
		}

		public String getKey() {
			return key;
		}

		public SortOrder getSortOrder() {
			return sortOrder;
		}

	}

	@Override
	public Iterator<Property> iterator() {
		return properties.iterator();
	}

}
