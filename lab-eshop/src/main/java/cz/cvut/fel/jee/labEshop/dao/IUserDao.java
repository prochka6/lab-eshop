package cz.cvut.fel.jee.labEshop.dao;

import java.util.List;

import cz.cvut.fel.jee.labEshop.filter.UserListFilter;
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

	/**
	 * Finds users by specified user filter.
	 * 
	 * @param filter
	 *            the user filter we want to search by
	 * @return
	 * @throws IllegalArgumentException
	 *             if the filter param is null
	 */
	List<User> find(UserListFilter filter) throws IllegalArgumentException;

	/**
	 * Returns count of users by given filter.
	 * 
	 * @param filter
	 *            the user filter entity
	 * @return count of user returned by given filter
	 */
	int countByFilter(UserListFilter filter);

}
