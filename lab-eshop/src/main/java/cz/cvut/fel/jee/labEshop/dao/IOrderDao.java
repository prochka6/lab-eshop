package cz.cvut.fel.jee.labEshop.dao;

import java.util.List;

import cz.cvut.fel.jee.labEshop.model.Order;
import cz.cvut.fel.jee.labEshop.model.User;

public interface IOrderDao extends IBaseDao<Order, Long>{

	public List<Order> findOrdersByUser(User user);
	
}
