package cz.cvut.fel.jee.labEshop.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.jboss.solder.core.Veto;

/**
 * Money is immutable class mimic real money.
 * 
 * <p>
 * For our simple usage we suppose, that the currency is always CZK and will not
 * take in count any currency fractions. Also no other methods like, add,
 * subtrack, etc are not supported. :)
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Veto
@Embeddable
public class Money implements Comparable<Money>, Serializable {

	private static final long serialVersionUID = 1L;

	private long amount;

	// hibernate only
	Money() {
	}

	public Money(Number amount) {
		this.amount = amount.longValue();
	}

	/**
	 * Simply returns amount as long number, because of no use of fractions
	 * etc...
	 * 
	 * @return amount
	 */
	public long amount() {
		return amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (amount ^ (amount >>> 32));
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
		Money other = (Money) obj;
		if (amount != other.amount)
			return false;
		return true;
	}

	@Override
	public int compareTo(Money o) {
		if (amount < o.amount) {
			return -1;
		} else if (amount > o.amount) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "Money [amount=" + amount + "]";
	}

}
