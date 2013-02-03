package cz.cvut.fel.jee.labEshop.web.basket;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.cvut.fel.jee.labEshop.manager.BasketManager;
import cz.cvut.fel.jee.labEshop.model.Basket;
import cz.cvut.fel.jee.labEshop.model.BasketItem;
import cz.cvut.fel.jee.labEshop.web.LoginBean;

@Named("basketBean")
@SessionScoped
public class BasketBean implements Serializable{

   @Inject
    private LoginBean loginBean; 
    
    private Basket basket;
	
	private List<BasketItem> itemsInBasket;
	
	@Inject
	private BasketManager basketManager;
	
	@PostConstruct
	public void init(){
//		 loginBean = (LoginBean)FacesContext.getCurrentInstance() 
//				.getExternalContext().getSessionMap().get("LoginBean");
		basket = basketManager.findBasketByUser(loginBean.getLoggedUser());
		itemsInBasket = basketManager.findItemsInBasket(basket);
	}

	public List<BasketItem> getItemsInBasket() {
		return itemsInBasket;
	}

	public void setItemsInBasket(List<BasketItem> itemsInBasket) {
		this.itemsInBasket = itemsInBasket;
	}
	
	
	
}
