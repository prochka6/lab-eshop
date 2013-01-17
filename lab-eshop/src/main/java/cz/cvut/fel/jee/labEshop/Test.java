package cz.cvut.fel.jee.labEshop;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

import cz.cvut.fel.jee.labEshop.model.User;

/**
 * Example usage of injected {@linkplain EntityManager} and {@linkplain Logger}.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Singleton
@Startup
public class Test {

	@Inject
	private Logger log;

	@PersistenceContext
	private EntityManager entityManager;

	@PostConstruct
	public void startup() {
		List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();
		log.info("Found {} user/s", users.size());
		for (User user : users)
			log.info(user.toString());
	}

}
