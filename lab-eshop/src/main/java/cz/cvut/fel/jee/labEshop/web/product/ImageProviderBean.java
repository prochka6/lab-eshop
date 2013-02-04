package cz.cvut.fel.jee.labEshop.web.product;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("imageProvider")
@SessionScoped
public class ImageProviderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private byte[] stream;

	public byte[] getStream() {
		return stream;
	}

	public void setStream(byte[] stream) {
		this.stream = stream;
	}

	public StreamedContent getStreamedImage() {
		if (stream != null) {
			return new DefaultStreamedContent(new ByteArrayInputStream(stream), "image/jpeg");
		}
		return null;
	}
}
