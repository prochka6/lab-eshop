package cz.cvut.fel.jee.labEshop.test;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class ResourceProducers {

	@SuppressWarnings("unused")
	@Produces
	@PersistenceContext
	private EntityManager em;

}
