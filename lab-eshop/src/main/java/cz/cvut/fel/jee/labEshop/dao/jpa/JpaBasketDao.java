package cz.cvut.fel.jee.labEshop.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cz.cvut.fel.jee.labEshop.dao.IBasketDao;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.Basket_;
import cz.cvut.fel.jee.labEshop.model.User;

public class JpaBasketDao extends JpaBaseDao<Basket>  implements IBasketDao{

	@Override
	public Basket findBasketByUser(User user) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Basket> query = builder.createQuery(Basket.class);
		Root<Basket> root = query.from(Basket.class);
		Predicate usersBasket = builder.equal(root.get(Basket_.user),user);
		query.distinct(true);
		query.where(usersBasket);
        TypedQuery<Basket> typedQuery = em.createQuery(query);
        List<Basket> resultList = typedQuery.getResultList();
        if(!resultList.isEmpty()){
        	return resultList.get(0);
        }
        return null;
	}

	@Override
	public Basket createBasketForUser(Basket basket) {
		Basket updatedBasket  = saveOrUpdate(basket);
		return updatedBasket;
	}
	
	

	
}
