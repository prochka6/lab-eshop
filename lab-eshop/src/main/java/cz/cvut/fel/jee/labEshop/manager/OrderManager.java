package cz.cvut.fel.jee.labEshop.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import cz.cvut.fel.jee.labEshop.dao.jpa.JpaOrderDao;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.model.Order;
import cz.cvut.fel.jee.labEshop.model.OrderItem;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.util.converter.OrderItemConverter;

public class OrderManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private BasketManager basketManager;
	@Inject
	private JpaOrderDao orderDao;

	
	public void createOrder(User loggedUser){
		Basket userBasket = basketManager.findBasketByUser(loggedUser);
		List<BasketItem> itemsInBasket = userBasket.getItems();
		if(!itemsInBasket.isEmpty()){
			Order order = new Order();
			order.setStateOfOrder(Order.State.INSERTED);
			order.setDateOfInsert(Calendar.getInstance().getTime());
			order.setUser(loggedUser);
			Iterator<BasketItem> basketItemIt = itemsInBasket.iterator();
			List<OrderItem> orderItems = new ArrayList<OrderItem>();
			Long totalPrice = new Long(0);
			while(basketItemIt.hasNext()){
				BasketItem itemInBasket = basketItemIt.next();
				OrderItem orderItem = OrderItemConverter.convertToOrderItem(itemInBasket);
				orderItems.add(orderItem);
				orderItem.setOrder(order);
				totalPrice +=orderItem.getPrice();
			}
			order.setTotalPrice(totalPrice);
			order.setItems(orderItems);
			orderDao.saveOrUpdate(order);
			orderDao.flush();
			basketManager.dropBasket(loggedUser);
		}
		
	}
	
	public List<Order> findUsersOrders(User user){
		List<Order> orders = new ArrayList<Order>();
		if(user !=null){
			orders = orderDao.findOrdersByUser(user);
		}
		return orders;
	}
	
}
