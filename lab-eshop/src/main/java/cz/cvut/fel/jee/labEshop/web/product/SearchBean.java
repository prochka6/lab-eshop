package cz.cvut.fel.jee.labEshop.web.product;

import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.solder.servlet.http.RequestParam;

import cz.cvut.fel.jee.labEshop.filter.ProductSearchFilter;
import cz.cvut.fel.jee.labEshop.filter.SortModel;
import cz.cvut.fel.jee.labEshop.filter.SortOrder;
import cz.cvut.fel.jee.labEshop.manager.ProductManager;
import cz.cvut.fel.jee.labEshop.model.Product;

/**
 * Controller for searching products.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Model
public class SearchBean {

	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final long DEFAULT_PRICE_MIN = 0L;
	public static final long DEFAULT_PRICE_MAX = 50000L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private ProductManager productManager;

	// Product filter properties
	private String orderBy;
	private String ordering;
	private Long priceMin = DEFAULT_PRICE_MIN;
	private Long priceMax = DEFAULT_PRICE_MAX;
	private Set<Long> categoryIds;
	private Set<Long> brandIds;
	private Integer page = 1;
	private Boolean inStock;

	private List<Product> products;

	@Inject
	@RequestParam("productId")
	private Instance<String> productId;

	public List<Product> getProducts() {
		if (products == null && !(productId.get() == null && facesContext.isPostback())) {
			products = productManager.findByFilter(buildFilter());
		}

		return products;
	}

	private ProductSearchFilter buildFilter() {
		ProductSearchFilter filter = new ProductSearchFilter();

		filter.setMaxRows(DEFAULT_PAGE_SIZE);
		if (page != null && page > 1) {
			filter.setFirstRow(DEFAULT_PAGE_SIZE * page + 1);
		}
		if (priceMin != null && priceMin > 0) {
			filter.setPriceMin(priceMin);
		}
		if (priceMax != null && priceMax > 0) {
			if (priceMin != null) {
				if (priceMax > priceMin) {
					filter.setPriceMax(priceMax);
				}
			} else {
				filter.setPriceMax(priceMax);
			}
		}
		if (inStock != null && inStock) {
			filter.setInStock(true);
		}
		if (orderBy != null && "price".equalsIgnoreCase(orderBy)) {
			if (ordering != null && "desc".equalsIgnoreCase(ordering)) {
				filter.setSortModel(new SortModel("price", SortOrder.DESCENDING));
			} else {
				filter.setSortModel(new SortModel("price"));
			}
		} else {
			filter.setSortModel(new SortModel("title"));
		}
		filter.setCategoryIds(categoryIds);
		filter.setBrandIds(brandIds);

		return filter;
	}

	public String search() {
		return "/search?includeViewParams=true&amp;faces-redirect=true";
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrdering() {
		return ordering;
	}

	public void setOrdering(String ordering) {
		this.ordering = ordering;
	}

	public Long getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(Long priceMin) {
		this.priceMin = priceMin;
	}

	public Long getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(Long priceMax) {
		this.priceMax = priceMax;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Set<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(Set<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Set<Long> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(Set<Long> brandIds) {
		this.brandIds = brandIds;
	}

	public Boolean getInStock() {
		return inStock;
	}

	public void setInStock(Boolean inStock) {
		this.inStock = inStock;
	}

}
