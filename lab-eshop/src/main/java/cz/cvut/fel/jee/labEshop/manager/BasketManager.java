package cz.cvut.fel.jee.labEshop.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import cz.cvut.fel.jee.labEshop.dao.IBasketDao;
import cz.cvut.fel.jee.labEshop.dao.IBasketItemDao;
import cz.cvut.fel.jee.labEshop.dao.IUserDao;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.model.Product;
import cz.cvut.fel.jee.labEshop.model.User;
/**
 * This manager work with basket and items in basket
 * @author Tom
 *
 */

@Stateless
public class BasketManager extends BaseManager<BasketItem>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	 * This method update basket in database
	 * @param itemsToModify items which will be modify
	 * @param owner of basket
	 */
	public void modifyBasket(List<BasketItem> itemsToModify, User owner){
		if(itemsToModify == null){
			return;
		}
		Basket userBasket = findBasketByUser(owner);
		Iterator<BasketItem> baskteItemIt = itemsToModify.iterator();
		while(baskteItemIt.hasNext()){
			BasketItem itemToModify = baskteItemIt.next();
			modifyBasket(itemToModify, null,userBasket );
		}
	}
	/**
	 * This function modify one basket item in datbase.
	 * If owners basket is null then owner must be not null. Base find by ownersBasket
	 * @param item to modify
	 * @param owner of basket
	 * @param ownersBasket basekt of owner
	 */
	public void modifyBasket(BasketItem item, User owner, Basket ownersBasket){
		if(ownersBasket == null){
			ownersBasket = findBasketByUser(owner);
		}
		List<BasketItem> itemsInBasket = null;
		itemsInBasket = removeFromCollection(ownersBasket.getItems(), item);
		if(item.getNumberOfItems() <= 0){
			ownersBasket.setItems(itemsInBasket);
			basketItemDao.delete(item);
		}
		else{
			itemsInBasket = addItemToCollection(ownersBasket.getItems(), item);
			ownersBasket.setItems(itemsInBasket);
			basketItemDao.saveOrUpdate(item);
		}
		basketDao.saveOrUpdate(ownersBasket);		
	}
	
	/**
	 * This method add item to basket which belongs to concrete user. Use logic from 
	 * findBasketByUser method. If there is a item in basket then only number of this item
	 * in basket is increased
	 * @param owner basket owner
	 * @param productToAdd product which will be added
	 */
	public void addItemToBasket(User owner, Product productToAdd){
		Basket basket = findBasketByUser(owner);
		BasketItem basketItem = basketItemDao.findBasketItemInBasket(productToAdd, basket);
//		Create new basketItem
		if(basketItem == null){
			basketItem = new BasketItem();
			basketItem.setNumberOfItems(1);
			basketItem.setProduct(productToAdd);
			basketItem.setBasket(basket);
		}
		else{
//			Modify number pieces in basket
			int pieces = basketItem.getNumberOfItems()+1;
			basketItem.setNumberOfItems(pieces);
			
		}
		
		basketItem = basketItemDao.saveOrUpdate(basketItem);
		List<BasketItem> itemsInBasket = addItemToCollection(basket.getItems(), basketItem); 
		basket.setItems(itemsInBasket);
		basket = basketDao.saveOrUpdate(basket);
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
