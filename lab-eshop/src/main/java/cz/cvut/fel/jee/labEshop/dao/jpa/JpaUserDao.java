package cz.cvut.fel.jee.labEshop.dao.jpa;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cz.cvut.fel.jee.labEshop.dao.IUserDao;
import cz.cvut.fel.jee.labEshop.filter.UserListFilter;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.model.User_;
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

	@Override
	public List<User> find(UserListFilter filter) throws IllegalArgumentException {
		Assert.notNull(filter, "Filter may not be null.");

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);

		query.select(root);
		query.where(buildFindPredicate(builder, root, filter));
		applySortModel(builder, query, root, filter);

		return applyPaging(em.createQuery(query), filter).getResultList();
	}

	@Override
	public int countByFilter(UserListFilter filter) {
		Assert.notNull(filter, "Filter may not be null.");

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<User> root = query.from(User.class);

		query.select(builder.count(root));
		query.where(buildFindPredicate(builder, root, filter));

		return em.createQuery(query).getSingleResult().intValue();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Predicate buildFindPredicate(CriteriaBuilder builder, Root root, UserListFilter filter) {
		Predicate predicate = builder.conjunction();

		if (filter.getUsername() != null) {
			predicate = builder.and(predicate,
					builder.like(builder.lower(root.get(User_.username)), filter.getUsername() + "%"));
		}
		if (filter.getEmail() != null) {
			predicate = builder.and(predicate,
					builder.like(builder.lower(root.get(User_.email)), filter.getEmail() + "%"));
		}
		if (filter.getName() != null) {
			predicate = builder.and(
					predicate,
					builder.or(builder.like(builder.lower(root.get(User_.firstName)), filter.getName() + "%"),
							builder.like(builder.lower(root.get(User_.lastName)), filter.getName() + "%")));
		}

		return predicate;
	}

}
