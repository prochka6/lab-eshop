package cz.cvut.fel.jee.labEshop.web.converter;

import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import cz.cvut.fel.jee.labEshop.model.Category;

public class CategoryConverter implements Converter{
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		  if (value == null || (value.trim().length() == 0))
	        {
	            return value;
	        }
	 
		  Category category = new Category();
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
	                    category.setId(Long.parseLong(token));
	                }
	 
	                if (dashCount == 1)
	                {
	                    category.setName(token);
	                }
	 
	                if (dashCount == 2)
	                {
	                    category.setDescription(token);
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
	 
	        return category;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Category category = null;
		 
        if (value instanceof Category)
        {
        	category = (Category)value;
 
            StringBuilder brandAsString = new StringBuilder();
            brandAsString.append(category.getId() + "-");
            brandAsString.append(category.getName() + "-");
            brandAsString.append(category.getDescription());
            return brandAsString.toString();
        }
        return "";
	}
}
