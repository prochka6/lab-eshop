package cz.cvut.fel.jee.labEshop.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validator checks whether the password input and confirm password are the same
 * 
 * @author Michal Horak
 */
@FacesValidator(value = "confirmPasswordValidator")
public class ConfirmPasswordValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || value.toString().trim().isEmpty()) {
			return;
		}

		UIInput passwordComponent = (UIInput) component.getAttributes().get("passwordComponent");
		String password = (String) passwordComponent.getValue();
		String confirmPassword = (String) value;

		if (confirmPassword != null && !confirmPassword.equals(password)) {
			throw new ValidatorException(new FacesMessage("Confirm new password is not the same as new password"));
		}
	}
}
