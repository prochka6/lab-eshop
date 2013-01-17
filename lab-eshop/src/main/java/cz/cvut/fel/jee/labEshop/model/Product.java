package cz.cvut.fel.jee.labEshop.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jboss.solder.core.Veto;

/**
 * Product represent main entity in eshop. Every product contains main title,
 * short description named summary, long description where should be all
 * specification listed. Every product must be in some category and under some
 * brand.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Veto
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String title;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
	private Brand brand;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Category category;

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private ProductAvailability availability;

	@Basic(optional = false)
	@Embedded
	@AttributeOverride(name = "amount", column = @Column(name = "price"))
	private Money price;

	private String summary;

	@Lob
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public ProductAvailability getAvailability() {
		return availability;
	}

	public void setAvailability(ProductAvailability availability) {
		this.availability = availability;
	}

	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
