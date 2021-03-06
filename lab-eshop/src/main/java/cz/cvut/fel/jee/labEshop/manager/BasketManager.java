package cz.cvut.fel.jee.labEshop.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.SecurityDomain;

import cz.cvut.fel.jee.labEshop.dao.IBasketDao;
import cz.cvut.fel.jee.labEshop.dao.IBasketItemDao;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.model.Product;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;

/**
 * This manager work with basket and items in basket
 * 
 * @author Tom
 * 
 */
@Stateless
@SecurityDomain("labeshopsecurity")
@DeclareRoles({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
public class BasketManager extends BaseManager<BasketItem> {

	private static final long serialVersionUID = 1L;

	@Inject
	private IBasketDao basketDao;

	@Inject
	private IBasketItemDao basketItemDao;
	@Resource
	private SessionContext sessionContext; 


	/**
	 * This method find users basket, if there is no basket then new basket will
	 * be create and returned. New basket will have of course empty item list
	 * 
	 * @param user
	 *            which basket has been found
	 * @return basket which has particular user
	 */
	@RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public Basket findBasketByUser(User user) {
		Basket result = basketDao.findBasketByUser(user);
		if (result == null) {
			result = basketDao.saveOrUpdate(buildBasket(user));
		}
		return result;
	}

	/**
	 * This method update basket in database
	 * 
	 * @param itemsToModify
	 *            items which will be modify
	 * @param owner
	 *            of basket
	 */
	@RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public void modifyBasket(List<BasketItem> itemsToModify, User owner) {
		if (itemsToModify == null) {
			return;
		}
		Basket userBasket = findBasketByUser(owner);
		Iterator<BasketItem> baskteItemIt = itemsToModify.iterator();
		while (baskteItemIt.hasNext()) {
			BasketItem itemToModify = baskteItemIt.next();
			modifyBasket(itemToModify, null, userBasket);
		}
	}

	/**
	 * This function modify one basket item in datbase. If owners basket is null
	 * then owner must be not null. Base find by ownersBasket
	 * 
	 * @param item
	 *            to modify
	 * @param owner
	 *            of basket
	 * @param ownersBasket
	 *            basekt of owner
	 */
	@RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public void modifyBasket(BasketItem item, User owner, Basket ownersBasket) {
		if (ownersBasket == null) {
			ownersBasket = findBasketByUser(owner);
		}
		List<BasketItem> itemsInBasket = null;
		itemsInBasket = removeFromCollection(ownersBasket.getItems(), item);
		if (item.getNumberOfItems() <= 0) {
			ownersBasket.setItems(itemsInBasket);
			basketItemDao.delete(item);
		} else {
			itemsInBasket = addItemToCollection(ownersBasket.getItems(), item);
			ownersBasket.setItems(itemsInBasket);
			basketItemDao.saveOrUpdate(item);
		}
		basketDao.saveOrUpdate(ownersBasket);
	}
	
	/**
	 * This method drop all items in basket. Basket is not deleted, but item in them are.
	 * @param basketOwner owner of basket, which basket will be empty
	 */
	@RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public void dropBasket(User basketOwner){
		if(basketOwner!=null){
			Basket ownersBasket = findBasketByUser(basketOwner);
			List<BasketItem> itemsInBasket = basketItemDao.findItemsInBasket(ownersBasket);
			while(itemsInBasket.size()>=1){
				BasketItem itemInBasket = itemsInBasket.get(0);
				itemsInBasket.remove(0);
				basketItemDao.delete(itemInBasket);
			}
			ownersBasket.setItems(new ArrayList<BasketItem>());
			basketDao.saveOrUpdate(ownersBasket);
		}
	}

	/**
	 * This method add item to basket which belongs to concrete user. Use logic
	 * from findBasketByUser method. If there is a item in basket then only
	 * number of this item in basket is increased
	 * 
	 * @param owner
	 *            basket owner
	 * @param productToAdd
	 *            product which will be added
	 */
	@RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public void addItemToBasket(User owner, Product productToAdd) {
		Basket basket = findBasketByUser(owner);
		BasketItem basketItem = basketItemDao.findBasketItemInBasket(productToAdd, basket);
		// Create new basketItem
		if (basketItem == null) {
			basketItem = new BasketItem();
			basketItem.setNumberOfItems(1);
			basketItem.setProduct(productToAdd);
			basketItem.setBasket(basket);
		} else {
			// Modify number pieces in basket
			int pieces = basketItem.getNumberOfItems() + 1;
			basketItem.setNumberOfItems(pieces);

		}

		basketItem = basketItemDao.saveOrUpdate(basketItem);
		List<BasketItem> itemsInBasket = addItemToCollection(basket.getItems(), basketItem);
		basket.setItems(itemsInBasket);
		basket = basketDao.saveOrUpdate(basket);
	}

	/**
	 * Method inf all items in basket
	 * 
	 * @param basket
	 *            in which are items
	 * @return items which are in basket
	 */
	@RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public List<BasketItem> findItemsInBasket(Basket basket) {
		return basketItemDao.findItemsInBasket(basket);
	}

	/**
	 * Method build new basket to persist
	 * 
	 * @param owner
	 *            user withnout basekt
	 * @return new basket
	 */
	@RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	private Basket buildBasket(User owner) {
		Basket basket = new Basket();
		List<BasketItem> basketItem = new ArrayList<BasketItem>();
		basket.setItems(basketItem);
		basket.setUser(owner);
		return basket;
	}

}
