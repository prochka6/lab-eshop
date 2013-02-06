package cz.cvut.fel.jee.labEshop.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class BaseBean {

	
	public void printMessage(String header,String message){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(header, message));
	}
}
