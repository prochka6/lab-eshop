package cz.cvut.fel.jee.labEshop.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.jboss.solder.core.Veto;

/**
 * Category groups {@linkplain Product products} under category name and
 * optionally description of the category. Doesn't support nested model, only
 * top level categories are supported right now.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Veto
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
