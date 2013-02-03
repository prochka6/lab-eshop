	package cz.cvut.fel.jee.labEshop.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.jboss.solder.core.Veto;

/**
 * This is basket entity to hold items in basket for concrete user.
 * One basket belongs to one user, in one basket can by multiply items.
 * @author Tom
 *
 */

@Veto
@Entity
@Table(name = "basket")
public class Basket extends BaseEntity{

	private static final long serialVersionUID = 1L;
	 @OneToOne
	private User user;
	
	 @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
	private List<BasketItem> items;

	public List<BasketItem> getItems() {
		return items;
	}

	public void setItems(List<BasketItem> items) {
		this.items = items;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
