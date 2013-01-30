package cz.cvut.fel.jee.labEshop.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import cz.cvut.fel.jee.labEshop.model.User;
import cz.cvut.fel.jee.labEshop.util.Password;
import cz.cvut.fel.jee.labEshop.web.LoginBean;

/**
 * Validator checks correctness of given password against logged user
 * 
 * @author Michal Horak
 * 
 */
@FacesValidator(value = "userPasswordCorrectnessValidator")
public class UserPasswordCorrectnessValidator implements Validator {

	@Inject
	LoginBean loginBean;

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (value == null || value.toString().trim().isEmpty()) {
			return;
		}

		User loggedUser = loginBean.getLoggedUser();

		String password;
		try {
			password = Password.getHash((String) value);
		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage("ERROR: "
					+ e.getMessage()));
		}

		if (loggedUser != null && !loggedUser.getPassword().equals(password)) {
			throw new ValidatorException(new FacesMessage(
					"The old password is incorrect"));
		}
	}

}
