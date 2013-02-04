package cz.cvut.fel.jee.labEshop.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jboss.solder.core.Veto;

@Veto
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST })
	private Order order;
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	private Long price;
	
	private int numberOfPieces;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public int getNumberOfPieces() {
		return numberOfPieces;
	}

	public void setNumberOfPieces(int numberOfPieces) {
		this.numberOfPieces = numberOfPieces;
	}
}
