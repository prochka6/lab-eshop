package cz.cvut.fel.jee.labEshop.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import cz.cvut.fel.jee.labEshop.manager.UserManager;
import cz.cvut.fel.jee.labEshop.model.Address;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.util.LabEshopConstants;
import cz.cvut.fel.jee.labEshop.util.Password;

/**
 * This class creates new customers.
 * 
 * @author Michal Horak
 * 
 */
@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private UserManager userManager;

	@Inject
	private Event<User> userEventSrc;

	private User newUser;

	private Address address;

	public UserBean() {
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

		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Registered!",
				"Registration successful"));
		userEventSrc.fire(newUser);

		initNewUser();

	}

	@PostConstruct
	public void initNewUser() {
		newUser = new User();
		address = new Address();
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

}
