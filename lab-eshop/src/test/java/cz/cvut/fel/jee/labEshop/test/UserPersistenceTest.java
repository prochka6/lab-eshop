package cz.cvut.fel.jee.labEshop.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Arquillian test class. Not Implemented yet.
 *
 * @author buben
 *
 */
@RunWith(Arquillian.class)
public class UserPersistenceTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}

	@Test
	public void userPersistenceTest() {
		//TODO: Implement persistenceTest. Now I have some errors after adding classes to ShrinkWrap
		// NoClassDefFound... I haven't figured out how to solve this.
		
		Assert.fail("Not yet implemented");
	}

}