package cz.cvut.fel.jee.labEshop.dao;

import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.Brand;
import cz.cvut.fel.jee.labEshop.model.User;

public interface IBasketDao extends IBaseDao<Basket, Long> {
	/**
	 * This method find basket in database which belongs to user in parameter.
	 * If there is no basket then return null
	 * @param user which own basket
	 * @return basket which own user or null if there is no basket
	 */
	public Basket findBasketByUser(User user);
	    
	/**
	 * This method create new basket for user
	 * @param basket which will be persist
	 * @return new basket which was persist, this new basket contain id
	 */
	public Basket createBasketForUser(Basket basket);
}
