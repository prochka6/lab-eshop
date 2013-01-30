package cz.cvut.fel.jee.labEshop.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;
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
	private Messages messages;

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

			messages.info("New password was sent to your email address!");
			log.info("Password changed");

		} catch (Exception e) {

			messages.info("Ooops, somethig goes wrong, please try it again.");
			log.info("Detail: " + e.getMessage());
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
