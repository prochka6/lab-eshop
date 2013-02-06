package cz.cvut.fel.jee.labEshop.web.order;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.cvut.fel.jee.labEshop.manager.BasketManager;
import cz.cvut.fel.jee.labEshop.manager.OrderManager;
import cz.cvut.fel.jee.labEshop.model.Order;
import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;
import cz.cvut.fel.jee.labEshop.web.BaseBean;

@Named("orderDetailBean")
@SessionScoped
public class OrderDetailBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	protected OrderManager orderManager;
	@Inject
	protected BasketManager basketManager;

	private Order order;

	@PostConstruct
	public void init() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parameter = params.get("orderId");
		if (parameter != null) {
			Long id = Long.parseLong(parameter);
			order = orderManager.findOrderById(id);
		}
	}
	/**
	 * This function load detail of order
	 * @return navigation string
	 */
	public String loadOrderDetail() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parameter = params.get("orderId");
		Long id = Long.parseLong(parameter);
		order = orderManager.findOrderById(id);
		return "orderDetail";
	}

	public String loadOrderAdminDetail() {
		loadOrderDetail();
		return "orderAdminDetail";
	}

	public String deleteOrder() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parameter = params.get("orderId");
		Long id = Long.parseLong(parameter);
		orderManager.deleteOrder(id);
		// setOrders(orderManager.findAllOrders());
		return "orderManaging";
	}

	public void saveOrder() {
		if (order != null) {
			orderManager.updateOrder(order);
			printMessage(LabEshopConstants.MODIFY_ORDER_SUCC_HEADER, "");
		}

		// setOrders(orderManager.findAllOrders());
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
