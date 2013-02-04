package cz.cvut.fel.jee.labEshop.util.converter;

import javax.ejb.Stateless;

import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.model.OrderItem;

public class OrderItemConverter {
	
	public static OrderItem convertToOrderItem(BasketItem basketItem){
		OrderItem orderItem = new OrderItem();
		orderItem.setProduct(basketItem.getProduct());
		orderItem.setNumberOfPieces(basketItem.getNumberOfItems());
		orderItem.setPrice(basketItem.getNumberOfItems()*basketItem.getProduct().getPrice().amount());
		return orderItem;
	}
	
	
}
