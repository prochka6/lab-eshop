package cz.cvut.fel.jee.labEshop.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.FacesValidator;

import cz.cvut.fel.jee.labEshop.model.Brand;
import cz.cvut.fel.jee.labEshop.model.ProductAvailability;

@FacesConverter(value = "AvailabilityConverter")
public class AvailabilityConverter implements Converter{
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		  if (value == null || (value.trim().length() == 0))
	        {
	            return value;
	        }
	 
		  ProductAvailability availability = ProductAvailability.valueOf(value);
	        if (availability==null)
	        {
	            throw new ConverterException();
	        }
	 
	        return availability;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		ProductAvailability availability = null;
		 
        if (value instanceof Brand)
        {
        	availability = (ProductAvailability)value;
 
            StringBuilder availabilityAsString = new StringBuilder();
            availabilityAsString.append(availability.toString());
            return availabilityAsString.toString();
        }
        return "";
	}
}
