package cz.cvut.fel.jee.labEshop.dao.jpa;

import java.util.List;

import cz.cvut.fel.jee.labEshop.dao.IUserDao;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.util.Assert;

/**
 * Jpa implementation of {@linkplain IUserDao}.
 * 
 * @see IUserDao
 * @see JpaBaseDao
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class JpaUserDao extends JpaBaseDao<User> implements IUserDao {

	@Override
	public User getByUsername(String username) {
		Assert.notEmpty(username, "Username may not be empty or null.");

		List<User> result = em.createNamedQuery("User.getByUsername", User.class).setParameter("username", username)
				.getResultList();
		if (result.isEmpty()) {
			return null;
		}

		// Expected max 1 value because of DB uniqueness.
		return result.get(0);
	}

	@Override
	public User getByEmail(String email) {
		Assert.notEmpty(email, "Email may not be empty or null.");

		List<User> result = em.createNamedQuery("User.getByEmail", User.class).setParameter("email", email)
				.getResultList();
		if (result.isEmpty()) {
			return null;
		}

		// Expected max 1 value because of DB uniqueness.
		return result.get(0);
	}

}
