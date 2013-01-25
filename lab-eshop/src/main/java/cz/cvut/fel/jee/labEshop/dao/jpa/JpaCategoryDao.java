package cz.cvut.fel.jee.labEshop.dao.jpa;

import java.util.List;

import cz.cvut.fel.jee.labEshop.dao.ICategoryDao;
import cz.cvut.fel.jee.labEshop.model.Category;
import cz.cvut.fel.jee.labEshop.util.Assert;

/**
 * Jpa implementation of {@linkplain ICategoryDao}.
 * 
 * @see ICategoryDao
 * @see JpaBaseDao
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class JpaCategoryDao extends JpaBaseDao<Category> implements ICategoryDao {

	@Override
	public Category getByName(String categoryName) throws IllegalArgumentException {
		Assert.notEmpty(categoryName, "Category name may not be empty or null.");

		List<Category> result = em.createNamedQuery("Category.getByName", Category.class)
				.setParameter("name", categoryName).getResultList();
		if (result.isEmpty()) {
			return null;
		}

		// Expected max 1 value because of DB uniqueness.
		return result.get(0);
	}

}
