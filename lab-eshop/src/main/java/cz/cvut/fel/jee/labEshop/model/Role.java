package cz.cvut.fel.jee.labEshop.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.jboss.solder.core.Veto;

/**
 * This entity hold information of role in system include user which has these
 * roles
 * 
 * @author Tom
 * 
 */
@Veto
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * Users who has assigned this role
	 */
	@Column(nullable = false, unique = true)
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	/**
	 * Label of role used by JAAS to determine authorization
	 */
	@Column(nullable = false, unique = true)
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
