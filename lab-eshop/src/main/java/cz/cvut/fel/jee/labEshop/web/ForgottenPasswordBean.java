package cz.cvut.fel.jee.labEshop.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import cz.cvut.fel.jee.labEshop.manager.UserManager;
import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.util.Mail;
import cz.cvut.fel.jee.labEshop.util.Password;

/**
 * This Bean is used for restoring forgotten password
 * 
 * @author Michal Horak
 * 
 */
@Named("forgottenPasswordBean")
@SessionScoped
public class ForgottenPasswordBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	Logger log;

	@Inject
	FacesContext facesContext;

	@Inject
	private UserManager userManager;

	private String usernameEmail;

	public String getUsernameEmail() {
		return usernameEmail;
	}

	public void setUsernameEmail(String usernameEmail) {
		this.usernameEmail = usernameEmail;
	}

	public ForgottenPasswordBean() {
	}

	/**
	 * This method reset password and send notification to user
	 * 
	 * @see Mail
	 */
	public void sendMail() {
		try {
			String userMail;
			User user = null;

			/**
			 * If user write username instead mail, the user mail will be gained
			 * from db
			 */
			if (usernameEmail.contains("@")) {
				user = userManager.findUserByEmail(usernameEmail);
			} else {
				user = userManager.findUserByUsername(usernameEmail);
			}

			userMail = user.getEmail();

			String subject = "Lab-Eshop, your password was reseted!";

			// generate new random password
			String password = Password.getRandomPlainPassword();

			user.setPassword(Password.getHash(password));

			userManager.updateUser(user);

			// send notification to user email
			Mail.sendMail(userMail, subject, generateMessage(password, user));

			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"New password was sent to your email address!",
					"Password changed"));

		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Ooops, somethig goes wrong, please try it again.",
					"Detail: " + e.getMessage()));

		}
	}

	private String generateMessage(String password, User user) {
		String message = "Hi, " + user.getFullName() + " ("
				+ user.getUsername() + ")\n";
		message += "For your account was generated new password: " + password
				+ "\n\n\n";
		message += "Your Lab-Eshop Team.\n\n\n";
		message += "Please do not reply to this message.";

		return message;
	}

}
