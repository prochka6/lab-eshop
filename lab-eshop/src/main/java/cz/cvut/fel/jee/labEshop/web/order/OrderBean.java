package cz.cvut.fel.jee.labEshop.web.order;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.cvut.fel.jee.labEshop.manager.BasketManager;
import cz.cvut.fel.jee.labEshop.manager.OrderManager;
import cz.cvut.fel.jee.labEshop.model.Order;
import cz.cvut.fel.jee.labEshop.web.LoginBean;

@Named("orderBean")
@RequestScoped
public class OrderBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	OrderManager orderManager;
	@Inject
	BasketManager basketManager;
	@Inject
	private LoginBean loginBean;

	private List<Order> orders;
	
	@PostConstruct
	public void init() {
		orders = orderManager.findUsersOrders(loginBean.getLoggedUser());
	}
	
	public String createOrder() {
		orderManager.createOrder(loginBean.getLoggedUser());
		orders = orderManager.findUsersOrders(loginBean.getLoggedUser());
		return "customerOrders";
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}