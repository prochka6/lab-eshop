package cz.cvut.fel.jee.labEshop.util;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.seam.persistence.SeamManagedPersistenceContextCreated;
import org.jboss.solder.core.ExtensionManaged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDI style resource producer class.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class Resources {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unused")
	@Produces
	@ExtensionManaged
	@ConversationScoped
	@PersistenceUnit(unitName = "lab-eshop-PU")
	private EntityManagerFactory entityManagerFactory;

	@Produces
	public Logger produceLog(InjectionPoint ip) {
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
	}

	public void initEntityManager(@Observes SeamManagedPersistenceContextCreated event) {
		log.debug("Entity Manager {} opened.", event.getEntityManager());
	}

}
