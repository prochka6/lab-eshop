package cz.cvut.fel.jee.labEshop.filter;

import java.util.Set;

import cz.cvut.fel.jee.labEshop.model.Product;

/**
 * Product filter TO. Contains attributes for filtering {@linkplain Product
 * products} in eshop.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class ProductSearchFilter extends EntityFilter {

	private static final long serialVersionUID = 1L;

	private Long priceMin;
	private Long priceMax;

	private Set<Long> categoryIds;
	private Set<Long> brandIds;

	private Boolean inStock;

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
