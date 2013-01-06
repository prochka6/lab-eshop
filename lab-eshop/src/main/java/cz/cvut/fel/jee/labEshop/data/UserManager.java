package cz.cvut.fel.jee.labEshop.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cz.cvut.fel.jee.labEshop.idata.IUserManager;
import cz.cvut.fel.jee.labEshop.model.Role;
import cz.cvut.fel.jee.labEshop.model.User;

/**
 * This is implementation of IUserManager interface. Please use metamodel in future. 
 * @author Tom
 *
 */

@Stateless
public class UserManager extends ObjectManager implements IUserManager {

	private static final long serialVersionUID = 1L;

	@Override
	public User findUserByUsername(String username) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> userQuery = cb.createQuery(User.class);
		Root<User> rootQueryUser = userQuery.from(User.class);
		//NEXT TIME USE ACCESS VIA METAMODEL!!!!!
		Predicate usernamePredicate = cb.equal(rootQueryUser.get("username"),
				username);
		userQuery.distinct(true).where(usernamePredicate);
		TypedQuery<User> typedQuery = em.createQuery(userQuery);
		List<User> resultList = typedQuery.getResultList();
		if (resultList.size() == 1) {
			return resultList.get(0);
		}
		return null;
	}




	@Override
	public void addUser(User userToadd, Set<String> rolesOfUser) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Role> roleQuery = cb.createQuery(Role.class);
		roleQuery.distinct(true);
		TypedQuery<Role> typedQuery = em.createQuery(roleQuery);
		List<Role> roleList = typedQuery.getResultList();
		Iterator<Role> roleIt = roleList.iterator();
		Role targetRole = null;
		Set<Role> rolesInSystem = new HashSet<Role>();
		while (roleIt.hasNext()) {
			targetRole = (Role) roleIt.next();
			if (rolesOfUser.contains(targetRole.getRole())) {
				Set<User> newUsersInRole = targetRole.getUsers();
				newUsersInRole.add(userToadd);
				targetRole.setUsers(newUsersInRole);
				super.update(targetRole);
				rolesInSystem.add(targetRole);
			}
		}
		userToadd.setRoles(rolesInSystem);
		super.add(userToadd);
	}

}
