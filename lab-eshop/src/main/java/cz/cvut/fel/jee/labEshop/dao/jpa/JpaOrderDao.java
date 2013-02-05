package cz.cvut.fel.jee.labEshop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cz.cvut.fel.jee.labEshop.dao.IOrderDao;
import cz.cvut.fel.jee.labEshop.model.Order;
import cz.cvut.fel.jee.labEshop.model.Order_;
import cz.cvut.fel.jee.labEshop.model.User;

public class JpaOrderDao extends JpaBaseDao<Order> implements IOrderDao{

	@Override
	public List<Order> findOrdersByUser(User user) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		Predicate usersBasket = builder.equal(root.get(Order_.user), user);
		query.distinct(true);
		query.where(usersBasket);
		TypedQuery<Order> typedQuery = em.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		if (resultList!=null) {
			return resultList;
		}
		return new ArrayList<Order>();
	}

}
