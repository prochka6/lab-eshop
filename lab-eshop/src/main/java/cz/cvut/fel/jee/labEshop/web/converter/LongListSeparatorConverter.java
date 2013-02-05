package cz.cvut.fel.jee.labEshop.web.converter;

import java.util.Collection;
import java.util.HashSet;

import javax.enterprise.context.RequestScoped;
import javax.faces.convert.FacesConverter;

/**
 * Comma separated list of {@linkplain Long} converter.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@FacesConverter("longListCommaConverter")
@RequestScoped
public class LongListSeparatorConverter extends AbstractListSeparatorConverter<Long> {

	@Override
	protected String getIdentifier(Long object) {
		if (object != null) {
			return object.toString();
		}
		return "";
	}

	@Override
	protected Long getEntity(String identifier) {
		if (!identifier.isEmpty()) {
			try {
				return Long.parseLong(identifier);
			} catch (NumberFormatException nfe) {
				// ignore and return null
			}
		}

		return null;
	}

	@Override
	protected Collection<Long> buildItemsCollection() {
		return new HashSet<Long>();
	}

}
