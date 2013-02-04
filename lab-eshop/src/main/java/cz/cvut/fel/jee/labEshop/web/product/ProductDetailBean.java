package cz.cvut.fel.jee.labEshop.web.product;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.solder.servlet.http.RequestParam;

import cz.cvut.fel.jee.labEshop.exceptions.EntityNotFoundException;
import cz.cvut.fel.jee.labEshop.manager.ProductManager;
import cz.cvut.fel.jee.labEshop.model.Product;

/**
 * Product detail page.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Model
public class ProductDetailBean {

	@Inject
	private FacesContext facesContext;

	@Inject
	private ProductManager productManager;

	@Inject
	private ImageProviderBean imgProvider;

	@Inject
	@RequestParam("id")
	private String requestParam;

	private Long productId;
	private Product product;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void loadProduct() {
		if (facesContext.isValidationFailed()) {
			navigateToUnknownProductPage();
			return;
		}
		try {
			product = productManager.findProduct(productId);
			imgProvider.setStream(product.getPromoImage());
		} catch (EntityNotFoundException enfe) {
			navigateToUnknownProductPage();
		}
	}

	private void navigateToUnknownProductPage() {
		facesContext
				.getApplication()
				.getNavigationHandler()
				.handleNavigation(facesContext, null,
						"/unknown-product.xhtml?id=" + requestParam + "&faces-redirect=true");
	}

}
