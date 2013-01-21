package cz.cvut.fel.jee.labEshop.web.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import cz.cvut.fel.jee.labEshop.idata.IUserManager;
import cz.cvut.fel.jee.labEshop.model.User;

/**
 * This class serves as the custom validator for checking the username
 * uniqueness.
 * 
 * @author buben
 * 
 */
@Named(value = "usernameValidator")
@RequestScoped
public class VUsername implements Validator {

	@Inject
	private IUserManager userManager;

	private User userToFind;

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		String username = (String) value;
		userToFind = userManager.findUserByUsername(username);

		if (userToFind != null) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Username is already taken");
			context.addMessage("Username: ", message);
			throw new ValidatorException(message);
		}

	}

}
