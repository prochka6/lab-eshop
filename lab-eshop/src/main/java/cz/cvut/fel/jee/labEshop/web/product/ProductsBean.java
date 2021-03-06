package cz.cvut.fel.jee.labEshop.web.product;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;

import cz.cvut.fel.jee.labEshop.manager.BrandManager;
import cz.cvut.fel.jee.labEshop.manager.CategoryManager;
import cz.cvut.fel.jee.labEshop.manager.ProductManager;
import cz.cvut.fel.jee.labEshop.model.Brand;
import cz.cvut.fel.jee.labEshop.model.Category;
import cz.cvut.fel.jee.labEshop.model.Money;
import cz.cvut.fel.jee.labEshop.model.Product;
import cz.cvut.fel.jee.labEshop.model.ProductAvailability;

/**
 * Products JSF controller.
 * 
 * @author Ondřej Harcuba (<a href="mailto:harcuond@fel.cvut.cz">prochka6</a>)
 */
@Named("productsBean")
@ViewScoped
public class ProductsBean implements Serializable {

	private static final long serialVersionUID = -1795939077116397980L;

	@Inject
	private Logger log;

	@Inject
	private Messages messages;

	@Inject
	private ProductManager productManager;

	@Inject
	private BrandManager brandManager;

	@Inject
	private CategoryManager categoryManager;

	@Inject
	private ImageProviderBean imgProvider;

	private transient List<Product> products;

	private Product selectedProduct;

	private List<Product> filteredProducts;

	private List<Brand> brands;

	private List<Category> categories;

	private Long price;

	public List<Product> getProducts() {
		if (products == null) {
			products = productManager.findAllProducts();
		}

		return products;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Brand> getBrands() {
		if (brands == null) {
			brands = brandManager.findAllBrands();
		}

		return brands;
	}

	public List<Category> getCategories() {
		if (categories == null) {
			categories = categoryManager.findAllCategories();
		}

		return categories;
	}

	public void createNewProduct() {
		selectedProduct = new Product();
		selectedProduct.setAvailability(ProductAvailability.IN_STOCK);
		imgProvider.setStream(null);
		price = null;
	}

	public void setSelectedProduct(Product selectedProduct) {
		if (selectedProduct != null) {
			price = selectedProduct.getPrice().amount();
			imgProvider.setStream(selectedProduct.getPromoImage());
		} else {
			imgProvider.setStream(null);
		}
		this.selectedProduct = selectedProduct;
	}

	public void clear() {
		price = null;
		this.selectedProduct = null;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public List<Product> getFilteredProducts() {
		return filteredProducts;
	}

	public void setFilteredProducts(List<Product> filteredProducts) {
		this.filteredProducts = filteredProducts;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public boolean isSelection() {
		return selectedProduct != null ? true : false;
	}

	public List<ProductAvailability> getAvailabilities() {
		return Arrays.asList(ProductAvailability.values());
	}

	public void submit() {
		if (selectedProduct.getPublishDate().getTime() > selectedProduct
				.getDiscardDate().getTime()) {
			messages.error("Invalid Dates - Publish Date must be lower than Discard Date!- Operation cancelled.");
			return;
		}

		if (selectedProduct.getId() == null) {
			selectedProduct.setPrice(new Money(price));

			if (productManager.findProduct(selectedProduct.getCode()) != null) {
				FacesMessage msg = new FacesMessage("Invalid Code - Code allready in database!- Operation cancelled.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				productManager.createProduct(selectedProduct);
				products.add(0, selectedProduct);
				messages.info("Product created successfully.");
				selectedProduct = null;
				price = null;
			}

		} else {
			Product productinDB = productManager.findProduct(selectedProduct
					.getCode());
			if (productinDB != null
					&& productinDB.getId() != selectedProduct.getId()) {
				FacesMessage msg = new FacesMessage("Invalid Code - Code allready in database!- Operation cancelled.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			} else {

				FacesMessage msg = new FacesMessage("Product "
						+ selectedProduct.getTitle() + " edited successfully");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				if (selectedProduct.getPrice() != null
						&& price != selectedProduct.getPrice().amount()) {
					selectedProduct.setPrice(new Money(price));
				}
				selectedProduct = productManager.updateProduct(selectedProduct);
				products.set(products.indexOf(selectedProduct), selectedProduct);

				selectedProduct = null;
				price = null;
			}
		}

	}

	public void uploadFile(FileUploadEvent event) throws IOException {
		UploadedFile uploadedFile = event.getFile();
		selectedProduct.setPromoImage(uploadedFile.getContents());
		imgProvider.setStream(selectedProduct.getPromoImage());

		log.info("Image {} upload success.", uploadedFile.getFileName());
		messages.info("Image: {0} has been successfully uploaded.",
				uploadedFile.getFileName());
	}

	public void onRowSelect(SelectEvent event) {
	}

	public void onRowUnselect(UnselectEvent event) {
	}

}