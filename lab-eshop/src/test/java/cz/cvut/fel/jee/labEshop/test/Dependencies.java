package cz.cvut.fel.jee.labEshop.test;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

/**
 * Definition of global needed maven dependencies.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public interface Dependencies {

	public static final Archive<?>[] PERSISTENCE = DependencyResolvers.use(MavenDependencyResolver.class)
			.loadMetadataFromPom("pom.xml").artifact("org.jboss.seam.persistence:seam-persistence")
			.resolveAs(GenericArchive.class).toArray(new Archive<?>[0]);

}
