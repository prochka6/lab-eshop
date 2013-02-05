package cz.cvut.fel.jee.labEshop.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.jboss.solder.core.Veto;

@Veto
@Entity
@Table(name = "paymentMethod")
public class PaymentMethod extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@NotNull
	private String name;
	
	private String description;
	
	private Long price;

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
