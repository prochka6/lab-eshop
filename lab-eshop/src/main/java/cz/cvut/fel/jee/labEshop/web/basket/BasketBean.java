package cz.cvut.fel.jee.labEshop.web.basket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.cvut.fel.jee.labEshop.manager.BasketManager;
import cz.cvut.fel.jee.labEshop.manager.ProductManager;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.model.Product;
import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;
import cz.cvut.fel.jee.labEshop.web.BaseBean;
import cz.cvut.fel.jee.labEshop.web.LoginBean;

/**
 * This bean is controller to basket, it provide add to basket, create basket
 * and modify basket functions
 * 
 * @author Tom
 */
@Named("basketBean")
@RequestScoped
public class BasketBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LoginBean loginBean;

	private Basket basket;

	private List<BasketItem> itemsInBasket;

	private Long totalPrice;

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
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String parameter = params.get("productId");
		Long id = Long.parseLong(parameter);
		Product productToAdd = productManager.findProduct(id);
		basketManager.addItemToBasket(loginBean.getLoggedUser(), productToAdd);

		printMessage(LabEshopConstants.ADD_TO_BASKET_SUCC_HEADER, productToAdd.getTitle());
		
	}

	/**
	 * This function call business layer to drop basket, then items are
	 * re-rendered
	 */
	public void dropBasket() {
		basketManager.dropBasket(loginBean.getLoggedUser());
		itemsInBasket = new ArrayList<BasketItem>();
		printMessage(LabEshopConstants.REMOVE_BASKET_SUCC_HEADER, "");
	}

	/**
	 * This function call business method to modify basket, then items are
	 * re-rendered
	 */
	public void modifyBasket() {
		basketManager.modifyBasket(itemsInBasket, loginBean.getLoggedUser());
		itemsInBasket = basketManager.findItemsInBasket(basket);
		printMessage(LabEshopConstants.MODIFY_BASKET_SUCC_HEADER, "");
	}

	/**
	 * This function calculate total price in basket
	 * 
	 * @return total price items in basket
	 */
	public Long calculateTotalPrice() {
		totalPrice = new Long(0);
		if (itemsInBasket != null) {
			Iterator<BasketItem> basketItemIt = itemsInBasket.iterator();
			while (basketItemIt.hasNext()) {
				BasketItem item = basketItemIt.next();
				setTotalPrice(getTotalPrice()
						+ item.getProduct().getPrice().amount()
						* item.getNumberOfItems());
			}
		}
		return totalPrice;
	}

	public List<BasketItem> getItemsInBasket() {
		// itemsInBasket = basketManager.findItemsInBasket(basket);
		return itemsInBasket;
	}

	public void setItemsInBasket(List<BasketItem> itemsInBasket) {
		this.itemsInBasket = itemsInBasket;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

}
