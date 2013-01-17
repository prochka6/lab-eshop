package cz.cvut.fel.jee.labEshop.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.jboss.solder.core.Veto;

/**
 * Address wrapper type.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Veto
@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	private String street;

	private String city;

	private String country;

	public Address() {
	}

	public Address(String street, String city, String country) {
		super();
		this.street = street;
		this.city = city;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", country=" + country + "]";
	}

}
