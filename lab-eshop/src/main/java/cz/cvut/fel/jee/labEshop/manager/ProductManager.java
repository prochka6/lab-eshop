package cz.cvut.fel.jee.labEshop.manager;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import cz.cvut.fel.jee.labEshop.dao.IProductDao;
import cz.cvut.fel.jee.labEshop.exceptions.EntityNotFoundException;
import cz.cvut.fel.jee.labEshop.model.Brand;
import cz.cvut.fel.jee.labEshop.model.Product;
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
	 * Find {@linkplain Product} by given id. If no such brand exists throws
	 * EntityNotFoundException.
	 * 
	 * @param id
	 *            the id of product
	 * @return product entity
	 * @throws EntityNotFoundException
	 *             if no such brand with given id exists
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
	 *             if brand parameter is null or doesn't have assigned
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
	 * Return list of products stored in database.
	 * 
	 * @return list of all products never <code>null</code>
	 */
	public List<Product> findAllProducts() {
		return productDao.getAll();
	}
}
