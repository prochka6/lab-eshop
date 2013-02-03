package cz.cvut.fel.jee.labEshop.web;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import cz.cvut.fel.jee.labEshop.manager.ProductManager;
import cz.cvut.fel.jee.labEshop.model.Product;

/**
 * Sample implementation for showing Logger, EntityManager injection. Using
 * caching cause of JSF lifecycle calls getXXX methods multiple times during
 * single request processing.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Model
public class Sample {

	private List<Product> products;

	@Inject
	private ProductManager productManager;

	public List<Product> products() {
		if (products == null) {
			products = productManager.findLatestsProducts(null);
		}

		return products;
	}

}
