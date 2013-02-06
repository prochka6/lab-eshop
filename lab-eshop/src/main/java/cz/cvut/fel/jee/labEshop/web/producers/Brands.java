package cz.cvut.fel.jee.labEshop.web.producers;

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

import cz.cvut.fel.jee.labEshop.model.Brand;
import cz.cvut.fel.jee.labEshop.model.Brand_;

/**
 * Brands list producer implementation using producer method to retrieve brands
 * list ordered by name using hibernate criteria query for pertaining type
 * safety and store in current user {@linkplain HttpServletRequest}.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Stateless
public class Brands {

	@Inject
	private EntityManager em;

	@Named("brandsList")
	@Produces
	@RequestScoped
	public List<Brand> listCategories() {
		CriteriaQuery<Brand> query = em.getCriteriaBuilder().createQuery(Brand.class);
		Root<Brand> root = query.from(Brand.class);
		query.orderBy(em.getCriteriaBuilder().asc(root.get(Brand_.name)));

		return em.createQuery(query).getResultList();
	}

}
