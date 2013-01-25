package cz.cvut.fel.jee.labEshop.dao;

import cz.cvut.fel.jee.labEshop.model.User;

/**
 * User related Dao functionality.
 * 
 * @see User
 * @see IBaseDao
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public interface IUserDao extends IBaseDao<User, Long> {

	/**
	 * Looks up user entity by specified username. If no such exists return
	 * null.
	 * 
	 * @param username
	 *            the username to search
	 * @return user instance with given username or <code>null</code> if no such
	 *         exists.
	 * @throws IllegalArgumentException
	 *             if username is null or empty.
	 */
	User getByUsername(String username) throws IllegalArgumentException;

	/**
	 * Looks up user entity by specified email. If no such exists return null.
	 * 
	 * @param email
	 *            the email to search
	 * @return user instance with given email or <code>null</code> if no such
	 *         exists.
	 * @throws IllegalArgumentException
	 *             if email is null or empty.
	 */
	User getByEmail(String email) throws IllegalArgumentException;

}
