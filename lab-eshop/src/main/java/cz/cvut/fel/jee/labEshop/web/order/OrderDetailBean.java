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
import cz.cvut.fel.jee.labEshop.web.LoginBean;

@Named("orderDetailBean")
@SessionScoped
public class OrderDetailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	OrderManager orderManager;
	@Inject
	BasketManager basketManager;
	@Inject
	private LoginBean loginBean;
	
	private Order order;
	
	@PostConstruct
	public void init(){
		
	}
	
	public String loadOrderDetail(){
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parameter = params.get("orderId");
		Long id = Long.parseLong(parameter);
		order = orderManager.findOrderByIdAndUser(id, loginBean.getLoggedUser());
		return "orderDetail";
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}