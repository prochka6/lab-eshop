package cz.cvut.fel.jee.labEshop.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This base manager provide basic operation with collection.
 * 
 * @author Tom
 * 
 * @param <T>
 *            entity which will be manager work
 */
public class BaseManager<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * This method add item to collection, if collection is null then new
	 * collection is create
	 * 
	 * @param collection
	 *            to which will be added item
	 * @param itemToAdd
	 *            item to add
	 * @return new collection with added item
	 */
	public List<T> addItemToCollection(List<T> collection, T itemToAdd) {
		if (collection == null) {
			collection = new ArrayList<T>();
		}
		collection.add(itemToAdd);
		return collection;

	}

	/**
	 * This method remove item from collection, if collection is null then empty
	 * collection is returned.
	 * 
	 * @param collection
	 *            from which will be removed item
	 * @param itemToRemove
	 *            item to remove from collection
	 * @return new collection without itemToRemove
	 */
	public List<T> removeFromCollection(List<T> collection, T itemToRemove) {
		if (collection == null) {
			collection = new ArrayList<T>();
		}
		if (collection.contains(itemToRemove)) {
			collection.remove(itemToRemove);
		}
		return collection;

	}

}
