package cz.cvut.fel.jee.labEshop.web.basket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.cvut.fel.jee.labEshop.manager.BasketManager;
import cz.cvut.fel.jee.labEshop.manager.OrderManager;
import cz.cvut.fel.jee.labEshop.manager.ProductManager;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.model.PaymentMethod;
import cz.cvut.fel.jee.labEshop.web.LoginBean;

/**
 * This bean is controller to basket, it provide add to basket, create basket
 * and modify basket functions
 * 
 * @author Tom
 */
@Named("basketBean")
@RequestScoped
public class BasketBean implements Serializable {

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
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parameter = params.get("productId");
		Long id = Long.parseLong(parameter);
		basketManager.addItemToBasket(loginBean.getLoggedUser(), productManager.findProduct(id));
	}
	
	/**
	 * This function call business layer to drop basket, then items are re-rendered
	 */
	public void dropBasket(){
		basketManager.dropBasket(loginBean.getLoggedUser());
		itemsInBasket = new ArrayList<BasketItem>();
	}

	/**
	 * This function call business method to modify basket, then items are re-rendered
	 */
	public void modifyBasket() {
		basketManager.modifyBasket(itemsInBasket, loginBean.getLoggedUser());
		itemsInBasket = basketManager.findItemsInBasket(basket);
	}
	/**
	 * This function calculate total price in basket
	 * @return total price items in basket
	 */
	public Long calculateTotalPrice(){
		totalPrice = new Long(0);
		if(itemsInBasket !=null){
			Iterator<BasketItem> basketItemIt = itemsInBasket.iterator();
			while(basketItemIt.hasNext()){
				BasketItem item = basketItemIt.next();
				setTotalPrice(getTotalPrice() + item.getProduct().getPrice().amount()*item.getNumberOfItems());
			}
		}
		return totalPrice;
	}
	
	/**
	 * This method redirect user to final buy procedure
	 * @return page address of final buy procedure
	 */
	public String nextStep(){
		return "buyBasket";
	}
	
	public List<BasketItem> getItemsInBasket() {
//		itemsInBasket = basketManager.findItemsInBasket(basket);
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
