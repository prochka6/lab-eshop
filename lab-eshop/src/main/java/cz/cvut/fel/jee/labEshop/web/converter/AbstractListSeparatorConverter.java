package cz.cvut.fel.jee.labEshop.web.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base converter for string separated values representing {@linkplain List} of
 * entity type <T> which should be converter.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 * 
 * @param <T>
 *            type of {@linkplain List} element.
 */
public abstract class AbstractListSeparatorConverter<T> implements Converter {

	public static final String DEFAULT_SEPARATOR = ",";

	protected Logger log = LoggerFactory.getLogger(getClass());

	protected final String separator;

	public AbstractListSeparatorConverter() {
		separator = DEFAULT_SEPARATOR;
	}

	public AbstractListSeparatorConverter(String separator) {
		this.separator = separator;
	}

	@Override
	public Collection<T> getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		Collection<T> items = buildItemsCollection();
		StringTokenizer tokenizer = new StringTokenizer(value, separator);

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken().trim();
			T parsedObject = getEntity(token);
			if (parsedObject != null) {
				items.add(parsedObject);
			}
		}

		if (!items.isEmpty()) {
			return items;
		}

		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (value instanceof Collection<?>) {
				if (!((Collection<?>) value).isEmpty()) {
					StringBuilder sb = new StringBuilder();

					for (T object : ((Collection<T>) value)) {
						sb.append(getIdentifier(object));
						sb.append(separator);
					}

					return sb.substring(0, sb.length() - separator.length());
				}
			}
		}

		return null;
	}

	/**
	 * Factory method for construction of collection type like List or Set.
	 * Default implementation returns ArrayList.
	 * 
	 * @return collection to be used for storing elements parsed from client.
	 */
	protected Collection<T> buildItemsCollection() {
		return new ArrayList<T>();
	}

	/**
	 * Gets object identifier.
	 * 
	 * <p>
	 * Given parameter is element from converted collection which can be null in
	 * some type of collections.
	 * 
	 * @param object
	 *            the object which identifier should be returned
	 * @return identifier of given object
	 */
	protected abstract String getIdentifier(T object);

	/**
	 * Parse identifier and return entity. If returns <code>null</code> will not
	 * be added to items in collection returned by converter.
	 * 
	 * <p>
	 * Given parameter can be empty string, but never <code>null</code>
	 * 
	 * @param identifier
	 *            the identifier of object
	 * @return constructed object from given identifier.
	 */
	protected abstract T getEntity(String identifier);

}
