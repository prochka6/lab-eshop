package cz.cvut.fel.jee.labEshop.web;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;

import cz.cvut.fel.jee.labEshop.model.Product;

/**
 * Sample implementation for showing Logger, EntityManager injection. Using
 * caching cause of JSF lifecycle calls getXXX methods multiple times during
 * single request processing.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Model
public class Sample {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	private List<Product> products;

	public List<Product> products() {
		if (products == null) {
			log.debug("Retrieving all products from database.");
			CriteriaQuery<Product> query = em.getCriteriaBuilder().createQuery(Product.class);
			query.from(Product.class);
			products = em.createQuery(query).getResultList();
		}

		return products;
	}

}
