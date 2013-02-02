package cz.cvut.fel.jee.labEshop.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.cvut.fel.jee.labEshop.dao.IRoleDao;
import cz.cvut.fel.jee.labEshop.dao.IUserDao;
import cz.cvut.fel.jee.labEshop.exceptions.EntityNotFoundException;
import cz.cvut.fel.jee.labEshop.filter.UserListFilter;
import cz.cvut.fel.jee.labEshop.model.Role;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.util.Assert;

/**
 * 
 * @author Tom
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Stateless
public class UserManager {

	@Inject
	private IUserDao userDao;

	@Inject
	private IRoleDao roleDao;

	/**
	 * Find user with given identifier.
	 * 
	 * @param id
	 *            the user identifier
	 * @return user entity
	 * @throws EntityNotFoundException
	 *             if user with given identifier doesn't exists.
	 */
	public User findUser(Long id) throws EntityNotFoundException {
		if (id == null) {
			throw new EntityNotFoundException(User.class, id);
		}

		User user = userDao.get(id);

		if (user == null) {
			throw new EntityNotFoundException(User.class, id);
		}

		return user;
	}

	/**
	 * This method find user in database based on username.
	 * 
	 * @param username
	 *            of user to find
	 * @return entity User which has username given as parameter. If there is no
	 *         user or more then one user then null is returned
	 */
	public User findUserByUsername(String username) {
		if (username == null) {
			return null;
		}

		return userDao.getByUsername(username);
	}

	/**
	 * This method find user in database based on email.
	 * 
	 * @param email
	 *            of user to find
	 * @return entity User which has email given as parameter. If there is no
	 *         user or more then one user then null is returned
	 */
	public User findUserByEmail(String email) {
		if (email == null) {
			return null;
		}

		return userDao.getByEmail(email);
	}

	/**
	 * This method add new user to database and set them necessary roles.
	 * 
	 * @param user
	 *            user which will be added to database
	 * @param roles
	 *            role which will user has
	 */
	public void addUser(User user, Set<String> roles) {
		// refactor !!!

		EntityManager em = userDao.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Role> roleQuery = cb.createQuery(Role.class);
		roleQuery.distinct(true);
		Root<Role> role = roleQuery.from(Role.class);
		roleQuery.select(role);
		TypedQuery<Role> typedQuery = em.createQuery(roleQuery);

		List<Role> roleList = typedQuery.getResultList();
		Iterator<Role> roleIt = roleList.iterator();
		Role targetRole = null;
		Set<Role> rolesInSystem = new HashSet<Role>();
		while (roleIt.hasNext()) {
			targetRole = (Role) roleIt.next();
			if (roles.contains(targetRole.getRole())) {
				Set<User> newUsersInRole = targetRole.getUsers();
				newUsersInRole.add(user);
				targetRole.setUsers(newUsersInRole);
				roleDao.saveOrUpdate(targetRole);
				rolesInSystem.add(targetRole);
			}
		}
		user.setRoles(rolesInSystem);
		userDao.saveOrUpdate(user);
	}

	/**
	 * This method find all users in database
	 * 
	 * @return List<Usr> of all users
	 */
	public List<User> findAllUsers() {
		return userDao.getAll();
	}

	/**
	 * This method update data of selected user.
	 * 
	 * @param userToEdit
	 *            user which will updated in database
	 */
	public void updateUser(User userToEdit) {
		userDao.saveOrUpdate(userToEdit);
	}

	/**
	 * Filters users by given filter.
	 * 
	 * @param filter
	 *            the user filter
	 * @return filtered User entities
	 * @throws IllegalArgumentException
	 *             if filter is null.
	 */
	public List<User> findByFilter(UserListFilter filter) throws IllegalArgumentException {
		Assert.notNull(filter);

		return userDao.find(filter);
	}

	/**
	 * Count users by given filter.
	 * 
	 * @param filter
	 *            the user filter
	 * @return count of users returned by filter
	 * @throws IllegalArgumentException
	 *             if filter is null.
	 */
	public int countByFilter(UserListFilter filter) throws IllegalArgumentException {
		Assert.notNull(filter);

		return userDao.countByFilter(filter);
	}

}
