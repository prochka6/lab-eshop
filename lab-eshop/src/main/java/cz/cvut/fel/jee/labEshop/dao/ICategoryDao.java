package cz.cvut.fel.jee.labEshop.dao;

import cz.cvut.fel.jee.labEshop.model.Category;

/**
 * Category related Dao functionality.
 * 
 * @see Category
 * @see IBaseDao
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public interface ICategoryDao extends IBaseDao<Category, Long> {

	/**
	 * Looks up Category entity by specified name. If no such exists return
	 * null.
	 * 
	 * @param categoryName
	 *            the category name we searches for
	 * @return category instance with given name or <code>null</code> if no such
	 *         exists.
	 * @throws IllegalArgumentException
	 *             if categoryName is null or empty.
	 */
	Category getByName(String categoryName) throws IllegalArgumentException;

}
