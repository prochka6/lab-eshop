package cz.cvut.fel.jee.labEshop.web;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import cz.cvut.fel.jee.labEshop.model.Category;
import cz.cvut.fel.jee.labEshop.model.Category_;

/**
 * Sample implementation using producer method to retrieve categories list
 * ordered by name using hibernate criteria query for pertaining type safety and
 * store in current user {@linkplain HttpServletRequest}.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Named
@Stateless
public class Categories {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Named("categoriesList")
	@Produces
	@RequestScoped
	public List<Category> listCategories() {
		CriteriaQuery<Category> query = em.getCriteriaBuilder().createQuery(Category.class);
		Root<Category> root = query.from(Category.class);
		query.orderBy(em.getCriteriaBuilder().asc(root.get(Category_.name)));

		log.debug("Retrieving list of all categories.");

		return em.createQuery(query).getResultList();
	}

}
