package cz.cvut.fel.jee.labEshop.web.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import cz.cvut.fel.jee.labEshop.manager.ProductManager;
import cz.cvut.fel.jee.labEshop.model.Product;

/**
 * Faces converter for {@linkplain Product} instances. Converter uses
 * {@linkplain Product#getId()} field for identification.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@RequestScoped
@FacesConverter("productConverter")
public class ProductConverter implements Converter {

	@Inject
	private ProductManager productManager;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		try {
			return productManager.findProduct(Long.parseLong(value.trim()));
		} catch (RuntimeException re) {
			throw new ConverterException("Could not convert product. Param value must be id of existing product.");
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (!(value instanceof Product)) {
				throw new ConverterException(
						"Could not convert product to string. The value param is not a product instance.");
			}

			return ((Product) value).getId().toString();
		}

		return null;
	}

}
