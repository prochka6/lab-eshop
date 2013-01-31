package cz.cvut.fel.jee.labEshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
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
@NamedQueries({ @NamedQuery(name = "Category.getByName", query = "select c from Category c where c.name = :name") })
public class Category extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 50)
	@Column(length = 50, unique = true)
	private String name;

	@Lob
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}

}
