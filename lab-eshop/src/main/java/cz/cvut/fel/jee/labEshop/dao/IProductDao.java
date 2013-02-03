package cz.cvut.fel.jee.labEshop.dao;

import java.util.List;

import cz.cvut.fel.jee.labEshop.filter.EntityFilter;
import cz.cvut.fel.jee.labEshop.model.Product;

/**
 * Product related Dao functionality.
 * 
 * @see Product
 * @see IBaseDao
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public interface IProductDao extends IBaseDao<Product, Long> {

	/**
	 * Finds latest published products sorted by publish date descending.
	 * 
	 * @param filter
	 *            the pagination filter
	 * @return list of latest published Products
	 */
	List<Product> findLatestsProducts(EntityFilter filter);

}
