package cz.cvut.fel.jee.labEshop.web.validator;

import java.util.List;

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
 * Class serves as custom validator for checking the email uniqueness.
 * 
 * @author buben
 * 
 */
@Named(value = "emailValidator")
@RequestScoped
public class VEmail implements Validator {

	@Inject
	private IUserManager userManager;

	private List<User> usersToFind;

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		String email = (String) value;
		usersToFind = userManager.findAllUsers();

		for (User user : usersToFind) {
			if (user.getEmail().equals(email)) {
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Email is already taken");
				context.addMessage("Email: ", message);
				throw new ValidatorException(message);
			}

		}
	}

}
