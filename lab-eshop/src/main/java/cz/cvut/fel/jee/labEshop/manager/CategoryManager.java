package cz.cvut.fel.jee.labEshop.manager;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.SecurityDomain;

import cz.cvut.fel.jee.labEshop.dao.ICategoryDao;
import cz.cvut.fel.jee.labEshop.exceptions.EntityNotFoundException;
import cz.cvut.fel.jee.labEshop.model.Brand;
import cz.cvut.fel.jee.labEshop.model.Category;
import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;

/**
 * Service fasade for working with {@linkplain Category}.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Stateless
@SecurityDomain("labeshopsecurity")
@DeclareRoles({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
public class CategoryManager {

	@Inject
	private ICategoryDao categoryDao;

	/**
	 * Find {@linkplain Category} by given id. If no such category exists throws
	 * EntityNotFoundException.
	 * 
	 * @param id
	 *            the id of category
	 * @return category entity
	 * @throws EntityNotFoundException
	 *             if no such category with given id exists
	 */
	  @PermitAll
	public Category findCategory(Long id) throws EntityNotFoundException {
		if (id == null) {
			throw new EntityNotFoundException(Brand.class, id);
		}

		Category category = categoryDao.get(id);

		if (category == null) {
			throw new EntityNotFoundException(Category.class, id);
		}

		return category;
	}

	/**
	 * Find {@linkplain Category} by name. If no such entity exists returns
	 * <code>null</code>.
	 * 
	 * @param name
	 *            the name of the category
	 * @return the category entity with given name or <code>null</code>
	 */
	  @PermitAll
	public Category findCategoryByName(String name) {
		if (name == null || "".equals(name.trim())) {
			return null;
		}

		return categoryDao.getByName(name);
	}

	/**
	 * Create new {@linkplain Category} entity in database.
	 * 
	 * @param category
	 *            the category
	 * @throws IllegalArgumentException
	 *             if category parameter is null
	 */
	@RolesAllowed({ LabEshopConstants.ADMINISTRATOR_ROLE })
	public void createCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException();
		}

		categoryDao.saveOrUpdate(category);
	}

	/**
	 * Update given {@linkplain Category} in database.
	 * 
	 * @param category
	 *            the category to be updated
	 * @return merged database entity
	 * @throws IllegalArgumentException
	 *             if category parameter is null or doesn't have assigned
	 *             identifier.
	 */
	@RolesAllowed({ LabEshopConstants.ADMINISTRATOR_ROLE })
	public Category updateCategory(Category category) {
		if (category == null || category.getId() == null) {
			throw new IllegalArgumentException();
		}

		return categoryDao.saveOrUpdate(category);
	}

	/**
	 * Return list of categories stored in database.
	 * 
	 * @return list of all categories never <code>null</code>
	 */
	@PermitAll
	public List<Category> findAllCategories() {
		return categoryDao.getAll();
	}

}
