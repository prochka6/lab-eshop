package cz.cvut.fel.jee.labEshop.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * This entity hold information of role in system include user which has these roles
 * @author Tom
 *
 */
@Entity
@Table(name = "role")
public class Role extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Users which has this role
	 */
	@Column(nullable = false, unique = true)
	 @ManyToMany(mappedBy="roles",fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
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
