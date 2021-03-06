package cz.cvut.fel.jee.labEshop.manager;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.SecurityDomain;

import cz.cvut.fel.jee.labEshop.dao.jpa.JpaOrderDao;
import cz.cvut.fel.jee.labEshop.dao.jpa.JpaPaymentMethodDao;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.model.Order;
import cz.cvut.fel.jee.labEshop.model.OrderItem;
import cz.cvut.fel.jee.labEshop.model.PaymentMethod;
import cz.cvut.fel.jee.labEshop.model.Product;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;
import cz.cvut.fel.jee.labEshop.util.converter.OrderItemConverter;

/**
 * This manager work with order. It can create order, find orders and etc. For
 * more information view avaiable method.
 * 
 * @author Tom
 * 
 */
@Stateless
@SecurityDomain("labeshopsecurity")
@DeclareRoles({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
public class OrderManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private BasketManager basketManager;
	@Inject
	private ProductManager productManager;
	@Inject
	private JpaOrderDao orderDao;
	@Inject
	private JpaPaymentMethodDao paymentMethodDao;
	
	@Resource
	private SessionContext sessionContext; 

	/**
	 * This method create new order. Order is created based on logged user.
	 * First users basket is found and then if there are items in basket is
	 * order created.
	 * 
	 * @param loggedUser
	 *            user which basket will be convert to order
	 */
	  @RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public void createOrder(User loggedUser) {
		Basket userBasket = basketManager.findBasketByUser(loggedUser);
		List<BasketItem> itemsInBasket = userBasket.getItems();
		if (!itemsInBasket.isEmpty()) {
			Order order = new Order();
			order.setStateOfOrder(Order.State.INSERTED);
			order.setDateOfInsert(Calendar.getInstance().getTime());
			order.setUser(loggedUser);
			Iterator<BasketItem> basketItemIt = itemsInBasket.iterator();
			List<OrderItem> orderItems = new ArrayList<OrderItem>();
			Long totalPrice = new Long(0);
			while (basketItemIt.hasNext()) {
				BasketItem itemInBasket = basketItemIt.next();
				OrderItem orderItem = OrderItemConverter
						.convertToOrderItem(itemInBasket);
				orderItems.add(orderItem);
				orderItem.setOrder(order);
				productManager.sellProduct(orderItem.getProduct(), orderItem.getNumberOfPieces());
				totalPrice += orderItem.getPrice();
			}
			order.setTotalPrice(totalPrice);
			order.setItems(orderItems);
			List<PaymentMethod> methods = paymentMethodDao.getAll();
			PaymentMethod defaultMethod = null;
			if (methods != null && !methods.isEmpty()) {
				defaultMethod = methods.get(0);
			} else {
				defaultMethod = new PaymentMethod();
				defaultMethod.setDescription("Super metoda");
				defaultMethod.setName("Czech post");
				defaultMethod.setPrice(new Long(0));
				defaultMethod = paymentMethodDao.saveOrUpdate(defaultMethod);
			}
			order.setPaymentMethod(defaultMethod);
			orderDao.saveOrUpdate(order);
			orderDao.flush();
			basketManager.dropBasket(loggedUser);
		}

	}

	/**
	 * This method find all orders which belong to user in parameter. If there
	 * are no orders then empty List is returned
	 * 
	 * @param user
	 *            which order are in system
	 * @return list of orders which belongs to user, if there are no orders then
	 *         new empty List is returnder
	 */
	  @RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public List<Order> findUsersOrders(User user) {
		List<Order> orders = new ArrayList<Order>();
		if (user != null) {
			orders = orderDao.findOrdersByUser(user);
		}
		return orders;
	}

	  @RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE})
	public List<Order> findAllOrders() {
		return orderDao.getAll();
	}

	/**
	 * This method find order by id, this order must be belongs to user in
	 * parameter, otherwise is null returned
	 * 
	 * @param id
	 *            of order
	 * @param user
	 *            owner of order
	 * @return Order which belongs to user and has specifi id
	 */
	  @RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public Order findOrderByIdAndUser(Long id, User user) {
		Order order = findOrderById(id);
		if (!order.getUser().equals(user)) {
			return null;
		}
		return order;
	}

	/**
	 * This method find order by id in parameter
	 * 
	 * @param id
	 *            of order to find
	 * @return order which has specific id
	 */
	  @RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public Order findOrderById(Long id) {
		return orderDao.get(id);
	}
	  @RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE, LabEshopConstants.CUSTOMER_ROLE})
	public List<PaymentMethod> findAllPaymentMethod() {
		return paymentMethodDao.getAll();
	}
	  @RolesAllowed(LabEshopConstants.ADMINISTRATOR_ROLE)
	public void deleteOrder(Long id){
		Order order = orderDao.get(id);
		List<OrderItem> items = order.getItems();
		Iterator<OrderItem> itemsIT = items.iterator();
		while(itemsIT.hasNext()){
			OrderItem item = itemsIT.next();
			Product product = item.getProduct();
			productManager.revertProduct(product, item.getNumberOfPieces());
		}
		orderDao.delete(order);
		orderDao.flush();
	}
		@RolesAllowed({LabEshopConstants.ADMINISTRATOR_ROLE})
	public void updateOrder(Order order){
		orderDao.saveOrUpdate(order);
	}

}
