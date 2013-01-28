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
 * Class serves as custom validator for checking the email uniqueness.
 * 
 * @author Michal Horak
 * @author <a href="mailto:prochka6@fel.cvut.cz">Kamil Prochazka (prochka6)</a>
 */
@RequestScoped
@FacesValidator("emailUniqueValidator")
public class UserEmailUniqueValidator implements Validator {

	@Inject
	private UserManager userManager;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || value.toString().trim().isEmpty()) {
			return;
		}

		User user = userManager.findUserByEmail((String) value);
		if (user != null) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email already taken.", null));
		}
	}

}
