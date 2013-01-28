package cz.cvut.fel.jee.labEshop.manager;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import cz.cvut.fel.jee.labEshop.dao.IBrandDao;
import cz.cvut.fel.jee.labEshop.exceptions.EntityNotFoundException;
import cz.cvut.fel.jee.labEshop.model.Brand;
import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;

/**
 * Service fasade for working with {@linkplain Brand}.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Stateless
public class BrandManager {

	@Inject
	private IBrandDao brandDao;

	/**
	 * Find {@linkplain Brand} by given id. If no such brand exists throws
	 * EntityNotFoundException.
	 * 
	 * @param id
	 *            the id of brand
	 * @return brand entity
	 * @throws EntityNotFoundException
	 *             if no such brand with given id exists
	 */
	public Brand findBrand(Long id) throws EntityNotFoundException {
		if (id == null) {
			throw new EntityNotFoundException(Brand.class, id);
		}

		Brand brand = brandDao.get(id);

		if (brand == null) {
			throw new EntityNotFoundException(Brand.class, id);
		}

		return brand;
	}

	/**
	 * Create new {@linkplain Brand} entity in database.
	 * 
	 * @param brand
	 *            the brand
	 * @throws IllegalArgumentException
	 *             if brand parameter is null
	 */
	@RolesAllowed({ LabEshopConstants.ADMINISTRATOR_ROLE })
	public void createBrand(Brand brand) throws IllegalArgumentException {
		if (brand == null) {
			throw new IllegalArgumentException();
		}

		brandDao.saveOrUpdate(brand);
	}

	/**
	 * Update given {@linkplain Brand} in database.
	 * 
	 * @param brand
	 *            the brand to be updated
	 * @return merged database entity
	 * @throws IllegalArgumentException
	 *             if brand parameter is null or doesn't have assigned
	 *             identifier.
	 */
	@RolesAllowed({ LabEshopConstants.ADMINISTRATOR_ROLE })
	public Brand updateBrand(Brand brand) throws IllegalArgumentException {
		if (brand == null || brand.getId() == null) {
			throw new IllegalArgumentException();
		}

		return brandDao.saveOrUpdate(brand);
	}

	/**
	 * Return list of brands stored in database.
	 * 
	 * @return list of all brands never <code>null</code>
	 */
	public List<Brand> findAllBrands() {
		return brandDao.getAll();
	}

}
