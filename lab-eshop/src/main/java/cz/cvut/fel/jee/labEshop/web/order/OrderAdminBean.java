package cz.cvut.fel.jee.labEshop.web.order;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@Named("orderAdminBean")
@RequestScoped
public class OrderAdminBean extends OrderBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		setOrders(orderManager.findAllOrders());
	}

}
