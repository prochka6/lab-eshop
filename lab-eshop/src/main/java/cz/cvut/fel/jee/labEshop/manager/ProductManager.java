package cz.cvut.fel.jee.labEshop.manager;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import cz.cvut.fel.jee.labEshop.dao.IProductDao;
import cz.cvut.fel.jee.labEshop.exceptions.EntityNotFoundException;
import cz.cvut.fel.jee.labEshop.filter.EntityFilter;
import cz.cvut.fel.jee.labEshop.filter.ProductSearchFilter;
import cz.cvut.fel.jee.labEshop.model.Brand;
import cz.cvut.fel.jee.labEshop.model.Product;
import cz.cvut.fel.jee.labEshop.util.Assert;
import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;

/**
 * Service fasade for working with {@linkplain Product}.
 * 
 * @author Ondřej Harcuba (<a href="mailto:harcuond@fel.cvut.cz">harcuond</a>)
 */
@Stateless
public class ProductManager {

	@Inject
	private IProductDao productDao;

	/**
	 * Find {@linkplain Product} by given id. If no such product exists throws
	 * EntityNotFoundException.
	 * 
	 * @param id
	 *            the id of product
	 * @return product entity
	 * @throws EntityNotFoundException
	 *             if no such product with given id exists
	 */
	public Product findProduct(Long id) throws EntityNotFoundException {
		if (id == null) {
			throw new EntityNotFoundException(Brand.class, id);
		}

		Product product = productDao.get(id);

		if (product == null) {
			throw new EntityNotFoundException(Brand.class, id);
		}

		return product;
	}

	/**
	 * Create new {@linkplain Product} entity in database.
	 * 
	 * @param product
	 *            the product
	 * @throws IllegalArgumentException
	 *             if product parameter is null
	 */
	@RolesAllowed({ LabEshopConstants.ADMINISTRATOR_ROLE })
	public void createProduct(Product product) {
		if (product == null) {
			throw new IllegalArgumentException();
		}

		productDao.saveOrUpdate(product);
	}

	/**
	 * Update given {@linkplain Product} in database.
	 * 
	 * @param product
	 *            the product to be updated
	 * @return merged database entity
	 * @throws IllegalArgumentException
	 *             if product parameter is null or doesn't have assigned
	 *             identifier.
	 */
	@RolesAllowed({ LabEshopConstants.ADMINISTRATOR_ROLE })
	public Product updateProduct(Product product) throws IllegalArgumentException {
		if (product == null || product.getId() == null) {
			throw new IllegalArgumentException();
		}

		return productDao.saveOrUpdate(product);
	}

	/**
	 * Finds latest published products sorted by publish date descending. If
	 * pagination filter is null, assign default row size to 10.
	 * 
	 * @param filter
	 *            the pagination filter
	 * @return list of latest published products
	 */
	public List<Product> findLatestsProducts(EntityFilter filter) {
		if (filter == null) {
			filter = new EntityFilter();
			filter.setMaxRows(10);
		}

		return productDao.findLatestsProducts(filter);
	}

	/**
	 * Filters products by given filter.
	 * 
	 * @param filter
	 *            the product search filter
	 * @return filtered Product entities
	 * @throws IllegalArgumentException
	 *             if filter is null.
	 */
	public List<Product> findByFilter(ProductSearchFilter filter) {
		Assert.notNull(filter);

		return productDao.find(filter);
	}

	/**
	 * Return list of products stored in database.
	 * 
	 * @return list of all products never <code>null</code>
	 */
	public List<Product> findAllProducts() {
		return productDao.getAll();
	}
}
