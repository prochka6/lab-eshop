package cz.cvut.fel.jee.labEshop.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import cz.cvut.fel.jee.labEshop.dao.IProductDao;
import cz.cvut.fel.jee.labEshop.filter.EntityFilter;
import cz.cvut.fel.jee.labEshop.model.Product;

/**
 * Jpa implementation of {@linkplain IProductDao}.
 * 
 * @see IProductDao
 * @see JpaBaseDao
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class JpaProductDao extends JpaBaseDao<Product> implements IProductDao {

	@Override
	public List<Product> findLatestsProducts(EntityFilter filter) {
		TypedQuery<Product> query = em.createNamedQuery("Product.getLatests", Product.class).setParameter("date",
				new Date());
		return applyPaging(query, filter).getResultList();
	}

}
