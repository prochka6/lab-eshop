package cz.cvut.fel.jee.labEshop.web.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import cz.cvut.fel.jee.labEshop.manager.UserManager;
import cz.cvut.fel.jee.labEshop.model.User;

/**
 * This class is custom validator which validates given username or mail
 * 
 * @author Michal Horak
 */
@RequestScoped
@FacesValidator("usernameMailValidator")
public class PasswordUsernameMailValidator implements Validator {

	@Inject
	private UserManager userManager;

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (value == null || value.toString().trim().isEmpty()) {
			return;
		}

		User user = null;

		// check the presence of given mail in db
		if (value.toString().contains("@")) {
			user = userManager.findUserByEmail((String) value);
			check(user, "Mail");

		} else {
			user = userManager.findUserByUsername((String) value);
			check(user, "Username");
		}

	}

	private void check(User user, String text) {
		if (user == null) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, text
							+ " doesn't exist!", null));
		}
	}

}
