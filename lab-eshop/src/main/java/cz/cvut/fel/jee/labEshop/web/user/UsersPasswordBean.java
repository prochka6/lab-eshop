package cz.cvut.fel.jee.labEshop.web.user;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;
import org.slf4j.Logger;

import cz.cvut.fel.jee.labEshop.manager.UserManager;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.util.Password;
import cz.cvut.fel.jee.labEshop.web.LoginBean;

/**
 * User's password Controller.
 * 
 * @author Michal Horak
 * 
 */
@Named("usersPasswordBean")
@SessionScoped
public class UsersPasswordBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private Messages messages;

	@Inject
	private UserManager userManager;

	@Inject
	private LoginBean loginBean;

	private String oldPassword;

	private User editUser;

	public UsersPasswordBean() {
	}

	public String changePassword() {
		editUser = loginBean.getLoggedUser();
		return "changePassword";
	}

	public void update() throws Exception {
		log.info("Changing password " + editUser.getFullName());
		editUser.setPassword(Password.getHash(editUser.getPassword()));
		userManager.updateUser(editUser);

		messages.info("Password was changed.");
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public User getEditUser() {
		return editUser;
	}

	public void setEditUser(User editUser) {
		this.editUser = editUser;
	}
}