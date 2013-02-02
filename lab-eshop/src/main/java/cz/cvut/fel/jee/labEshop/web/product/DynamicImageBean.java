package cz.cvut.fel.jee.labEshop.web.product;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("imageBean")
@RequestScoped
@Stateful
public class DynamicImageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProductsBean productsBean;

	public StreamedContent getStreamedImage() {
		if (productsBean.getSelectedProduct() != null && productsBean.getSelectedProduct().getPromoImage() != null) {
			return new DefaultStreamedContent(new ByteArrayInputStream(productsBean.getSelectedProduct()
					.getPromoImage()), "image/jpeg");
		}
		return null;
	}

}
