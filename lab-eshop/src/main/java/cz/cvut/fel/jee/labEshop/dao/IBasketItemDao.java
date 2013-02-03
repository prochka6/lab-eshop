package cz.cvut.fel.jee.labEshop.dao;

import java.util.List;

import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;

public interface IBasketItemDao extends IBaseDao<BasketItem, Long> {
	public List<BasketItem> findItemsInBasket(Basket basket);
}
