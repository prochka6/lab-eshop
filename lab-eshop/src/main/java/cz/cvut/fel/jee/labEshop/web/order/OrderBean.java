package cz.cvut.fel.jee.labEshop.web.order;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.cvut.fel.jee.labEshop.manager.BasketManager;
import cz.cvut.fel.jee.labEshop.manager.OrderManager;
import cz.cvut.fel.jee.labEshop.model.Order;
import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;
import cz.cvut.fel.jee.labEshop.web.BaseBean;
import cz.cvut.fel.jee.labEshop.web.LoginBean;
/**
 * This bean controll orders and their details
 * @author Tom
 *
 */

@Named("orderBean")
@RequestScoped
public class OrderBean extends BaseBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	protected OrderManager orderManager;
	@Inject
	protected BasketManager basketManager;
	@Inject
	private LoginBean loginBean;

	private List<Order> orders;
	
	@PostConstruct
	public void init() {
		orders = orderManager.findUsersOrders(loginBean.getLoggedUser());
	}
	
	/**
	 * This method create order of logged user from his basket.
	 * Then return string to redirect user to orders sum page
	 * @return string to redirect user to orders sum page
	 */
	public String createOrder() {
		orderManager.createOrder(loginBean.getLoggedUser());
		orders = orderManager.findUsersOrders(loginBean.getLoggedUser());
		printMessage(LabEshopConstants.CREATE_ORDER_SUCC_HEADER, "");
		return "customerOrders";
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
