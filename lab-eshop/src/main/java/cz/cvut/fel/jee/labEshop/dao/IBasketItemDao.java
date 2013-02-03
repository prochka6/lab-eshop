package cz.cvut.fel.jee.labEshop.dao;

import java.util.List;

import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.model.Product;

public interface IBasketItemDao extends IBaseDao<BasketItem, Long> {
	/**
	 * This method find all items which belongs to particular basket
	 * @param basket in which are items
	 * @return all items which are in concrete basket 
	 */
	public List<BasketItem> findItemsInBasket(Basket basket);
	/**
	 * This method find concrete basket item which is in concrete basket and is 
	 * connected with concrete product
	 * @param productItem product which is connected with basketitem
	 * @param basket basket in which is product contaion
	 * @return concrete basket item which is in concrete basket and is 
	 * connected with concrete product
	 */
	public BasketItem findBasketItemInBasket(Product productItem, Basket basket);
}
