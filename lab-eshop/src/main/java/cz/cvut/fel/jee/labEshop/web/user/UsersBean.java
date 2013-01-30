package cz.cvut.fel.jee.labEshop.web.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;
import org.slf4j.Logger;

import cz.cvut.fel.jee.labEshop.manager.UserManager;
import cz.cvut.fel.jee.labEshop.model.Address;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;
import cz.cvut.fel.jee.labEshop.util.Password;
import cz.cvut.fel.jee.labEshop.web.LoginBean;

/**
 * This class creates new customers.
 * 
 * @author Michal Horak
 * 
 */
@Named("usersBean")
@SessionScoped
public class UsersBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private Messages messages;

	@Inject
	private UserManager userManager;

	@Inject
	private Event<User> userEventSrc;

	@Inject
	private LoginBean loginBean;

	private User newUser;

	private User editUser;

	private Address address;

	public UsersBean() {
	}

	/**
	 * Register new user with customer role
	 * 
	 * @throws Exception
	 */
	public void register() throws Exception {
		log.info("Registering " + newUser.getFullName());
		Set<String> role = new HashSet<String>();
		role.add(LabEshopConstants.CUSTOMER_ROLE);
		newUser.setAddress(address);
		newUser.setPassword(Password.getHash(newUser.getPassword()));
		userManager.addUser(newUser, role);

		messages.info("Registration successful");
		userEventSrc.fire(newUser);

		initNewUser();
	}

	/**
	 * Update user account credentials
	 */
	public void update() {
		log.info("Updating " + editUser.getFullName());
		userManager.updateUser(editUser);
		messages.info("Update successful");

	}

	@PostConstruct
	public void initNewUser() {
		newUser = new User();
		address = new Address();

	}

	public String editUser() {
		editUser = loginBean.getLoggedUser();
		if (editUser.getAddress() == null) {
			editUser.setAddress(new Address());
		}

		return "editAccount";
	}

	@Named
	@Produces
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Named
	@Produces
	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	@Named
	@Produces
	public User getEditUser() {
		return editUser;
	}

	public void setEditUser(User editUser) {
		this.editUser = editUser;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

}