package cz.cvut.fel.jee.labEshop.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.jboss.solder.core.Veto;

/**
 * Brand represents manufacturer of product. Can be used to specify workflow
 * during complain processes or contain additional data about brands.
 * 
 * <p>
 * Example: Sony, Cisco, LG, ...
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Veto
@Entity
@Table(name = "brand")
public class Brand extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
