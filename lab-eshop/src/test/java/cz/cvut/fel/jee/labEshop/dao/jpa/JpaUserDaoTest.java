package cz.cvut.fel.jee.labEshop.dao.jpa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.cvut.fel.jee.labEshop.dao.IUserDao;
import cz.cvut.fel.jee.labEshop.dao.jpa.JpaUserDao;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.test.ResourceProducers;
import cz.cvut.fel.jee.labEshop.util.Assert;

/**
 * Test class for {@linkplain JpaUserDao}.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@RunWith(Arquillian.class)
public class JpaUserDaoTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "UserDaoTest.war").addPackages(true, IUserDao.class.getPackage())
				.addPackages(true, User.class.getPackage()).addClasses(ResourceProducers.class, Assert.class)
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addAsWebInfResource("test-ds.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction tx;

	@Before
	public void setUp() throws Exception {
		tx.begin();
		{
			User user = new User();
			user.setUsername("admin");
			user.setEmail("admin@admin");
			user.setPassword("password");
			em.persist(user);

			user = new User();
			user.setUsername("customer");
			user.setEmail("customer@customer");
			user.setPassword("password");
			em.persist(user);

			user = new User();
			user.setUsername("lojza");
			user.setEmail("lojza@customer");
			user.setPassword("password");
			em.persist(user);
		}
		tx.commit();
		em.clear();
	}

	@After
	public void tearDown() throws Exception {
		tx.begin();
		{
			em.createNativeQuery("delete from user;").executeUpdate();
		}
		tx.commit();
	}

	@Inject
	private IUserDao userDao;

	@Test
	public void test() {
		List<User> users = userDao.getAll();
		assertTrue(users.size() == 3);
	}

	@Test
	public void testFindByUsername_ExistingUser() {
		User user = userDao.getByUsername("lojza");
		assertNotNull(user);
		assertTrue("lojza".equals(user.getUsername()));
	}

	@Test
	public void testFindByUsername_NotExistingUser() {
		User user = userDao.getByUsername("xxx");
		assertNull(user);
	}

	@Test
	public void testFindByEmail_ExistingUser() {
		User user = userDao.getByEmail("customer@customer");
		assertNotNull(user);
		assertTrue("customer@customer".equals(user.getEmail()));
	}

	@Test
	public void testFindByEmail_NotExistingUser() {
		User user = userDao.getByEmail("xxx");
		assertNull(user);
	}

	@Test
	public void testRemoveUser() throws Exception {
		tx.begin();
		{
			User user = userDao.getByUsername("customer");
			userDao.delete(user);
			userDao.flushAndClear();

			user = userDao.getByUsername("customer");
			assertNull(user);
		}
		tx.commit();
	}
}
