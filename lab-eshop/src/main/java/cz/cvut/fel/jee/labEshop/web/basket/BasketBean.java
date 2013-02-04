package cz.cvut.fel.jee.labEshop.web.basket;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.cvut.fel.jee.labEshop.manager.BasketManager;
import cz.cvut.fel.jee.labEshop.manager.ProductManager;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.web.LoginBean;

/**
 * This bean is controller to basket, it provide add to basket, create basket
 * and modify basket functions
 * 
 * @author Tom
 */
@Named("basketBean")
@SessionScoped
public class BasketBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LoginBean loginBean;

	private Basket basket;

	private List<BasketItem> itemsInBasket;

	@Inject
	private BasketManager basketManager;
	@Inject
	private ProductManager productManager;

	@PostConstruct
	public void init() {
		basket = basketManager.findBasketByUser(loginBean.getLoggedUser());
		itemsInBasket = basketManager.findItemsInBasket(basket);
	}

	/**
	 * This function call business methods to add item to basket. Item id is
	 * taken from FacesContext.
	 */
	public void addItemToBasket() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parameter = params.get("productId");
		Long id = Long.parseLong(parameter);
		basketManager.addItemToBasket(loginBean.getLoggedUser(), productManager.findProduct(id));
	}

	/**
	 * This function call business method to modify basket.
	 */
	public void modifyBasket() {
		basketManager.modifyBasket(itemsInBasket, loginBean.getLoggedUser());
	}

	public List<BasketItem> getItemsInBasket() {
		itemsInBasket = basketManager.findItemsInBasket(basket);
		return itemsInBasket;
	}

	public void setItemsInBasket(List<BasketItem> itemsInBasket) {
		this.itemsInBasket = itemsInBasket;
	}

}
