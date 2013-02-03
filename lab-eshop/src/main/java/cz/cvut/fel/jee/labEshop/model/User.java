package cz.cvut.fel.jee.labEshop.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.jboss.solder.core.Veto;

import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;

/**
 * User represents person who is interacting with system.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Veto
@Entity
@Table(name = "user")
@NamedQueries({ @NamedQuery(name = "User.getByEmail", query = "select u from User u where u.email = :email"),
		@NamedQuery(name = "User.getByUsername", query = "select u from User u where u.username = :username") })
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Role> roles;

	@NotNull
	@Size(min = 5)
	@Column(nullable = false, unique = true)
	private String username;

	@NotNull
	@Size(min = 5)
	@Column(nullable = false)
	private String password;

	@NotNull
	@Email
	@Column(nullable = false, unique = true)
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Embedded
	private Address address;
	
	 @OneToOne(mappedBy = "user", fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
	private Basket basket;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Decide whether user has Administrator role in application.
	 * 
	 * @return <code>true</code> if given user has assigned administrator role
	 *         otherwise <code>false</code>
	 */
	public boolean isAdmin() {
		if (roles != null) {
			for (Role role : roles) {
				if (LabEshopConstants.ADMINISTRATOR_ROLE.equals(role.getRole())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

}
