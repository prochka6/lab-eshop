package cz.cvut.fel.jee.labEshop.web.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Safe converter for Long objects. When getAsObject is called with not Long
 * value returns null.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@FacesConverter("longSafeConverter")
@RequestScoped
public class LongSafeConverter implements Converter {

	@Override
	public Long getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		try {
			return Long.parseLong(value.trim());
		} catch (NumberFormatException e) {
			// ignore and return null
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return value.toString();
		}

		return null;
	}

}
