package cz.cvut.fel.jee.labEshop.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base persistent class. Defines persistence contract with generated synthetic
 * identifier {@linkplain #id}. ID is used for equals and hashCode contract.
 * Every persistent class implements {@linkplain Serializable} interface to be
 * able transfered or passivated in user session, replicated in clustered
 * environment, etc...
 * 
 * <p>
 * Please note, that every subclass should override default equals and hashCode
 * contract base on own business keys. Otherwise there might occur inconsistency
 * because of possible null values as identifier.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s (id=%s)", new Object[] { getClass().getSimpleName(), id });
	}
}
