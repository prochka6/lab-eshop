package cz.cvut.fel.jee.labEshop.dao.jpa;

import cz.cvut.fel.jee.labEshop.dao.IProductDao;
import cz.cvut.fel.jee.labEshop.model.Product;

/**
 * Jpa implementation of {@linkplain IProductDao}.
 * 
 * @see IProductDao
 * @see JpaBaseDao
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class JpaProductDao extends JpaBaseDao<Product> implements IProductDao {

}