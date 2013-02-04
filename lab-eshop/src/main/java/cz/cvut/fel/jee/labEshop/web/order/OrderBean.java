package cz.cvut.fel.jee.labEshop.web.order;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.cvut.fel.jee.labEshop.manager.BasketManager;
import cz.cvut.fel.jee.labEshop.manager.OrderManager;
import cz.cvut.fel.jee.labEshop.web.LoginBean;

@Named("orderBean")
@SessionScoped
public class OrderBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	OrderManager orderManager;
	@Inject
	BasketManager basketManager;
	@Inject
	private LoginBean loginBean;

	@PostConstruct
	public void init() {
		
	}
	
	public void createOrder() {
		orderManager.createOrder(loginBean.getLoggedUser());
	}

}
