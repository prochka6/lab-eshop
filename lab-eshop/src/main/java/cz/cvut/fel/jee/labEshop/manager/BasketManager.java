package cz.cvut.fel.jee.labEshop.manager;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import cz.cvut.fel.jee.labEshop.dao.IBasketDao;
import cz.cvut.fel.jee.labEshop.dao.IBasketItemDao;
import cz.cvut.fel.jee.labEshop.dao.IBrandDao;
import cz.cvut.fel.jee.labEshop.dao.IUserDao;
import cz.cvut.fel.jee.labEshop.dao.jpa.JpaBasketDao;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.model.User;
/**
 * This manager work with basket and items in basket
 * @author Tom
 *
 */

@Stateless
public class BasketManager {
	@Inject
	private IBasketDao basketDao;
	
	@Inject
	private IBasketItemDao basketItemDao;
	
	@Inject
	private IUserDao userDao;
	/**
	 * This method find users basket, if there is no basket then new basket will be create and returned. New basket will have of course empty item list
	 * @param user which basket has been found
	 * @return basket which has particular user
	 */
	public Basket findBasketByUser(User user){
		Basket result = basketDao.findBasketByUser(user);
		if(result == null){
			result = createBasket(buildBasket(user), user);
		}
		return result;
	}
	/**
	 * Method inf all items in basket
	 * @param basket in which are items
	 * @return items which are in basket
	 */
	public List<BasketItem> findItemsInBasket(Basket basket){
		return basketItemDao.findItemsInBasket(basket);
	}
	/**
	 * Method add new basket to database
	 * @param basket to create
	 * @param user withou basket
	 * @return new basket
	 */
	public Basket createBasket(Basket basket, User user){
		Basket toReturn = basketDao.createBasketForUser(basket);
		user.setBasket(basket);
		userDao.saveOrUpdate(user);
		return toReturn;

	}
	/**
	 * Method build new basket to persist
	 * @param owner user withnout basekt
	 * @return new basket
	 */
	private Basket buildBasket(User owner){
		Basket basket = new Basket();
		List<BasketItem> basketItem = new ArrayList<BasketItem>();
		basket.setItems(basketItem);
		basket.setUser(owner);
		return basket;
	}
	
}
