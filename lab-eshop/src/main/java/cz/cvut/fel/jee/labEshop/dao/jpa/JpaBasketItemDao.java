package cz.cvut.fel.jee.labEshop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cz.cvut.fel.jee.labEshop.dao.IBasketItemDao;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.model.BasketItem_;
import cz.cvut.fel.jee.labEshop.model.Product;

public class JpaBasketItemDao extends JpaBaseDao<BasketItem> implements IBasketItemDao {

	@Override
	public List<BasketItem> findItemsInBasket(Basket basket) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BasketItem> query = builder.createQuery(BasketItem.class);
		Root<BasketItem> root = query.from(BasketItem.class);
		Predicate basketPredicate = builder.equal(root.get(BasketItem_.basket), basket);
		query.distinct(true);
		query.where(basketPredicate);
		TypedQuery<BasketItem> typedQuery = em.createQuery(query);
		List<BasketItem> resultList = typedQuery.getResultList();
		if (resultList.isEmpty()) {
			return new ArrayList<BasketItem>();
		}
		return resultList;
	}

	@Override
	public BasketItem findBasketItemInBasket(Product productItem, Basket basket) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BasketItem> query = builder.createQuery(BasketItem.class);
		Root<BasketItem> root = query.from(BasketItem.class);
		Predicate basketPredicate = builder.equal(root.get(BasketItem_.basket), basket);
		Predicate productPredicate = builder.equal(root.get(BasketItem_.product), productItem);
		query.distinct(true);
		query.where(builder.and(productPredicate, basketPredicate));
		TypedQuery<BasketItem> typedQuery = em.createQuery(query);
		List<BasketItem> resultList = typedQuery.getResultList();
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		}
		return null;
	}

}
