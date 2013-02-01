package cz.cvut.fel.jee.labEshop.web.converter;

import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import cz.cvut.fel.jee.labEshop.model.Brand;

public class BrandConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		  if (value == null || (value.trim().length() == 0))
	        {
	            return value;
	        }
	 
	        Brand brand = new Brand();
	        boolean conversionError = false;
	 
	        int dashCount = 0;
	        StringTokenizer dashTokenizer = new StringTokenizer(value, "-");
	        while (dashTokenizer.hasMoreTokens())
	        {
	            String token = dashTokenizer.nextToken();
	            try
	            {
	                if (dashCount == 0)
	                {
	                    brand.setId(Long.parseLong(token));
	                }
	 
	                if (dashCount == 1)
	                {
	                    brand.setName(token);
	                }
	 
	                if (dashCount == 2)
	                {
	                    brand.setComment(token);
	                }
	                dashCount ++;
	            }
	            catch (Exception exception)
	            {
	                conversionError = true;
	            }
	        }
	 
	        if (conversionError || (dashCount != 3))
	        {
	            throw new ConverterException();
	        }
	 
	        return brand;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Brand brand = null;
		 
        if (value instanceof Brand)
        {
        	brand = (Brand)value;
 
            StringBuilder brandAsString = new StringBuilder();
            brandAsString.append(brand.getId() + "-");
            brandAsString.append(brand.getName() + "-");
            brandAsString.append(brand.getComment());
            return brandAsString.toString();
        }
        return "";
	}

}
